package com.example.inmobiliaria.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.Fragments.PropiedadFragment.OnListFragmentInteractionListener;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Model.FavResponse;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.PropiedadDetalleActivity;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPropiedadRecyclerViewAdapter extends RecyclerView.Adapter<MyPropiedadRecyclerViewAdapter.ViewHolder> {

    private List<PropiedadFoto> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context ctx;

    public MyPropiedadRecyclerViewAdapter(Context contexto,List<PropiedadFoto> items, OnListFragmentInteractionListener listener) {
        ctx = contexto;
        mValues = items;
        mListener = listener;
    }
    public void setNuevasPropiedades(List<PropiedadFoto> nuevasPropiedades) {
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
        holder.provincia.setText(holder.mItem.getProvince());
        holder.municipio.setText(holder.mItem.getCity());
        holder.habitaciones.setText(Integer.toString(holder.mItem.getRooms()));
        holder.precio.setText(Integer.toString(( holder.mItem.getPrice())));
        holder.size.setText(Integer.toString(holder.mItem.getSize()));
        if (holder.mItem.getPhotos() != null) {
            holder.imgProp.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(ctx)
                    .load(holder.mItem.getPhotos().get(0))
                    .into(holder.imgProp);
        }
        PropiedadViewModel propiedadViewModel = ViewModelProviders.of((FragmentActivity) ctx)
                .get(PropiedadViewModel.class);

        if(propiedadViewModel.getShowStar()){

        if(UtilToken.getToken(ctx) != null) {


                holder.isFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFav(holder.mItem.getId());
                        holder.isFav.setVisibility(View.GONE);
                        holder.isNotFav.setVisibility(View.VISIBLE);
                    }
                });

                holder.isNotFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addFav(holder.mItem.getId());
                        holder.isNotFav.setVisibility(View.GONE);
                        holder.isFav.setVisibility(View.VISIBLE);
                    }
                });



            if (holder.mItem.isFav()) {
                holder.isNotFav.setVisibility(View.GONE);
            }else
                holder.isFav.setVisibility(View.GONE);

        }else {
            holder.isNotFav.setVisibility(View.GONE);
            holder.isFav.setVisibility(View.GONE);
        }
        }else{
            holder.isFav.setVisibility(View.GONE);
            holder.isNotFav.setVisibility(View.GONE);
        }


        holder.imgProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, PropiedadDetalleActivity.class);
                i.putExtra("id", holder.mItem.getId() );
                ctx.startActivity(i);
            }
        });

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
        public final TextView titulo,precio,habitaciones,provincia,size,municipio;
        public final ImageView isFav,isNotFav,imgProp;
        public PropiedadFoto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = view.findViewById(R.id.textViewTitulo);
            precio = view.findViewById(R.id.textViewPrecio);
            habitaciones = view.findViewById(R.id.textViewNumeroHab);
            provincia = view.findViewById(R.id.textViewProvinciaList);
            municipio = view.findViewById(R.id.textViewCiudadList);
            size = view.findViewById(R.id.textViewSize);
            isFav = view.findViewById(R.id.imageViewIsFav);
            isNotFav = view.findViewById(R.id.imageViewIsNotFav);
            imgProp = view.findViewById(R.id.imageViewPropiedad);


        }

    }

    private void addFav(String id) {
        PropiedadService service = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<FavResponse> call = service.addFav(id);

        call.enqueue(new Callback<FavResponse>() {

            @Override
            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(ctx, "Añadido a favoritos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FavResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });
    }


    private void deleteFav(String id) {
        PropiedadService service = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<FavResponse> call = service.deleteFav(id);

        call.enqueue(new Callback<FavResponse>() {

            @Override
            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(ctx, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FavResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });
    }
}


