package com.example.inmobiliaria.Fragments;


import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.inmobiliaria.Adapters.MyPropiedadRecyclerViewAdapter;
import com.example.inmobiliaria.AddImagenActivity;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.ServiceGeneratorNear;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.Model.ResponseNear;
import com.example.inmobiliaria.PropiedadDetalleActivity;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.Services.PropiedadService;
import com.example.inmobiliaria.ViewModels.PropiedadViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class MapFragment extends Fragment {


    private MapView mMapView;
    private GoogleMap googleMap;
    // private Location mLastLocation;
    private LatLng miPosicion, posicionMarker;
    private FusedLocationProviderClient fusedLocationClient;
    private List<ResponseNear> propiedadListFoto;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        final PropiedadViewModel propiedadViewModel  = ViewModelProviders.of(getActivity())
                .get(PropiedadViewModel.class);


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                //mMapView.getMapAsync(this);

                if(!propiedadViewModel.getIrMapa()) {
                    // For showing a move to my location button
                    if (checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }

                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {

                                    if (location != null) {
                                        miPosicion = new LatLng(location.getLatitude(), location.getLongitude());
                                        googleMap.addMarker(new MarkerOptions().position(miPosicion).title("Tu posición"));
                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(miPosicion).zoom(15).build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                        PropiedadService service = ServiceGeneratorNear.createService(PropiedadService.class);
                                        addMarksNear(miPosicion);
                                    }
                                }
                            });

                }else {
                    googleMap.addMarker(new MarkerOptions().position(propiedadViewModel.getposicionPropiedad()).title("Posición propiedad"));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(propiedadViewModel.getposicionPropiedad()).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    propiedadViewModel.setIrMapa(false);
                }

            }


        });


        return view;
    }



    public void addMarksNear(LatLng posicion) {

        PropiedadService service = ServiceGeneratorNear.createService(PropiedadService.class);
        String posicionActual = Double.toString(posicion.longitude) + "," + Double.toString(posicion.latitude);
        Call<ResponseContainer<ResponseNear>> call = service.getPropiedadesCercanas(posicionActual);

        call.enqueue(new Callback<ResponseContainer<ResponseNear>>() {

            @Override
            public void onResponse(Call<ResponseContainer<ResponseNear>> call, Response<ResponseContainer<ResponseNear>> response) {


                if(response.isSuccessful()) {
                    propiedadListFoto = response.body().getRows();

                    for (ResponseNear propiedad : propiedadListFoto) {

                        String[] latlong =  propiedad.getLoc().split(",");
                        double latitude = Double.parseDouble(latlong[0].trim());
                        double longitude = Double.parseDouble(latlong[1].trim());
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude,longitude))
                                .title(propiedad.getTitle())
                                .snippet(propiedad.getDescription()));
                        //.setTag(propiedad.getId());
                    }
                } else {
                    Toast.makeText(getActivity(), "Algo fue mal", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseContainer<ResponseNear>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }


        });
    }


    /*@Override
    public boolean onMarkerClick(Marker marker) {

        Intent i = new Intent(getActivity(), PropiedadDetalleActivity.class);
        i.putExtra("id", marker.getTag().toString());
        getActivity().startActivity(i);
        return false;
    }*/
}
