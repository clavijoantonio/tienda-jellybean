package com.alexandra.jellybeanstore.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoApiService {
    @POST("pedidos")
    Call<ResponseBody> crearPedido(@Body PedidoRequest pedido);

   // @GET("pedidos/{id}")
  // Call<PedidoResponse> obtenerPedido(@Path("id") long id);


}
