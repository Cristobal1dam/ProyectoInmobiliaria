package com.example.inmobiliaria.Services;

import com.example.inmobiliaria.Model.Category;
import com.example.inmobiliaria.Model.PropiedadFoto;
import com.example.inmobiliaria.Model.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("/Categories")
    Call<ResponseContainer<Category>> getListCategory();
}
