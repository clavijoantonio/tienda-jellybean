package com.alexandra.jellybeanstore.repositories;

import com.alexandra.jellybeanstore.api.DetalleRequest;
import com.alexandra.jellybeanstore.api.PedidoApiService;
import com.alexandra.jellybeanstore.api.PedidoRequest;
import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Pedido;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepository {
    private PedidoApiService apiService;

    public PedidoRepository(PedidoApiService apiService) {
        this.apiService = apiService;
    }

   /* public void crearPedido(Pedido pedido, final PedidoCallback callback) {
        // Convertir a request
        List<DetalleRequest> detallesRequest = new ArrayList<>();
        for (DetallePedido detalle : pedido.getDetalles()) {
            detallesRequest.add(new DetalleRequest(
                    detalle.getId(),
                    detalle.getCantidad()
            ));
        }

        PedidoRequest request = new PedidoRequest(
                pedido.getIdCliente(),
                PedidoRequest
        );

        apiService.crearPedido(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Error al crear pedido");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }*/

    public interface PedidoCallback {
        void onSuccess();
        void onError(String error);
    }
}
