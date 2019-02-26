package com.example.inmobiliaria.Services;

import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.PropiedadId;
import com.example.inmobiliaria.Model.PropiedadIdFoto;
import com.example.inmobiliaria.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PropiedadService {

    @GET("/Properties")
    Call<ResponseContainer<PropiedadFoto>> getListPropiedades();

    @GET("properties/mine")
    Call<ResponseContainer<PropiedadIdFoto>> getListMisPropiedades();

    @GET("properties/fav")
    Call<ResponseContainer<PropiedadIdFoto>> getListPropiedadesFavoritas();

}
