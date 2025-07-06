package com.alexandra.jellybeanstore.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.repositories.ProductoRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private ProductoRepository repository;

    public ProductViewModel() {
        repository = new ProductoRepository();
        loadProducts();
    }

    public void loadProducts() {
        repository.getProducts(new ProductoRepository.ProductCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                productsLiveData.postValue(products);
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }
        });
    }

    public LiveData<List<Product>> getProducts() {
        return productsLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }
}
