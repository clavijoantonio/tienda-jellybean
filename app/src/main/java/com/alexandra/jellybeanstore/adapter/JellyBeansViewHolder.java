package com.alexandra.jellybeanstore.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandra.jellybeanstore.R;
import com.alexandra.jellybeanstore.models.Product;
import com.squareup.picasso.Picasso;

public class JellyBeansViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView name,descripcion,precio;
    String imageUrl;
    View vista;
    CardView cardView;
    public JellyBeansViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView= itemView.findViewById(R.id.Imageview);
        name=itemView.findViewById(R.id.nameProductTextview);
        descripcion=itemView.findViewById(R.id.descripcionTextview);
        precio=itemView.findViewById(R.id.precioTextview);
        cardView=itemView.findViewById(R.id.cv);

    }

    void bigData (final Product item){
    name.setText(item.getNombreProducto());
    descripcion.setText(item.getDescripcionProducto());
    precio.setText(Double.toString(item.getPrecioProducto()));
    imageUrl=item.getFoto();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // Imagen mientras se carga
                .error(R.drawable.ic_launcher_foreground) // Imagen en caso de error
                .into(imageView);

    }


}
