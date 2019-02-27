package com.example.inmobiliaria.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliaria.Adapters.MyPropiedadIdRecyclerViewAdapter;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.PropiedadId;
import com.example.inmobiliaria.Model.PropiedadIdFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.Services.PhotoService;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisPropiedadesFragment extends Fragment {

    private int mColumnCount = 1;
    private List<PropiedadIdFoto> propiedadListFoto;
    private PropiedadFragment.OnListFragmentInteractionListener mListener;
    private Context ctx;
    MyPropiedadIdRecyclerViewAdapter adapter;
    //private PropiedadViewModel mViewModel;


    public MisPropiedadesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_propiedad_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            ctx = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(ctx, mColumnCount));
            }


            propiedadListFoto = new ArrayList<>();
          //  UtilToken.setToken(ctx,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjNzNkMDhmZDU0ZWE5MDAxNzExZmZkYyIsImlhdCI6MTU1MTExMTA4OH0.xGJdOvVfT-1587vCTwPZaobIs-qjNHoXZfdf7QrzMI0");
            PropiedadService service = ServiceGenerator.createService(PropiedadService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
            Call<ResponseContainer<PropiedadIdFoto>> call = service.getListMisPropiedades();

            call.enqueue(new Callback<ResponseContainer<PropiedadIdFoto>>() {

                @Override
                public void onResponse(Call<ResponseContainer<PropiedadIdFoto>> call, Response<ResponseContainer<PropiedadIdFoto>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                    } else {

                        propiedadListFoto = response.body().getRows();




                        adapter = new MyPropiedadIdRecyclerViewAdapter(
                                ctx,
                                propiedadListFoto,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<PropiedadIdFoto>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }


            });

            // lanzarViewModel(ctx);

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PropiedadFragment.OnListFragmentInteractionListener) {
            mListener = (PropiedadFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
   /* private void lanzarViewModel(Context ctx) {
        PropiedadViewModel propiedadViewModel = ViewModelProviders.of((FragmentActivity) ctx)
                .get(PropiedadViewModel.class);
        propiedadViewModel.getAll().observe(getActivity(), new Observer<List<Propiedad>>() {
            @Override
            public void onChanged(@Nullable List<Propiedad> propiedades) {
                adapter.setNuevasPropiedades(propiedades);
            }
        });
    }*/


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PropiedadFoto item);
    }


}
