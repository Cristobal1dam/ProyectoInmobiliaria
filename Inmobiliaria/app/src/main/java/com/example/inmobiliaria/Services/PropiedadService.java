package com.example.inmobiliaria.Services;

import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PropiedadService {

    @GET("/Properties")
    Call<ResponseContainer<Propiedad>> getListPropiedades();

    @GET("properties/mine")
    Call<ResponseContainer<Propiedad>> getListMisPropiedades();

    @GET("properties/fav")
    Call<ResponseContainer<Propiedad>> getListPropiedadesFavoritas();

}
