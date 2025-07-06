package com.alexandra.jellybeanstore.viewmodels;

import android.support.annotation.NonNull;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alexandra.jellybeanstore.repositories.PedidoRepository;

public class ViewModelFactory implements ViewModelProvider.Factory{
    private PedidoRepository repository;

    public ViewModelFactory(PedidoRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CrearPedidoViewModel(repository);
    }
}
