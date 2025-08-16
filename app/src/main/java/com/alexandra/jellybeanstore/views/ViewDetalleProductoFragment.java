package com.alexandra.jellybeanstore.views;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.ClienteApiService;
import com.alexandra.jellybeanstore.api.ClienteRequest;
import com.alexandra.jellybeanstore.api.ClienteResponse;
import com.alexandra.jellybeanstore.databinding.ActivityAgregarCantidadBinding;
import com.alexandra.jellybeanstore.databinding.ActivityLoginClienteBinding;
import com.alexandra.jellybeanstore.databinding.FragmentViewDetalleProductoBinding;
import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.viewmodels.SharedPedidoViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDetalleProductoFragment extends Fragment {
    private FragmentViewDetalleProductoBinding binding;
    private SharedPedidoViewModel sharedViewModel;
    private Product product;
    private long cliente;
    private SharedPreferences sharedPreferences;
    private NavController navController;

    public ViewDetalleProductoFragment() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentViewDetalleProductoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedPedidoViewModel.class);

        sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE);

        cliente = sharedPreferences.getLong("clienteId", 0);
        // Obtener NavController
        NavController navController = Navigation.findNavController(view);

        if (cliente != 0) {
            sharedViewModel.setClienteId(cliente);
        }

        binding.fabVerPedido.setOnClickListener(v -> {
            // Navegar usando el fragment_container como host
            navController.navigate(R.id.action_to_resumenPedidoFragment);
        });



        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable("product");
            if (product != null) {
                mostrarDatosProducto(product);
            }
        }

        binding.buttonAgregar.setOnClickListener(v -> {
            if (cliente != 0) {

                mostrarDialogoCantidad();
            } else {
                mostrarDialogoLogin();
            }
        });
        // Observar cambios en el pedido
        sharedViewModel.getItemCount().observe(getViewLifecycleOwner(), count -> {
            if (count > 0) {
                mostrarBotonPedido();
            }
        });
    }

    private void mostrarDatosProducto(Product product) {
        binding.textNameProduct.setText(product.getNombreProducto());
        binding.textDescription.setText(product.getDescripcionProducto());
        binding.textPrice.setText(String.valueOf(product.getPrecioProducto()));
        binding.textCantidad.setText(String.valueOf(product.getCantidaDisponible()));
        binding.textId.setText(String.valueOf(product.getIdProducto()));

        Picasso.get()
                .load(product.getFoto())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageView);
    }

    private void mostrarDialogoCantidad() {
        Dialog dialog = new Dialog(requireContext());
        ActivityAgregarCantidadBinding dialogBinding = ActivityAgregarCantidadBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        dialogBinding.btnEnviar.setOnClickListener(v -> {
            try {
                int cantidad = Integer.parseInt(dialogBinding.etCantidad.getText().toString());
                if (cantidad <= 0) {
                    Toast.makeText(requireContext(), "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cantidad > product.getCantidaDisponible()) {
                    Toast.makeText(requireContext(), "No hay suficiente stock", Toast.LENGTH_SHORT).show();
                    return;
                }
                DetallePedido detalle = new DetallePedido(product, cantidad);
                sharedViewModel.agregarDetalle(detalle);
                // Feedback visual
                Snackbar.make(binding.getRoot(), "Producto agregado al pedido", Snackbar.LENGTH_LONG)
                        .setAction("VER PEDIDO", v1 -> {
                            // Navegar al fragmento de resumen del pedido
                            navController.navigate(R.id.action_to_resumenPedidoFragment);
                        })
                        .show();
                dialog.dismiss();

            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Cantidad inválida", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void mostrarDialogoLogin() {
        Dialog dialog = new Dialog(requireContext());
        ActivityLoginClienteBinding dialogBinding = ActivityLoginClienteBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        EditText nombre = dialogBinding.etNombre;
        EditText password = dialogBinding.etIdentificacion;
        Button btnLogin = dialogBinding.btnLogin;

        btnLogin.setOnClickListener(v -> {
            String nombreCliente = nombre.getText().toString().trim();
            String identificacion = password.getText().toString().trim();

            if (nombreCliente.isEmpty()) {
                nombre.setError("Nombre requerido");
                return;
            }
            if (identificacion.isEmpty()) {
                password.setError("Identificación requerida");
                return;
            }

            btnLogin.setEnabled(false);

            ClienteApiService service = ApiClient.getClient().create(ClienteApiService.class);
            ClienteRequest loginRequest = new ClienteRequest(nombreCliente, identificacion);

            service.login(loginRequest).enqueue(new Callback<ClienteResponse>() {
                @Override
                public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {
                    btnLogin.setEnabled(true);
                    if (response.isSuccessful() && response.body() != null) {
                        ClienteResponse clienteResponse = response.body();
                        long clienteId = response.body().getIdCliente();
                        sharedViewModel.setClienteId(clienteId);
                        sharedPreferences.edit().putLong("clienteId", clienteResponse.getIdCliente()).apply();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Login inválido", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClienteResponse> call, Throwable t) {
                    btnLogin.setEnabled(true);
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }
    private void mostrarBotonPedido() {
        // Mostrar un botón flotante o en la barra inferior para ver el pedido
        binding.fabVerPedido.setVisibility(View.VISIBLE);
        binding.fabVerPedido.setOnClickListener(v -> {
            // Navegar al fragmento de resumen del pedido
            navController.navigate(R.id.action_to_resumenPedidoFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
