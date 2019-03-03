package com.example.inmobiliaria.Services;

import com.example.inmobiliaria.Model.AddPropiedad;
import com.example.inmobiliaria.Model.FavResponse;
import com.example.inmobiliaria.Model.Propiedad;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.PropiedadId;
import com.example.inmobiliaria.Model.PropiedadIdFoto;
import com.example.inmobiliaria.Model.ResponseContainer;
import com.example.inmobiliaria.Model.ResponseContainerNoList;
import com.example.inmobiliaria.Model.ResponsePropiedad;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PropiedadService {

    @GET("/Properties")
    Call<ResponseContainer<PropiedadFoto>> getListPropiedades();

    @GET("/Properties/auth")
    Call<ResponseContainer<PropiedadFoto>> getListPropiedadesLog();

    @GET("properties/mine")
    Call<ResponseContainer<PropiedadFoto>> getListMisPropiedades();

    @GET("properties/fav")
    Call<ResponseContainer<PropiedadFoto>> getListPropiedadesFavoritas();

    @GET("/Properties/{id}")
    Call<ResponsePropiedad> getPropiedad(@Path("id") String id);

    @GET("/Properties")
    Call<ResponseContainer<PropiedadFoto>> getPropiedadesCercanas(@Query("near") String loc);

    @POST("/properties/fav/{id}")
    Call<FavResponse> addFav(@Path("id") String id);

    @POST("/properties")
    Call<AddPropiedad> addProperty(@Body AddPropiedad propiedad);

    @DELETE("/properties/fav/{id}")
    Call<FavResponse> deleteFav(@Path("id") String id);

}
