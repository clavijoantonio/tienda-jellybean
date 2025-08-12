package com.alexandra.jellybeanstore.repositories;

import android.widget.Toast;

import com.alexandra.jellybeanstore.api.ApiClient;
import com.alexandra.jellybeanstore.api.ProductoApiService;
import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoRepository {
    private ProductoApiService apiService;

    public ProductoRepository() {
        apiService = ApiClient.getClient().create(ProductoApiService.class);
    }

    public void getProducts(final ProductCallback callback) {
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }
    public void getProductsId(final ProductCallback2 callback,long id) {

        Call<Product> call = apiService.getProductById(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }

            }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }

    public interface ProductCallback {
        void onSuccess(List<Product> products);

        void onError(String error);
    }
    public interface ProductCallback2 {
        void onSuccess(Product products);
        void onError(String error);
    }

}
