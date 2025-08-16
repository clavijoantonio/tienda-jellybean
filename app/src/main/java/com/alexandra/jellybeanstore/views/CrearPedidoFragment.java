package com.alexandra.jellybeanstore.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexandra.jellybeanstore.adapter.DetallePedidoAdapter;
import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.PedidoApiService;
import com.alexandra.jellybeanstore.databinding.FragmentCrearPedidoBinding;
import com.alexandra.jellybeanstore.repositories.PedidoRepository;
import com.alexandra.jellybeanstore.repositories.ProductoRepository;
import com.alexandra.jellybeanstore.viewmodels.CrearPedidoViewModelFactory;

import java.util.ArrayList;


public class CrearPedidoFragment extends Fragment {

    private FragmentCrearPedidoBinding binding;
    private CrearPedidoViewModel viewModel;
    private DetallePedidoAdapter adapter;

    public CrearPedidoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCrearPedidoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PedidoApiService apiService = ApiClient.getClient().create(PedidoApiService.class);
        PedidoRepository pedidoRepository = new PedidoRepository(apiService);
        ProductoRepository productoRepository = new ProductoRepository();
        CrearPedidoViewModelFactory factory = new CrearPedidoViewModelFactory(pedidoRepository, productoRepository);

        viewModel = new ViewModelProvider(requireActivity(), factory).get(CrearPedidoViewModel.class);

        setupRecyclerView();
        setupObservers();
        //setupButtons();
    }

    private void setupRecyclerView() {
        adapter = new DetallePedidoAdapter(new ArrayList<>(), position -> viewModel.removerDetalle(position));

        binding.rvDetalles.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDetalles.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.getDetalles().observe(getViewLifecycleOwner(), detalles -> {
            if (detalles != null) {
                adapter.updateData(detalles);
                binding.rvDetalles.scheduleLayoutAnimation();
            } else {
                Toast.makeText(requireContext(), "Lista de detalles vacía", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* private void setupButtons() {
        binding.btnAgregarProducto.setOnClickListener(v -> {
            // Cambiar a fragmento de selección de producto
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container)
                    .addToBackStack(null)
                    .commit();
        });

        // TODO: Agregar funcionalidad de crear pedido si es necesario
        // binding.btnCrearPedido.setOnClickListener(...)
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
