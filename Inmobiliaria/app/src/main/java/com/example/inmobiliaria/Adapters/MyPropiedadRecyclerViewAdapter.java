package com.example.inmobiliaria.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inmobiliaria.Fragments.PropiedadFragment.OnListFragmentInteractionListener;
import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.R;


import java.util.List;


public class MyPropiedadRecyclerViewAdapter extends RecyclerView.Adapter<MyPropiedadRecyclerViewAdapter.ViewHolder> {

    private List<Propiedad> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context ctx;

    public MyPropiedadRecyclerViewAdapter(Context contexto,List<Propiedad> items, OnListFragmentInteractionListener listener) {
        ctx = contexto;
        mValues = items;
        mListener = listener;
    }
    public void setNuevasPropiedades(List<Propiedad> nuevosComentarios) {
        this.mValues = nuevosComentarios;
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


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titulo,precio,habitaciones,direccion;
        public final ImageView favorito;
        public Propiedad mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = view.findViewById(R.id.textViewTitulo);
            precio = view.findViewById(R.id.textViewPrecio);
            habitaciones = view.findViewById(R.id.textViewNumeroHab);
            direccion = view.findViewById(R.id.textViewDireccion);
            favorito = view.findViewById(R.id.imageViewFavorito);


        }

    }
}
