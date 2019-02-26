package com.example.inmobiliaria.Services;



import com.example.inmobiliaria.Model.LoginResponse;
import com.example.inmobiliaria.Model.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);

    @POST("/users")
    Call<LoginResponse> doRegister(@Body UserDto user);


}
