package com.alexandra.jellybeanstore.views;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.activityCrearPedido;
import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.ClienteApiService;
import com.alexandra.jellybeanstore.api.ClienteRequest;
import com.alexandra.jellybeanstore.api.ClienteResponse;
import com.alexandra.jellybeanstore.databinding.ActivityAgregarCantidadBinding;
import com.alexandra.jellybeanstore.databinding.ActivityLoginClienteBinding;
import com.alexandra.jellybeanstore.databinding.ActivityViewDetalleProductoBinding;
import com.alexandra.jellybeanstore.models.Cliente;
import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Pedido;
import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.viewmodels.CrearPedidoViewModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class viewDetalleProducto extends AppCompatActivity {
    private ActivityViewDetalleProductoBinding binding;
    private String imageUrl;
    private EditText nombre, password, cantidad;
    private Button btnLogin;
    private static final String TAG = "viewDetalleProducto";
    private long cliente;
    private Pedido pedido = new Pedido();
    private CrearPedidoViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private Product product;
    private int cantidadProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicialización de View Binding
        binding = ActivityViewDetalleProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CrearPedidoViewModel.class);
        EdgeToEdge.enable(this);
        // Obtener SharedPreferences
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);

        // Verificar si ya hay una sesión guardada
        cliente = sharedPreferences.getLong("clienteId", 0);
        if (cliente != 0) {
            pedido.setIdCliente(cliente);
        }


        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        try {
            product = (Product) getIntent().getSerializableExtra("product");
            if (product == null) throw new Exception("Product is null");

            // Mostrar datos del producto
            binding.textNameProduct.setText(getString(R.string.name_product, product.getNombreProducto()));
            binding.textDescription.setText(getString(R.string.description, product.getDescripcionProducto()));
            binding.textPrice.setText(getString(R.string.price, String.valueOf(product.getPrecioProducto())));
            binding.textCantidad.setText(getString(R.string.amount, String.valueOf(product.getCantidaDisponible())));

            imageUrl = product.getFoto();
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.imageView);

        } catch (Exception e) {
            Log.e(TAG, "Error al procesar el producto", e);
            Toast.makeText(this, "Error al mostrar el producto", Toast.LENGTH_LONG).show();
            finish();
        }
        setupViews();
    }
    private void setupViews() {

        // Configurar listeners
        binding.buttonAgregar.setOnClickListener(v ->{
            if(cliente!=0) {
                agregarCantidad();
                cantidadProduct= Integer.parseInt(cantidad.getText().toString().trim());
                DetallePedido detallePedido= new DetallePedido(product,cantidadProduct);
                viewModel.agregarDetalle(detallePedido);
                mostrarAtivityCrearPedido();
             }else{
                mostrarFormularioAgregarCliente();
            }
        });

    }
 //funcion para cargar la vista del cliente
    public void mostrarFormularioAgregarCliente(){
        Dialog dialog = new Dialog(this);
        ActivityLoginClienteBinding dialogBinding = ActivityLoginClienteBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        nombre = dialogBinding.etNombre;
        password = dialogBinding.etIdentificacion;
        btnLogin = dialogBinding.btnLogin;
        validarLogin();
        dialog.show();
    }
    private void validarLogin() {
        btnLogin.setOnClickListener(v -> {
            String nombreCliente = nombre.getText().toString().trim();
            String identificacion = password.getText().toString().trim();

            if (validarInputs(nombreCliente, identificacion)) {
                realizarLogin(nombreCliente, identificacion);
            }
        });
    }
    private boolean validarInputs(String nombreCliente, String identificacion) {
        if (nombreCliente.isEmpty()) {
            nombre.setError("Nombre es requerido");
            return false;
        }

        if (identificacion.isEmpty()) {
            password.setError("Identificacion es requerida");
            return false;
        }

        return true;
    }
    private void realizarLogin(String nombreCliente, String identificacion) {

        btnLogin.setEnabled(false);

        ClienteRequest loginRequest = new ClienteRequest(nombreCliente, identificacion);
        ClienteApiService service = ApiClient.getClient().create(ClienteApiService.class);

        Call<ClienteResponse> call = service.login(loginRequest);
        call.enqueue(new Callback<ClienteResponse>() {
            @Override
            public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                   ClienteResponse loginResponse = response.body();
                    guardarSesion(loginResponse);
                    //irAMainActivity();

                    cliente= loginResponse.getIdCliente();
                    pedido.setIdCliente(cliente);
                    mostrarAtivityCrearPedido();
                } else {
                    mostrarError("Credenciales incorrectas o error del servidor");
                }
            }
            @Override
            public void onFailure(Call<ClienteResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                mostrarError("Error de conexión: " + t.getMessage());
            }
        });
    }
    private void guardarSesion(ClienteResponse loginResponse) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("clienteId", loginResponse.getIdCliente());
        editor.apply();
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
    public void agregarCantidad(){
        Dialog dialog = new Dialog(this);
        ActivityAgregarCantidadBinding dialogBinding = ActivityAgregarCantidadBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        cantidad=dialogBinding.etCantidad;
        dialog.show();
    }

    //funcion para cargar la vista de crear pedido
    public void mostrarAtivityCrearPedido(){
        Intent intent = new Intent(viewDetalleProducto.this, activityCrearPedido.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}