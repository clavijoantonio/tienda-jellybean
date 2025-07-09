package com.alexandra.jellybeanstore.api;

import com.alexandra.jellybeanstore.models.Cliente;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClienteApiService {
    @POST("cliente")
    Call<ResponseBody> crearCliente(@Body ClienteRequest cliente);

   @GET("cliente/{id}")
   Call<Cliente> obtenerCliente(@Path("id") long id);
    @POST("cliente/login")
    Call<ClienteResponse> login(@Body ClienteRequest loginRequest);

}
