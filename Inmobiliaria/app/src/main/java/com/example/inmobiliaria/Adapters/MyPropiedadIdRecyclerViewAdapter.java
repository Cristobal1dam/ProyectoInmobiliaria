package com.example.inmobiliaria.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.Fragments.PropiedadFragment.OnListFragmentInteractionListener;
import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.Model.PropiedadId;
import com.example.inmobiliaria.Model.PropiedadIdFoto;
import com.example.inmobiliaria.R;

import java.util.List;


public class MyPropiedadIdRecyclerViewAdapter extends RecyclerView.Adapter<MyPropiedadIdRecyclerViewAdapter.ViewHolder> {

    private List<PropiedadIdFoto> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context ctx;

    public MyPropiedadIdRecyclerViewAdapter(Context contexto, List<PropiedadIdFoto> items, OnListFragmentInteractionListener listener) {
        ctx = contexto;
        mValues = items;
        mListener = listener;
    }
    public void setNuevasPropiedades(List<PropiedadIdFoto> nuevasPropiedades) {
        this.mValues = nuevasPropiedades;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_propiedad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titulo.setText(holder.mItem.getTitle());
        holder.direccion.setText(holder.mItem.getAddress());
        holder.habitaciones.setText(Integer.toString(holder.mItem.getRooms()));
        holder.precio.setText(Integer.toString(( holder.mItem.getPrice())));
        if (holder.mItem.getPhotos() != null) {
            Glide.with(ctx)
                    .load(holder.mItem.getPhotos().get(0))
                    .into(holder.imgProp);
        }





    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titulo,precio,habitaciones,direccion;
        public final ImageView favorito,imgProp;
        public PropiedadIdFoto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = view.findViewById(R.id.textViewTitulo);
            precio = view.findViewById(R.id.textViewPrecio);
            habitaciones = view.findViewById(R.id.textViewNumeroHab);
            direccion = view.findViewById(R.id.textViewDireccion);
            favorito = view.findViewById(R.id.imageViewFavorito);
            imgProp = view.findViewById(R.id.imageViewPropiedad);


        }

    }
}
