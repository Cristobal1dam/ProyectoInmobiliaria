package com.example.inmobiliaria;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.Adapters.MyPropiedadRecyclerViewAdapter;
import com.example.inmobiliaria.Adapters.ViewPagerAdapter;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Generator.UtilUser;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.Model.ResponsePropiedad;
import com.example.inmobiliaria.Services.PhotoService;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropiedadDetalleActivity extends AppCompatActivity {

    TextView propietario,habs,descripcion,titulo,ciudad,precio,size,direccion,position;
    String idProp;
    ViewPager viewPager;
    ImageView photo;
    PropiedadFoto propiedad;

   // SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propiedad_detalle);

        findViews();
        Bundle extras = getIntent().getExtras();
        idProp = extras.getString("id");

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(PropiedadDetalleActivity.this, AddImagenActivity.class);
                    i.putExtra("id", idProp);
                    PropiedadDetalleActivity.this.startActivity(i);



            }
        });


        PropiedadService service = ServiceGenerator.createService(PropiedadService.class);
        Call<ResponsePropiedad> call = service.getPropiedad(idProp);

        call.enqueue(new Callback<ResponsePropiedad>() {

            @Override
            public void onResponse(Call<ResponsePropiedad> call, Response<ResponsePropiedad> response) {
                if (response.code() != 200) {
                    Toast.makeText(PropiedadDetalleActivity.this, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    propiedad = response.body().getRows();

                    propietario.setText(propiedad.getOwnerId().getName());
                    habs.setText(Integer.toString(propiedad.getRooms()));
                    descripcion.setText(propiedad.getDescription());
                    titulo.setText(propiedad.getTitle());
                    ciudad.setText(propiedad.getProvince());
                    precio.setText(Integer.toString(propiedad.getPrice()));
                    size.setText(Integer.toString(propiedad.getSize()));
                    direccion.setText(propiedad.getAddress());
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(PropiedadDetalleActivity.this, propiedad.getPhotos(), position);
                    viewPager.setAdapter(viewPagerAdapter);

                   if(!(propiedad.getOwnerId().getName().equals(UtilUser.getNombre(PropiedadDetalleActivity.this))) || UtilToken.getToken(PropiedadDetalleActivity.this) == null)
                        photo.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ResponsePropiedad> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(PropiedadDetalleActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });


    }

    private void findViews() {
        propietario = findViewById(R.id.textViewPropDetalle);
        habs = findViewById(R.id.textViewHabDetalle);
        descripcion = findViewById(R.id.textViewDescripcionDetalle);
        titulo = findViewById(R.id.textViewTituloDetalle);
        ciudad = findViewById(R.id.textViewCiudadDetalle);
        precio = findViewById(R.id.textViewPrecioDetalle);
        size = findViewById(R.id.textViewSizeDetalle);
        direccion = findViewById(R.id.textViewDireccionDetalle);
        viewPager = findViewById(R.id.viewPager);
        position = findViewById(R.id.textViewPosition);
        photo = findViewById(R.id.imageViewAddPhoto);
    }








}
