package com.example.inmobiliaria;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Geography.Data.GeographySpain;
import com.example.inmobiliaria.Geography.Geocode;
import com.example.inmobiliaria.Geography.Selector.GeographyListener;
import com.example.inmobiliaria.Geography.Selector.GeographySelector;
import com.example.inmobiliaria.Model.AddPropiedad;
import com.example.inmobiliaria.Model.Category;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.Services.CategoryService;
import com.example.inmobiliaria.Services.PropiedadService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;



import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPropiedadActivity extends AppCompatActivity implements GeographyListener {

    private GoogleMap googleMaps;
    private MapView mMapView;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng miPosicion,center;
    private AddPropiedad propiedad;
    private EditText size,titulo,precio,descripcion,habitaciones,direccion,zipcode;
    private TextView vistaProvincia,vistaRegion,vistaCiudad;
    private List<Category> listaCategorias;
    private Spinner categorias;
    private NestedScrollView scroll;
    private CardView cardDireccion;
    private Button ubicacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_propiedad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(AddPropiedadActivity.this);
        findViews();
        cargarSpinerCategorias();
        FloatingActionButton fab = findViewById(R.id.fab);

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeographySelector gs = new GeographySelector(AddPropiedadActivity.this);
                gs.setOnGeograpySelectedListener(AddPropiedadActivity.this);
                FragmentManager fm = getSupportFragmentManager();
                gs.show(fm, "geographySelector");
            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPropiedad(propiedad);
            }
        });




        try {
            MapsInitializer.initialize(AddPropiedadActivity.this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }



    private void findViews() {
        size = findViewById(R.id.editTextAddSize);
        titulo = findViewById(R.id.editTextAddTitulo);
        precio = findViewById(R.id.editTextAddPrecio);
        descripcion = findViewById(R.id.editTextAddDesc);
        habitaciones = findViewById(R.id.editTextAddHabs);
        direccion = findViewById(R.id.editTextAddDireccion);
        categorias = findViewById(R.id.spinnerCategorias);
        mMapView = findViewById(R.id.mapView);
        scroll = findViewById(R.id.ScrollViewAdd);
        vistaProvincia = findViewById(R.id.textViewProvinciaList);
        vistaCiudad = findViewById(R.id.textViewCiudad);
        vistaRegion = findViewById(R.id.textViewRegion);
        ubicacion = findViewById(R.id.buttonSeleccionar);
        zipcode = findViewById(R.id.editTextAddZipCode);

    }

    public String getLocation(String direccion) throws IOException {
        String loc = Geocode.getLatLong(AddPropiedadActivity.this, direccion);
        return loc;
    }


    private void cargarSpinerCategorias() {

        CategoryService serviceCategoria = ServiceGenerator.createService(CategoryService.class);
        Call<ResponseContainer<Category>> callCategorias = serviceCategoria.getListCategory();
        callCategorias.enqueue(new Callback<ResponseContainer<Category>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Category>> call, Response<ResponseContainer<Category>> response) {
                if(response.isSuccessful()){
                    listaCategorias = response.body().getRows();
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(AddPropiedadActivity.this,android.R.layout.simple_spinner_dropdown_item,listaCategorias);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorias.setAdapter(adapter);
                    categorias.setSelection(listaCategorias.size()-1);


                }else{
                    Toast.makeText(AddPropiedadActivity.this, "Error al obtener categorias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Category>> call, Throwable t) {
                Toast.makeText(AddPropiedadActivity.this, "Error de red", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void addPropiedad(AddPropiedad propiedad) {
        String address = "Calle " + direccion.getText().toString() + ", " + zipcode.getText().toString() + " " + " " + vistaProvincia.getText().toString() + ", España";
        String loc = null;
        try {
            loc = getLocation(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Category category = (Category) categorias.getSelectedItem();

        propiedad = new AddPropiedad();

        propiedad.setTitle(titulo.getText().toString());
        propiedad.setDescription(descripcion.getText().toString());
        propiedad.setPrice(Integer.valueOf(precio.getText().toString()));
        propiedad.setRooms(Integer.valueOf(habitaciones.getText().toString()));
        propiedad.setCategoryId(category.getId());
        propiedad.setSize(Integer.valueOf(size.getText().toString()));
        propiedad.setAddress(direccion.getText().toString());
        propiedad.setZipcode(zipcode.getText().toString());
        propiedad.setCity(vistaCiudad.getText().toString());
        propiedad.setProvince(vistaProvincia.getText().toString());
        propiedad.setLoc(loc);

        PropiedadService propiedadService = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(AddPropiedadActivity.this), TipoAutenticacion.JWT);
        Call<AddPropiedad> callPropiedad = propiedadService.addProperty(propiedad);
        callPropiedad.enqueue(new Callback<AddPropiedad>() {
            @Override
            public void onResponse(Call<AddPropiedad> call, Response<AddPropiedad> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddPropiedadActivity.this, "Propiedad añadida", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(AddPropiedadActivity.this, "Error al crear propiedad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddPropiedad> call, Throwable t) {
                Toast.makeText(AddPropiedadActivity.this, "Error de red", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void onGeographySelected(Map<String, String> hm) {
        vistaRegion.setText(hm.get(GeographySpain.REGION));
        vistaProvincia.setText(hm.get(GeographySpain.PROVINCIA));
        vistaCiudad.setText(hm.get(GeographySpain.MUNICIPIO));
    }



}

