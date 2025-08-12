package com.alexandra.jellybeanstore.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.models.DetallePedido;

import java.util.ArrayList;
import java.util.List;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder> {
    private List<DetallePedido> detalles;
    private final OnRemoveClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public DetallePedidoAdapter(List<DetallePedido> detalles, OnRemoveClickListener listener) {
        this.detalles = detalles != null ? detalles : new ArrayList<>();
        this.listener = listener;
    }


    public void updateData(List<DetallePedido> nuevosDetalles) {
        this.detalles = nuevosDetalles != null ? nuevosDetalles : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetallePedido detalle = detalles.get(position);
        holder.tvProducto.setText(detalle.getProduct().getNombreProducto());
        holder.tvCantidad.setText(String.valueOf(detalle.getCantidad()));

        holder.btnEliminar.setOnClickListener(v -> {
            listener.onRemoveClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return detalles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProducto, tvCantidad;
        ImageButton btnEliminar;

       public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.etNombre);
            tvCantidad = itemView.findViewById(R.id.textCantidad);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }
}

