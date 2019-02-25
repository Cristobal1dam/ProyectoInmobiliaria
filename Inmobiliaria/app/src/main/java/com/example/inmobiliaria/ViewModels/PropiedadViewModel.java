package com.example.inmobiliaria.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.inmobiliaria.Model.Propiedad;

import java.util.List;

public class PropiedadViewModel extends ViewModel {

    private final MutableLiveData<String> idPropiedad = new MutableLiveData<>();

    private final MutableLiveData<List<Propiedad>> listaPropiedades = new MutableLiveData<>();

    public void selectIdProyec(String id) {
        idPropiedad.setValue(id);
    }


    public void selectPropiedadList(List<Propiedad> propiedades) {
        listaPropiedades.setValue(propiedades);
    }

    public MutableLiveData<String> getSelectedIdProyec() {
        return idPropiedad;
    }



    public MutableLiveData<List<Propiedad>> getAll() { return listaPropiedades; }




}