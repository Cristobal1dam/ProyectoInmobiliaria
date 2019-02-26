package com.example.inmobiliaria.Services;

import com.example.inmobiliaria.Model.LoginResponse;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PhotoService {

    @POST("/photos")
    Call<ResponseContainer<Photo>> getOnePhoto(@Query("propertyId") String propiedadId);
}
