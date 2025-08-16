package com.alexandra.jellybeanstore.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandra.jellybeanstore.databinding.ItemPedidoBinding;
import com.alexandra.jellybeanstore.models.DetallePedido;

public class PedidoAdapter extends ListAdapter<DetallePedido, PedidoAdapter.ViewHolder> {
    private  OnDeleteClickListener onDeleteClickListener;
    public PedidoAdapter(OnDeleteClickListener listener) {
        super(DIFF_CALLBACK);
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPedidoBinding binding = ItemPedidoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPedidoBinding binding;

        ViewHolder(ItemPedidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DetallePedido detalle) {
            binding.textNombre.setText(detalle.getProduct().getNombreProducto());
            binding.textPrecio.setText(String.format("$%.2f", detalle.getProduct().getPrecioProducto()));
            binding.textCantidad.setText(String.valueOf(detalle.getCantidad()));
            binding.textSubtotal.setText(String.format("$%.2f",
                    detalle.getProduct().getPrecioProducto() * detalle.getCantidad()));
            // Configurar el click listener para el botón de eliminar
            binding.btnEliminar.setOnClickListener(v -> {
                if (onDeleteClickListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener.onDeleteClick(getItem(position));
                    }
                }
            });
        }
    }

    private static final DiffUtil.ItemCallback<DetallePedido> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DetallePedido>() {
                @Override
                public boolean areItemsTheSame(@NonNull DetallePedido oldItem, @NonNull DetallePedido newItem) {
                    return oldItem.getProduct().getIdProducto() == newItem.getProduct().getIdProducto();
                }

                @Override
                public boolean areContentsTheSame(@NonNull DetallePedido oldItem, @NonNull DetallePedido newItem) {
                    return oldItem.equals(newItem);
                }
            };
    public interface OnDeleteClickListener {
        void onDeleteClick(DetallePedido detalle);
    }
}
