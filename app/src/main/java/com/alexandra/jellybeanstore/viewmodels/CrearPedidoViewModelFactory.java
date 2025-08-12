package com.alexandra.jellybeanstore.viewmodels;

import android.support.annotation.NonNull;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alexandra.jellybeanstore.repositories.PedidoRepository;
import com.alexandra.jellybeanstore.repositories.ProductoRepository;

public class CrearPedidoViewModelFactory implements ViewModelProvider.Factory{
    private final PedidoRepository repository;
    private final ProductoRepository productoRepository;
    private long id;
    public CrearPedidoViewModelFactory(PedidoRepository repository, ProductoRepository productoRepository) {
        this.productoRepository=productoRepository;
        this.repository = repository;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CrearPedidoViewModel.class)) {
            //noinspection unchecked
            return (T) new CrearPedidoViewModel(repository, productoRepository,id);
        }
        throw new IllegalArgumentException("ViewModel class desconocida");
    }
}
