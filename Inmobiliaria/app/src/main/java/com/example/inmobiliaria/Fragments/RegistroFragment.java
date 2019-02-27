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
import com.example.inmobiliaria.Generator.UtilUser;
import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.Model.LoginResponse;
import com.example.inmobiliaria.Model.UserDto;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.Services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {

    private RegistroFragment.OnFragmentInteractionListener mListener;
    Button btnNavegarLogin, btnRegistrar;
    EditText etEmail, etPassword, etPasswordRep, etNombre;

    public RegistroFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegistroFragment.OnFragmentInteractionListener) {
            mListener = (RegistroFragment.OnFragmentInteractionListener) context;
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
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        findViews(view);

        btnNavegarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.navegarLogin();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCampos(etNombre,etEmail,etPassword,etPasswordRep)) {
                    if (validarRepetirPass(etPassword, etPasswordRep)) {

                        doRegistro(etEmail.getText().toString(),
                                etPassword.getText().toString(),
                                etNombre.getText().toString());
                    } else
                        Toast.makeText(getActivity(), "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();


            }
        });

        return view;
    }

    private void findViews(View view) {
        btnNavegarLogin = view.findViewById(R.id.buttonNavegarLogin);
        btnRegistrar = view.findViewById(R.id.buttonDoRegistro);
        etEmail = view.findViewById(R.id.editTextEmailRegistro);
        etNombre = view.findViewById(R.id.editTextNombreRegistro);
        etPassword = view.findViewById(R.id.editTextPassRegistro);
        etPasswordRep = view.findViewById(R.id.editTextPassRepeat);
    }

    private void doRegistro(String email, String password, String nombre){
        AuthService service = ServiceGenerator.createService(AuthService.class);
        UserDto user = new UserDto(email, password, nombre);
        Call<LoginResponse> callRegister = service.doRegister(user);

        callRegister.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("Uploaded", "Éxito");
                    Log.d("Uploaded", response.body().toString());
                    UtilUser.setUserInfo(getActivity(), response.body().getUser());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    Log.e("Upload error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Upload error", t.getMessage());
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void navegarLogin();

    }
    private Boolean validarCampos(EditText nombre,EditText email, EditText pass, EditText passRepeat){
        return (!(email.getText().toString().isEmpty() ||
                pass.getText().toString().isEmpty() ||
                passRepeat.getText().toString().isEmpty() ||
                nombre.getText().toString().isEmpty()));

    }

    private Boolean validarRepetirPass(EditText pass, EditText passRepeat){
        return pass.getText().toString().equals(passRepeat.getText().toString());
    }

}
