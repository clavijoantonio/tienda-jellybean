package com.alexandra.jellybeanstore.views;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.adapter.JellyBeanAdapter;
import com.alexandra.jellybeanstore.adapter.SelectListener;
import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.viewmodels.ProductViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private ProductViewModel viewModel;
    private JellyBeanAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración EdgeToEdge
        EdgeToEdge.enable(this);
        setupWindowInsets();

        initViewModelAndUI();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Aplicar padding a toda la vista principal
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Aplicar márgenes al RecyclerView para evitar solapamiento con barras del sistema
            recyclerView.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
    }

    private void initViewModelAndUI() {
        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.listrecyleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar adapter con lista vacía
        adapter = new JellyBeanAdapter(new ArrayList<>(), this,this);
        recyclerView.setAdapter(adapter);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Observar cambios en los productos
        viewModel.getProducts().observe(this, products -> {
            if (products != null && !products.isEmpty()) {
                adapter.updateList(products);
            }

        });

        // Observar errores
        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
                Log.e("error",error.toString());
            }
        });

        // Cargar datos
        viewModel.loadProducts();
    }

    @Override
    public void onItemClicked(Product product) {
     Intent intent =new Intent(MainActivity.this, viewDetalleProducto.class);
     intent.putExtra("product",product);
     startActivity(intent);

    }
}