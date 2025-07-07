package com.alexandra.jellybeanstore.views;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.activityCrearPedido;
import com.alexandra.jellybeanstore.databinding.ActivityViewDetalleProductoBinding;
import com.alexandra.jellybeanstore.models.Pedido;
import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.viewmodels.CrearPedidoViewModel;
import com.squareup.picasso.Picasso;

public class viewDetalleProducto extends AppCompatActivity {
    private ActivityViewDetalleProductoBinding binding;
    private String imageUrl;
    private static final String TAG = "viewDetalleProducto";

    private Pedido pedido = new Pedido();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicialización de View Binding
        binding = ActivityViewDetalleProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Product product;
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
      long cliente = pedido.getClienteId();
        // Configurar listeners
        binding.buttonAgregar.setOnClickListener(v ->{

            if(cliente==0) {
                        mostrarFormularioAgregarCliente();
             }else{
                Toast.makeText(this, "error no debe presntrar esto", Toast.LENGTH_LONG).show();
            }
        });

    }
 //funcion para cargar la vista del cliente
    public void mostrarFormularioAgregarCliente(){
        Dialog dialog = new Dialog(this);
        DialogAgregarProductoBinding dialogBinding = DialogAgregarProductoBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

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