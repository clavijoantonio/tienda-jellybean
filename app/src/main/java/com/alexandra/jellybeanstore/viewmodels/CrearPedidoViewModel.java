package com.alexandra.jellybeanstore.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Pedido;
import com.alexandra.jellybeanstore.repositories.PedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class CrearPedidoViewModel extends ViewModel{
    private PedidoRepository repository;
    //private MutableLiveData<List<Cliente>> clientes = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private List<DetallePedido> detalles = new ArrayList<>();

    public CrearPedidoViewModel(PedidoRepository repository) {
        this.repository = repository;
        cargarClientes();
    }
     public CrearPedidoViewModel(){
     }

   // public LiveData<List<Cliente>> getClientes() { return clientes; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public List<DetallePedido> getDetalles() { return detalles; }

    public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
    }

    public void removerDetalle(int position) {
        if (position >= 0 && position < detalles.size()) {
            detalles.remove(position);
        }
    }

    public void crearPedido( long idCliente) {
        isLoading.setValue(true);

        Pedido pedido = new Pedido(idCliente, new ArrayList<>(detalles));

        repository.crearPedido(pedido, new PedidoRepository.PedidoCallback() {
            @Override
            public void onSuccess() {
                isLoading.postValue(false);
                error.postValue(null);
            }

            @Override
            public void onError(String errorMsg) {
                isLoading.postValue(false);
                error.postValue(errorMsg);
            }
        });
    }

    private void cargarClientes() {
        // Implementar carga de clientes desde API o base de datos local
    }
}
