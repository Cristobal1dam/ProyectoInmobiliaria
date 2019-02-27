package com.example.inmobiliaria;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.inmobiliaria.Adapters.MyPropiedadRecyclerViewAdapter;
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

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PropiedadFragment.OnListFragmentInteractionListener, PropiedadFavoritasFragment.OnListFragmentInteractionListener, MisPropiedadesFragment.OnListFragmentInteractionListener{

    private Fragment f;
    private FloatingActionButton fab;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment f = null;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_propiedad:
                    if(!(UtilToken.getToken(MainActivity.this) == null))
                        getSupportActionBar().show();

                    f = new PropiedadFragment();
                    break;
                case R.id.navigation_perfil:
                    getSupportActionBar().hide();

                    f = new PerfilFragment();
                    break;
                case R.id.navigation_map:
                    getSupportActionBar().hide();

                    f = null;
                    break;


            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor, f)
                    .commit();
            return true;
        }


    };

    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment f = null;

        switch (item.getItemId()) {
            case R.id.action_misFavoritos:
                f = new PropiedadFavoritasFragment();
                break;
            case R.id.action_misPropiedades:
                f = new MisPropiedadesFragment();
                break;


        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, f)
                .commit();
        return true;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new PropiedadFragment())
                .commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        final Menu menu = navigation.getMenu();
        ocultarOpcionesNavigationNoLog(menu);
        if(UtilToken.getToken(MainActivity.this) == null)
            getSupportActionBar().hide();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



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


    public void ocultarOpcionesNavigationNoLog(Menu menu){
        if(UtilToken.getToken(MainActivity.this) == null) {
            MenuItem item = menu.findItem(R.id.navigation_perfil);
            item.setVisible(false);
        }
    }



    @Override
    public void onListFragmentInteraction(PropiedadFoto item) {

    }
}
