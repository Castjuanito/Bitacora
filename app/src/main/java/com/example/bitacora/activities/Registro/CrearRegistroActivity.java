package com.example.bitacora.activities.Registro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitacora.R;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.activities.Registro.location.GPSTracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearRegistroActivity extends AppCompatActivity {

    private static final String FILE_NAME = "example.txt";
    String currentPhotoPath;


    private static final int REQUEST_READ_EXTERNAL_STORAGE = 392;
    private static final int REQUEST_CAMERA = 485;
    private static final int IMAGE_CAPTURE_REQUEST = 615;
    private static final int IMAGE_PICKER_REQUEST = 31;

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

        SQLRegistro mSQLAlistamiento = new SQLRegistro(this);

        this.tituloText = (EditText) findViewById(R.id.editTextTituloRegistro);
        this.descripcionText = (EditText) findViewById(R.id.editTextDescripcionRegistro);

        this.crearButton = (Button) findViewById(R.id.buttonCrearRegistro);
        this.cancelarButton = (Button) findViewById(R.id.buttonCancelarRegistro);

        this.aniadirAudio = (Button) findViewById(R.id.buttonAñadirAudioCrearRegistro);
        this.aniadirFoto = (Button) findViewById(R.id.buttonAñadirFotosCrearRegistro);

        imagesName = new ArrayList<String>();
        images = new ArrayList<>();

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

        crearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloText.getText().toString();
                String file =  save();
                addToGallery();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CAPTURE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    File imgFile = new  File(currentPhotoPath);
                    if(imgFile.exists())            {

                    }
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
                sendTakePictureIntent();

            }
        });
    }

    private File getPictureFile() throws IOException {
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String pictureFile = "Bitacora_" + timeStamp;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
            return image;
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

    public String save() {
        String text = descripcionText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            descripcionText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getFilesDir() + "/" + FILE_NAME;
    }

    public void load() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            descripcionText.getText().clear();
            Toast.makeText(this, sb.toString(),
                    Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private void addToGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
    }


    private void sendTakePictureIntent() {

        Intent tomarFoto_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        tomarFoto_intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (tomarFoto_intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(tomarFoto_intent, IMAGE_CAPTURE_REQUEST);
            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.bitacora.fileprovider",
                        pictureFile);
                tomarFoto_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(tomarFoto_intent, IMAGE_CAPTURE_REQUEST);
            }
        }
    }



}
