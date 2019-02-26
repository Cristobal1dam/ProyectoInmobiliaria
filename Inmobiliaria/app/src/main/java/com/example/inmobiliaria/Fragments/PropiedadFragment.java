package com.example.inmobiliaria.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliaria.Adapters.MyPropiedadRecyclerViewAdapter;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Generator.UtilUser;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.Services.PhotoService;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropiedadFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<PropiedadFoto> propiedadListFoto;
    private OnListFragmentInteractionListener mListener;
    private Context ctx;
    MyPropiedadRecyclerViewAdapter adapter;
   // private PropiedadViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PropiedadFragment() {
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
           // UtilToken.setToken(ctx,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjNzNkMDhmZDU0ZWE5MDAxNzExZmZkYyIsImlhdCI6MTU1MTE4MzI4N30.QahrrglNtqZ2Rcb5f1lrXjBchj_HKVjWCiqCDo6MVTg");
            PropiedadService service = ServiceGenerator.createService(PropiedadService.class);
            Call<ResponseContainer<PropiedadFoto>> call = service.getListPropiedades();

            call.enqueue(new Callback<ResponseContainer<PropiedadFoto>>() {

                @Override
                public void onResponse(Call<ResponseContainer<PropiedadFoto>> call, Response<ResponseContainer<PropiedadFoto>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                    } else {
                        List<Propiedad> propiedadList = new ArrayList<>();
                        propiedadListFoto = response.body().getRows();




                        adapter = new MyPropiedadRecyclerViewAdapter(
                                ctx,
                                propiedadListFoto,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<PropiedadFoto>> call, Throwable t) {
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
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PropiedadFoto item);
    }
/*
    private List<PropiedadFoto> getListaFotos (List<Propiedad> listaProp){
        PhotoService service = ServiceGenerator.createService(PhotoService.class);
        final List<PropiedadFoto> listaConFoto = new ArrayList<>();
        for(Propiedad propiedad : listaProp) {
            final PropiedadFoto propFoto = new PropiedadFoto();
            propFoto.setPropiedad(propiedad);
            Call<ResponseContainer<Photo>> call = service.getOnePhoto(propiedad.getId());
            call.enqueue(new Callback<ResponseContainer<Photo>>() {

                @Override
                public void onResponse(Call<ResponseContainer<Photo>> call, Response<ResponseContainer<Photo>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                    } else {
                        List<Photo> fotos = new ArrayList<>();
                        fotos =  response.body().getRows();

                        for (Photo foto: fotos){
                            List<String> fotosLink = new ArrayList<>();
                            fotosLink = propFoto.getPhotos();
                            fotosLink.add(foto.getImgurLink());
                            propFoto.setPhotos(fotosLink);
                        }

                        listaConFoto.add(propFoto);


                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Photo>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }


            });
        }
        return listaConFoto;
    }*/
}
