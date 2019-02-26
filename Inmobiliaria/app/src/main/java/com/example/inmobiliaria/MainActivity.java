package com.example.inmobiliaria;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inmobiliaria.Adapters.MyPropiedadRecyclerViewAdapter;
import com.example.inmobiliaria.Fragments.MisPropiedadesFragment;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PropiedadFragment.OnListFragmentInteractionListener, PropiedadFavoritasFragment.OnListFragmentInteractionListener, MisPropiedadesFragment.OnListFragmentInteractionListener{

    private Fragment f;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment f = null;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_propiedad:
                    f = new PropiedadFragment();
                    break;
                case R.id.navigation_perfil:
                    f = null;
                    break;
                case R.id.navigation_map:
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




      /*  private void listarMisPropiedades() {

        PropiedadService service = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(MainActivity.this), TipoAutenticacion.JWT);
        Call<ResponseContainer<Propiedad>> call = service.getListMisPropiedades();

        call.enqueue(new Callback<ResponseContainer<Propiedad>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Propiedad>> call, Response<ResponseContainer<Propiedad>> response) {
                if (response.code() != 200) {
                    Toast.makeText(MainActivity.this, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    PropiedadViewModel mViewModel = ViewModelProviders.of((FragmentActivity) MainActivity.this).get(PropiedadViewModel.class);
                    mViewModel.selectPropiedadList(response.body().getRows());
                }
            }

                @Override
                public void onFailure (Call < ResponseContainer < Propiedad >> call, Throwable t){
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }

        });
    }*/

  /*  private void listarFavoritos() {

        PropiedadService service = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(MainActivity.this), TipoAutenticacion.JWT);
        Call<ResponseContainer<Propiedad>> call = service.getListPropiedadesFavoritas();

        call.enqueue(new Callback<ResponseContainer<Propiedad>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Propiedad>> call, Response<ResponseContainer<Propiedad>> response) {
                if (response.code() != 200) {
                    Toast.makeText(MainActivity.this, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {
                    PropiedadViewModel mViewModel = ViewModelProviders.of((FragmentActivity) MainActivity.this).get(PropiedadViewModel.class);
                    mViewModel.selectPropiedadList(response.body().getRows());
                }
            }

            @Override
            public void onFailure (Call < ResponseContainer < Propiedad >> call, Throwable t){
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }

        });
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilToken.setToken(MainActivity.this,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjNzNkMDhmZDU0ZWE5MDAxNzExZmZkYyIsImlhdCI6MTU1MTE4MzI4N30.QahrrglNtqZ2Rcb5f1lrXjBchj_HKVjWCiqCDo6MVTg");
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new PropiedadFragment())
                .commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    @Override
    public void onListFragmentInteraction(PropiedadFoto item) {

    }
}
