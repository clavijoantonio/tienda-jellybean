package com.alexandra.jellybeanstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.models.Product;

import java.util.List;

public class JellyBeanAdapter extends RecyclerView.Adapter<JellyBeansViewHolder> {
    private List<Product> productList;
    private LayoutInflater inflater;
    private Context context;
    private SelectListener listener;
    public JellyBeanAdapter(List<Product> productList, Context context, SelectListener listener) {
        this.productList = productList;
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.listener=listener;
    }
    // Método para actualizar la lista
    public void updateList(List<Product> newList) {
        productList = newList;
        notifyDataSetChanged(); // Notificar al RecyclerView que los datos cambiaron
    }

    @NonNull
    @Override
    public JellyBeansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=inflater.inflate(R.layout.list_element, null);
        return new JellyBeansViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JellyBeansViewHolder holder, int position) {
     var item= productList.get(position);
     holder.bigData(item);
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             listener.onItemClicked(productList.get(position));
         }
     });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
