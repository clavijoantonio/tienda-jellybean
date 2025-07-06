package com.alexandra.jellybeanstore.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.models.DetallePedido;

import java.util.List;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder>  {
    private List<DetallePedido> detalles;
    private OnRemoveClickListener onRemoveClickListener;

    public DetallePedidoAdapter(List<DetallePedido> detalles, OnRemoveClickListener listener) {
        this.detalles = detalles;
        this.onRemoveClickListener = listener;
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_detalle_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetallePedido detalle = detalles.get(position);

        // Aquí deberías cargar el nombre del producto basado en productoId
        holder.tvProducto.setText("Producto ID: " + detalle.getProductoId());
        holder.tvCantidad.setText("Cantidad: " + detalle.getCantidad());

        holder.btnEliminar.setOnClickListener(v -> {
            onRemoveClickListener.onRemoveClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return detalles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProducto, tvCantidad;
        Button btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
