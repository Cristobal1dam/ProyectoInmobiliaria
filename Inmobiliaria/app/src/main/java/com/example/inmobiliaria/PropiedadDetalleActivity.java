package com.example.inmobiliaria;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.example.inmobiliaria.Model.FavResponse;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Model.PropiedadDetalle;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.Model.ResponsePropiedad;
import com.example.inmobiliaria.Services.PhotoService;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;
import com.google.android.gms.maps.model.LatLng;

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
    ImageView photo,map,delete;
    PropiedadDetalle propiedad;
    PropiedadViewModel propiedadViewModel;

   // SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propiedad_detalle);

        findViews();
        propiedadViewModel = ViewModelProviders.of(PropiedadDetalleActivity.this)
                .get(PropiedadViewModel.class);
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

                   if(propiedad.getOwnerId().getName().equals(UtilUser.getNombre(PropiedadDetalleActivity.this))) {
                       photo.setVisibility(View.VISIBLE);
                       delete.setVisibility(View.VISIBLE);
                   }

                    map.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            propiedadViewModel.setIrMapa(true);
                            String[] latlong =  propiedad.getLoc().split(",");
                            double latitude = Double.parseDouble(latlong[0].trim());
                            double longitude = Double.parseDouble(latlong[1].trim());
                            propiedadViewModel.setposicionPropiedad(new LatLng(latitude,longitude));
                            Intent i = new Intent(PropiedadDetalleActivity.this, MainActivity.class);
                            i.putExtra("loc",propiedad.getLoc());
                            startActivity(i);
                        }
                    });



                }
            }



            @Override
            public void onFailure(Call<ResponsePropiedad> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(PropiedadDetalleActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PropiedadDetalleActivity.this);


                builder.setMessage("¿Esta seguro que quiere borrarla?")
                        .setTitle("Borrar propiedad");


                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        PropiedadService service = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(PropiedadDetalleActivity.this), TipoAutenticacion.JWT);

                        Call<FavResponse> call = service.deleteProperty(propiedad.getId());

                        call.enqueue(new Callback<FavResponse>() {

                            @Override
                            public void onResponse(Call<FavResponse> call, Response<FavResponse> response) {

                                if(response.code() != 204){
                                    Toast.makeText(PropiedadDetalleActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PropiedadDetalleActivity.this, "Eliminado con éxito", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(PropiedadDetalleActivity.this, MainActivity.class));

                                }



                            }

                            @Override
                            public void onFailure(Call<FavResponse> call, Throwable t) {
                                Log.e("NetworkFailure", t.getMessage());
                                Toast.makeText(PropiedadDetalleActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                            }


                        });



                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();

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
        map = findViewById(R.id.imageViewMap);
        delete = findViewById(R.id.imageViewDelete);
    }








}
