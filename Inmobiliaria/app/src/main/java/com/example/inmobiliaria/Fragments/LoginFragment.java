package com.example.inmobiliaria.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Generator.UtilUser;
import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.Model.LoginResponse;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.Services.AuthService;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Button btnRegistro,btnLogin;
    EditText etEmail,etPassowrd;



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        findViews(view);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.navegarRegistro();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos(etEmail,etPassowrd)) {
                    peticionLogin(etEmail.getText().toString(), etPassowrd.getText().toString());
                }else
                Toast.makeText(getActivity(), "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void peticionLogin(String email, String password) {

        String credentials = Credentials.basic(email, password);
        AuthService service = ServiceGenerator.createService(AuthService.class);
        Call<LoginResponse> call = service.doLogin(credentials);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() != 201) {
                    // error
                    Log.e("RequestError", response.message());
                    Toast.makeText(getContext(), "Email o contraseña incorrecto", Toast.LENGTH_SHORT).show();

                } else {

                    UtilToken.setToken(getActivity(), response.body().getToken());
                    UtilUser.setUserInfo(getActivity(), response.body().getUser());

                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void findViews(View view) {
        btnRegistro = view.findViewById(R.id.buttonRegistro);
        btnLogin = view.findViewById(R.id.buttonLogin);
        etEmail = view.findViewById(R.id.editTextEmail);
        etPassowrd = view.findViewById(R.id.editTextPassword);

    }

    private Boolean validarCampos(EditText email, EditText pass){
      return (!(email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()));

    }

    public interface OnFragmentInteractionListener {
        void navegarRegistro();

    }


}
