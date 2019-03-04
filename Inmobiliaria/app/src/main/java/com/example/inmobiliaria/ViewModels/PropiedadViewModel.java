package com.example.inmobiliaria.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.inmobiliaria.Model.Propiedad;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PropiedadViewModel extends ViewModel {

    private final MutableLiveData<String> idPropiedad = new MutableLiveData<>();

    private final MutableLiveData<List<Propiedad>> listaPropiedades = new MutableLiveData<>();

    private MutableLiveData<LatLng> posicionPropiedad = new MutableLiveData<>();

    private Boolean showStar = false;

    private MutableLiveData<Boolean> irMapa = new MutableLiveData<>();



    public void setIrMapa(Boolean ir){ irMapa.setValue(ir); }

    public Boolean getIrMapa() {return irMapa.getValue();}

    public void setposicionPropiedad(LatLng posicion){ posicionPropiedad.setValue(posicion); }

    public LatLng getposicionPropiedad() {return posicionPropiedad.getValue();}

    public void setShowStar(Boolean show){ showStar = show; }

    public Boolean getShowStar() {return showStar;}

    public void selectIdProyec(String id) {
        idPropiedad.setValue(id);
    }


    public void selectPropiedadList(List<Propiedad> propiedades) {
        listaPropiedades.setValue(propiedades);
    }

    public void addFavoritos(String id){

    }

    public MutableLiveData<String> getSelectedIdProyec() {
        return idPropiedad;
    }



    public MutableLiveData<List<Propiedad>> getAll() { return listaPropiedades; }




}