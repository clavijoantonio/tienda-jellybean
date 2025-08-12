package com.alexandra.jellybeanstore;

import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexandra.jellybeanstore.adapter.JellyBeanAdapter;
import com.alexandra.jellybeanstore.adapter.SelectListener;
import com.alexandra.jellybeanstore.databinding.FragmentMainBinding;
import com.alexandra.jellybeanstore.models.Product;
import com.alexandra.jellybeanstore.viewmodels.ProductViewModel;

import java.util.ArrayList;

public class MainFragment extends Fragment implements SelectListener {

    private FragmentMainBinding binding;
    private ProductViewModel viewModel;
    private JellyBeanAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupViewModel();
        setupWindowInsets();
    }

    private void setupRecyclerView() {
        adapter = new JellyBeanAdapter(new ArrayList<>(), requireContext(), this);
        binding.listrecyleview.setAdapter(adapter);
        binding.listrecyleview.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null && !products.isEmpty()) {
                adapter.updateList(products);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                Log.e("MainFragment", error);
            }
        });

        viewModel.loadProducts();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            binding.listrecyleview.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemClicked(Product product) {
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        Navigation.findNavController(requireView()).navigate(R.id.action_main_to_detail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}