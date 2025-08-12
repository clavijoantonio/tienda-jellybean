package com.alexandra.jellybeanstore;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexandra.jellybeanstore.adapter.PedidoAdapter;
import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.PedidoApiService;
import com.alexandra.jellybeanstore.api.PedidoRequest;
import com.alexandra.jellybeanstore.api.PedidoResponse;
import com.alexandra.jellybeanstore.databinding.FragmentResumenPedidoBinding;
import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.viewmodels.SharedPedidoViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResumenPedidoFragment extends Fragment {
    private NavController navController;
    private FragmentResumenPedidoBinding binding;
    private SharedPedidoViewModel sharedViewModel;
    private PedidoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResumenPedidoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedPedidoViewModel.class);

        adapter = new PedidoAdapter();
        binding.rvDetalles.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDetalles.setAdapter(adapter);
        navController = Navigation.findNavController(view);

        sharedViewModel.getDetallesPedido().observe(getViewLifecycleOwner(), detalles -> {
            adapter.submitList(detalles);
            calcularTotal(detalles);
        });

        binding.btnConfirmarPedido.setOnClickListener(v -> confirmarPedido());
    }

    private void calcularTotal(List<DetallePedido> detalles) {
        double total = 0;
        for (DetallePedido detalle : detalles) {
            total += detalle.getProduct().getPrecioProducto() * detalle.getCantidad();
        }
        binding.textTotal.setText(String.format("Total: $%.2f", total));
    }

    private void confirmarPedido() {
        sharedViewModel.getDetallesPedido().observe(getViewLifecycleOwner(), detalles -> {
            if (detalles == null || detalles.isEmpty()) {
                Toast.makeText(requireContext(), "No hay productos en el pedido", Toast.LENGTH_SHORT).show();
                return;
            }

            long clienteId = sharedViewModel.getClienteId().getValue(); // Asegúrate de tener este método en tu ViewModel
            PedidoRequest pedidoRequest = new PedidoRequest(clienteId, detalles);

            binding.btnConfirmarPedido.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);

            PedidoApiService apiService = ApiClient.getClient().create(PedidoApiService.class);
            Call<PedidoResponse> call = apiService.crearPedido(pedidoRequest);

            call.enqueue(new Callback<PedidoResponse>() {
                @Override
                public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {
                    binding.btnConfirmarPedido.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(requireContext(), "Pedido creado exitosamente", Toast.LENGTH_SHORT).show();
                        sharedViewModel.limpiarPedido();
                        // Navegar a pantalla de confirmación o inicio
                        navController.popBackStack(R.id.mainFragment, false);
                    } else {
                        Toast.makeText(requireContext(), "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PedidoResponse> call, Throwable t) {
                    binding.btnConfirmarPedido.setEnabled(true);
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}