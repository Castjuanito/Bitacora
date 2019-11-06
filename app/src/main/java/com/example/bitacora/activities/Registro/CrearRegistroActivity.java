package com.example.bitacora.activities.Registro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitacora.R;
import com.example.bitacora.activities.Registro.location.GPSTracker;

import java.util.List;

public class CrearRegistroActivity extends AppCompatActivity {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 392;
    private static final int REQUEST_LOCATION = 758;
    private static final int REQUEST_CAMERA = 485;
    private static final int IMAGE_CAPTURE_REQUEST = 615;
    private static final int IMAGE_PICKER_REQUEST = 31;
    private static final int PLACE_PICKER_REQUEST = 997;

    private List<String> imagesName;
    private List<Bitmap> images;
    //private ImageAddAdapter adapter;

    private EditText tituloText;
    private EditText descripcionText;

    private Button crearButton;
    private Button cancelarButton;

    private Button aniadirAudio;
    private Button aniadirFoto;

    double latitude = 0; // latitude
    double longitude = 0; // longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_registro);

        this.tituloText = (EditText) findViewById(R.id.editTextTituloRegistro);
        this.descripcionText = (EditText) findViewById(R.id.editTextDescripcionRegistro);

        this.crearButton = (Button) findViewById(R.id.buttonCrearRegistro);
        this.cancelarButton = (Button) findViewById(R.id.buttonCancelarRegistro);

        this.aniadirAudio = (Button) findViewById(R.id.buttonAñadirAudioCrearRegistro);
        this.aniadirFoto = (Button) findViewById(R.id.buttonAñadirFotosCrearRegistro);


        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                    "Para seleccionar una imagen",
                    REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            updateCargarImagen();
        }*/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(this, Manifest.permission.CAMERA,
                    "Para tomar una foto", REQUEST_CAMERA);
        }
        else {
            updateTomarFoto();
        }



    }

    private void requestPermission(Activity context, String permission, String explanation,
                                   int requestId) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, explanation, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestId);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            /*case REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateCargarImagen();
                }
                break;
            }*/
            case REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateTomarFoto();
                }
                break;
            }

        }
    }

    private void updateCargarImagen() {
        aniadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cargarImagen_intent = new Intent(Intent.ACTION_PICK);
                cargarImagen_intent.setType("image/*");
                startActivityForResult(cargarImagen_intent, IMAGE_PICKER_REQUEST);
            }
        });
    }

    private void updateTomarFoto() {
        aniadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomarFoto_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (tomarFoto_intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(tomarFoto_intent, IMAGE_CAPTURE_REQUEST);
                }
            }
        });
    }


    private void updateGetLocation() {
        GPSTracker tracker = new GPSTracker(this);
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            this.latitude = tracker.getLatitude();
            this.longitude = tracker.getLongitude();
        }
    }


}
