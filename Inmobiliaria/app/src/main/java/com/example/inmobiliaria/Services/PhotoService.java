package com.example.inmobiliaria.Services;

import com.example.inmobiliaria.Model.LoginResponse;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Model.ResponseContainer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PhotoService {

    @POST("/photos")
    Call<ResponseContainer<Photo>> getOnePhoto(@Query("propertyId") String propiedadId);

    @Multipart
    @POST("/photos")
    Call<Photo> addPhoto(@Part MultipartBody.Part photo,
                                 @Part("propertyId") RequestBody propertyId);
}
