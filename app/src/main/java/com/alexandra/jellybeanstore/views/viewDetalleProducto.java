package com.alexandra.jellybeanstore.views;

import static android.content.ContentValues.TAG;

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
import com.alexandra.jellybeanstore.models.Product;
import com.squareup.picasso.Picasso;

public class viewDetalleProducto extends AppCompatActivity {
    private ActivityViewDetalleProductoBinding binding;
    private String imageUrl;
    private static final String TAG = "viewDetalleProducto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
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
    }
    private void setupViews() {

        // Configurar listeners
       // binding.buttonBuy.setOnClickListener(v -> mostrarFormularioAgregarCliente());
        binding.buttonBuy.setOnClickListener(v -> {
            Intent intent = new Intent(this, activityCrearPedido.class);
            startActivity(intent);
        });
    }

    public void mostrarFormularioAgregarCliente(){

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}