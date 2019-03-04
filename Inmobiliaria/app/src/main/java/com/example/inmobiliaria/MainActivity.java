package com.example.inmobiliaria;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.inmobiliaria.Adapters.MyPropiedadRecyclerViewAdapter;
import com.example.inmobiliaria.Fragments.MapFragment;
import com.example.inmobiliaria.Fragments.MisPropiedadesFragment;
import com.example.inmobiliaria.Fragments.PerfilFragment;
import com.example.inmobiliaria.Fragments.PropiedadFavoritasFragment;
import com.example.inmobiliaria.Fragments.PropiedadFragment;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Generator.UtilUser;
import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;
import com.google.android.gms.maps.model.LatLng;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class MainActivity extends AppCompatActivity implements PropiedadFragment.OnListFragmentInteractionListener, PropiedadFavoritasFragment.OnListFragmentInteractionListener, MisPropiedadesFragment.OnListFragmentInteractionListener{

    private Fragment f;
    private FloatingActionButton fab;
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;
    private PropiedadViewModel propiedadViewModel;


    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final String[] COARSE_PERMS={
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment f = null;


        @SuppressLint("RestrictedApi")
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_propiedad:
                    if(!(UtilToken.getToken(MainActivity.this) == null)) {
                        getSupportActionBar().show();
                        fab.setVisibility(View.VISIBLE);
                    }
                    propiedadViewModel.setShowStar(true);

                    f = new PropiedadFragment();
                    break;
                case R.id.navigation_perfil:
                    getSupportActionBar().hide();
                    fab.setVisibility(View.GONE);
                    f = new PerfilFragment();
                    break;
                case R.id.navigation_map:
                        getSupportActionBar().hide();
                        fab.setVisibility(View.GONE);
                        f = new MapFragment();
                    break;


            }
            if(f != null) {
                cargarFragmento(f);
            }
                return true;

        }


    };

    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment f = null;


        switch (item.getItemId()) {
            case R.id.action_misFavoritos:
                propiedadViewModel.setShowStar(false);
                f = new PropiedadFavoritasFragment();
                break;
            case R.id.action_misPropiedades:
                propiedadViewModel.setShowStar(false);
                f = new MisPropiedadesFragment();
                break;


        }
        cargarFragmento(f);
        return true;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onStart() {
        super.onStart();


        propiedadViewModel.setShowStar(true);

        Bundle extras = getIntent().getExtras();
       if(extras != null){
           propiedadViewModel.setIrMapa(true);
           String[] latlong =  extras.getString("loc").split(",");
           double latitude = Double.parseDouble(latlong[0].trim());
           double longitude = Double.parseDouble(latlong[1].trim());
           propiedadViewModel.setposicionPropiedad(new LatLng(latitude,longitude));
           cargarFragmento(new MapFragment());
       } else {
           propiedadViewModel.setIrMapa(false);
            cargarFragmento(new PropiedadFragment());
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        final Menu menu = navigation.getMenu();
        ocultarOpcionesNavigationNoLog(menu);
        if(UtilToken.getToken(MainActivity.this) == null) {
            getSupportActionBar().hide();
            fab.setVisibility(View.GONE);
        }

    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(!canAccessLocationCoarse() && !canAccessLocationCoarse()){
            requestPermissions(LOCATION_PERMS,LOCATION_REQUEST);
        }


        propiedadViewModel = ViewModelProviders.of(MainActivity.this)
                .get(PropiedadViewModel.class);



        BottomNavigationView navigation = findViewById(R.id.navigation);

        fab = findViewById(R.id.fabMain);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, AddPropiedadActivity.class));

            }
        });



    }
    public void onBackPressed() {

        if (!(UtilToken.getToken(MainActivity.this) == null)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title);


            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    UtilUser.clearSharedPreferences(MainActivity.this);
                    UtilToken.setToken(MainActivity.this, null);
                    startActivity(new Intent(MainActivity.this, SessionActivity.class));
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();

        }else

        startActivity(new Intent(MainActivity.this, SessionActivity.class));

    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


                if (!canAccessLocationCoarse() && !canAccessLocationFine()) {
                    Toast.makeText(this, "Esta aplicación necesita permisos de localización", Toast.LENGTH_SHORT).show();
                   finish();
                }




    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canAccessLocationFine() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canAccessLocationCoarse() {
        return(hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }




    public void ocultarOpcionesNavigationNoLog(Menu menu){
        if(UtilToken.getToken(MainActivity.this) == null) {
            MenuItem item = menu.findItem(R.id.navigation_perfil);
            item.setVisible(false);
        }
    }

    public void cargarFragmento(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, fragment)
                .commit();
    }



    @Override
    public void onListFragmentInteraction(PropiedadFoto item) {

    }
}
