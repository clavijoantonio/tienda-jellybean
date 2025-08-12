package com.alexandra.jellybeanstore.api;

import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoApiService {

    @GET("producto")
    Call<List<Product>> getProducts();

    @GET("producto/{id}")
    Call<Product> getProductById(@Path("id") long id);
}
