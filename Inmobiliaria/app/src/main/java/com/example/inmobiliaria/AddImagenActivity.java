package com.example.inmobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.Generator.ServiceGenerator;
import com.example.inmobiliaria.Generator.TipoAutenticacion;
import com.example.inmobiliaria.Generator.UtilToken;
import com.example.inmobiliaria.Model.Photo;
import com.example.inmobiliaria.Services.PhotoService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImagenActivity extends AppCompatActivity {
    Uri uriSelected;
    ImageView preVis;
    Button subir, seleccionar;
    String idProp;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imagen);
        findViews();
        Bundle extras = getIntent().getExtras();
        idProp = extras.getString("id");

        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirFoto();

            }
        });
    }

    private void findViews() {
        preVis = findViewById(R.id.imageViewPreVi);
        subir = findViewById(R.id.buttonAddImg);
        seleccionar = findViewById(R.id.buttonSelecImg);
    }

    public void subirFoto() {
        if (uriSelected != null) {

            Toast.makeText(this, "Añadiendo foto...", Toast.LENGTH_LONG).show();

            PhotoService service = ServiceGenerator.createService(PhotoService.class, UtilToken.getToken(AddImagenActivity.this), TipoAutenticacion.JWT);
            // ctx=getView().getContext();

            try {
                InputStream inputStream = AddImagenActivity.this.getContentResolver().openInputStream(uriSelected);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                int cantBytes;
                byte[] buffer = new byte[1024 * 4];

                while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                    baos.write(buffer, 0, cantBytes);
                }


                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(AddImagenActivity.this.getContentResolver().getType(uriSelected)), baos.toByteArray());


                MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "photo", requestFile);


                RequestBody propertyId = RequestBody.create(MultipartBody.FORM, idProp);


                Call<Photo> callRegister = service.addPhoto(body, propertyId);

                callRegister.enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddImagenActivity.this, "Foto añadida", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(AddImagenActivity.this, PropiedadDetalleActivity.class);
                            i.putExtra("id", idProp);
                            AddImagenActivity.this.startActivity(i);
                            finish();

                        } else {
                            Log.e("Upload error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        Log.e("Upload error", t.getMessage());
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, "Debes seleccionar una imagen", Toast.LENGTH_SHORT).show();


    }

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {

                if (resultData != null) {
                    uri = resultData.getData();
                    Log.i("Filechooser URI", "Uri: " + uri.toString());
                    //showImage(uri);
                    Glide
                            .with(this)
                            .load(uri)
                            .into(preVis);
                    uriSelected = uri;


                }
            }
        }
    }
}
