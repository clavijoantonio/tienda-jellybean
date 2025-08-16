package com.alexandra.jellybeanstore.views;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexandra.jellybeanstore.models.DetallePedido;
import com.alexandra.jellybeanstore.models.Pedido;
import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.repositories.PedidoRepository;
import com.alexandra.jellybeanstore.repositories.ProductoRepository;

import java.util.ArrayList;
import java.util.List;

public class CrearPedidoViewModel extends ViewModel {
    private final PedidoRepository repository;
    //private MutableLiveData<List<Cliente>> clientes = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private List<DetallePedido> detalles = new ArrayList<>();
    private final ProductoRepository productoRepository;
    private final MutableLiveData <List<DetallePedido>> _detalles = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Product> _currentProduct = new MutableLiveData<>();
    private long id;

    public CrearPedidoViewModel(PedidoRepository repository,ProductoRepository productoRepository,long id) {
        this.repository = repository;
        this.productoRepository=productoRepository;
        this.id=id;
        // loadProducts(id);
        cargarClientes();
    }


    // public LiveData<List<Cliente>> getClientes() { return clientes; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    // public List<DetallePedido> getDetalles() { return detalles; }





    public void agregarDetalleConCantidad(long productId, int cantidad) {
        if (cantidad <= 0) {
            error.setValue("La cantidad debe ser mayor a cero");
            return;
        }

        isLoading.setValue(true);

        productoRepository.getProductsId(new ProductoRepository.ProductCallback2() {


            @Override
            public void onSuccess(Product products) {
                agregarProductoComoDetalle(products, cantidad);
                isLoading.postValue(false);
            }

            @Override
            public void onError(String errorMsg) {
                error.postValue("Error al cargar producto: " + errorMsg);
                isLoading.postValue(false);
            }
        }, productId);
    }

    private void agregarProductoComoDetalle(Product product, int cantidad) {
        List<DetallePedido> detallesActuales = _detalles.getValue();
        if (detallesActuales == null) {
            detallesActuales = new ArrayList<>();
        }

        // Crear un nuevo detalle sin verificar duplicados
        DetallePedido nuevoDetalle = new DetallePedido(product, cantidad);
        detallesActuales.add(nuevoDetalle); // Siempre se agrega como nuevo elemento
        _detalles.setValue(detallesActuales);
    }

    public LiveData<List<DetallePedido>> getDetalles() {
        return _detalles;
    }

    public void removerDetalle(int position) {
        List<DetallePedido> actual = _detalles.getValue();
        if (actual != null && position >= 0 && position < actual.size()) {
            actual.remove(position);
            _detalles.setValue(actual);
        }
    }
  /*  public void loadProducts(long id) {

        productoRepository.getProductsId(new ProductoRepository.ProductCallback2() {
            @Override
            public void onSuccess(List<Product> product) {

                productsLiveData.postValue(product);
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }
        },id);

    }*/

    public LiveData<List<Product>> getProducts() {
        return productsLiveData;
    }
    // Método para actualizar los detalles


    // Métodos para agregar/remover
   /* public void loadTestData() {
        List<DetallePedido> testData = new ArrayList<>();
        testData.add(new DetallePedido(new Product(1, "Producto prueba", 10.99), 2));
        _detalles.setValue(testData);
    }*/
    public void agregarDetalle(DetallePedido detalle) {
        List<DetallePedido> actual = _detalles.getValue();
        if (actual == null) {
            actual = new ArrayList<>();
        }
        actual.add(detalle);

        _detalles.setValue(actual);
        Log.d("ViewModel", "Detalle agregado. Tamaño: " + actual.size());
    }


    public void setDetalles(List<DetallePedido> nuevosDetalles) {
        _detalles.setValue(nuevosDetalles);
    }



    /* public void crearPedido( long idCliente) {
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
     }*/
    public void setClienteId(long idCliente) {
        this.id = idCliente;
    }
    private void cargarClientes() {
        // Implementar carga de clientes desde API o base de datos local
    }
}
