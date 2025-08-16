package com.alexandra.jellybeanstore.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexandra.jellybeanstore.models.DetallePedido;

import java.util.ArrayList;
import java.util.List;

public class SharedPedidoViewModel extends ViewModel {
    private final MutableLiveData<List<DetallePedido>> detallesPedido = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> itemCount = new MutableLiveData<>(0);
    private final MutableLiveData<Long> clienteId = new MutableLiveData<>();

    public void agregarDetalle(DetallePedido detalle) {
        List<DetallePedido> currentList = detallesPedido.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        // Verificar si el producto ya existe en el pedido
        boolean exists = false;
        for (DetallePedido item : currentList) {
            if (item.getProduct().getIdProducto() == detalle.getProduct().getIdProducto()) {
                item.setCantidad(item.getCantidad() + detalle.getCantidad());
                exists = true;
                break;
            }
        }

        if (!exists) {
            currentList.add(detalle);
        }

        detallesPedido.setValue(currentList);
        itemCount.setValue(currentList.size());
    }
    // Método para establecer el ID del cliente
    public void setClienteId(long id) {
        clienteId.setValue(id);
    }

    // Método para obtener el ID del cliente como LiveData
    public LiveData<Long> getClienteId() {
        return clienteId;
    }
    public void eliminarDetalle(DetallePedido detalle) {
        List<DetallePedido> currentList = detallesPedido.getValue();
        if (currentList != null) {
            List<DetallePedido> newList = new ArrayList<>(currentList);
            newList.remove(detalle);
            detallesPedido.setValue(newList);
        }
    }
    public LiveData<List<DetallePedido>> getDetallesPedido() {
        return detallesPedido;
    }

    public LiveData<Integer> getItemCount() {
        return itemCount;
    }


    public void limpiarPedido() {
        detallesPedido.setValue(new ArrayList<>());
        itemCount.setValue(0);
    }
}