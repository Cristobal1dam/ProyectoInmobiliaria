package com.example.inmobiliaria.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.inmobiliaria.Model.Propiedad;

import java.util.List;

public class PropiedadViewModel extends ViewModel {

    private final MutableLiveData<String> idPropiedad = new MutableLiveData<>();

    private final MutableLiveData<List<Propiedad>> listaPropiedades = new MutableLiveData<>();

    private Boolean showStar = false;

    private Boolean irMisPropiedades = false;

    public void setirMisPropiedades(Boolean show){ irMisPropiedades = show; }

    public Boolean getirMisPropiedades() {return irMisPropiedades;}

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