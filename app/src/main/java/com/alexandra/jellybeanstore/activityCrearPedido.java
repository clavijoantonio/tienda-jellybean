package com.alexandra.jellybeanstore;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexandra.jellybeanstore.adapter.DetallePedidoAdapter;
import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.PedidoApiService;
import com.alexandra.jellybeanstore.databinding.ActivityCrearPedidoBinding;
import com.alexandra.jellybeanstore.databinding.ActivityViewDetalleProductoBinding;
import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.repositories.PedidoRepository;
import com.alexandra.jellybeanstore.viewmodels.CrearPedidoViewModel;
import com.alexandra.jellybeanstore.viewmodels.ViewModelFactory;

public class activityCrearPedido extends AppCompatActivity {
    private ActivityCrearPedidoBinding binding;
    private CrearPedidoViewModel viewModel;
    private DetallePedidoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicialización de View Binding
        binding = ActivityCrearPedidoBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);

        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Configurar ViewModel
        PedidoApiService apiService = ApiClient.getClient().create(PedidoApiService.class);
        PedidoRepository repository = new PedidoRepository(apiService);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(repository)).get(CrearPedidoViewModel.class);

        setupViews();
        setupObservers();

    }

    private void setupViews() {
        // Configurar RecyclerView
        adapter = new DetallePedidoAdapter(viewModel.getDetalles(), position -> {
            viewModel.removerDetalle(position);
            adapter.notifyDataSetChanged();
        });

        binding.rvDetalles.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDetalles.setAdapter(adapter);

        // Botón para agregar producto
        binding.btnAgregarProducto.setOnClickListener(v -> {
           // mostrarDialogoAgregarProducto();
        });

        // Botón para crear pedido
       /* binding.btnCrearPedido.setOnClickListener(v -> {

            long clienteId = obtenerClienteSeleccionado(); // Implementar según tu Spinner

            if (validarFormulario( clienteId)) {
                viewModel.crearPedido( clienteId);
            }
        });*/
    }

        private void setupObservers () {
            viewModel.getIsLoading().observe(this, isLoading -> {
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.btnCrearPedido.setEnabled(!isLoading);
            });

            viewModel.getError().observe(this, error -> {
                if (error != null) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
       /* private void mostrarDialogoAgregarProducto () {
            Dialog dialog = new Dialog(this);
            DialogAgregarProductoBinding dialogBinding = DialogAgregarProductoBinding.inflate(getLayoutInflater());
            dialog.setContentView(dialogBinding.getRoot());

            // Configurar spinner de productos

            dialogBinding.btnAgregar.setOnClickListener(v -> {
                long productoId = obtenerProductoSeleccionado(); // Implementar según tu Spinner
                int cantidad = Integer.parseInt(dialogBinding.etCantidad.getText().toString());

                if (cantidad > 0) {
                    viewModel.agregarDetalle(new DetallePedido(productoId, cantidad));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Cantidad inválida", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        }*/
    }
