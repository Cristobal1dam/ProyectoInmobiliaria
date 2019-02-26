package com.example.inmobiliaria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.inmobiliaria.Fragments.LoginFragment;
import com.example.inmobiliaria.Fragments.RegistroFragment;


public class SessionActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,RegistroFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new LoginFragment())
                .commit();
    }
    @Override
    public void navegarRegistro() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new RegistroFragment())
                .commit();

    }

    @Override
    public void navegarLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, new LoginFragment())
                .commit();
    }
}
