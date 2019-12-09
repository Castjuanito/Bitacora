package com.example.bitacora.activities.Registro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.bitacora.R;
import com.example.bitacora.SQLAudios;
import com.example.bitacora.SQLFotos;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.activities.Alistamiento.AlistamientoActivity;
import com.example.bitacora.activities.Viaje.DetalleViajeActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CrearRegistroActivity extends AppCompatActivity {

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private static final String LOG_TAG = "AudioRecording";
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 392;
    private static final int REQUEST_CAMERA = 485;
    private static final int IMAGE_CAPTURE_REQUEST = 615;
    private static final int IMAGE_PICKER_REQUEST = 31;
    private static String mFileName = null;
    String currentPhotoPath;
    double latitude = 0; // latitude
    double longitude = 0; // longitude
    private String textFile;
    private MediaRecorder mRecorder;
    //private ImageAddAdapter adapter;
    private List<String> imagesName;
    private List<String> recordingsName;
    private int selectedID;
    private int tipo;
    private EditText tituloText;
    private EditText descripcionText;
    private Button crearButton;
    private Button cancelarButton;
    private ImageButton grabarAudio;
    private ImageButton pararGrabacion;
    private ImageButton aniadirFoto;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_registro);
        Intent receivedIntent = getIntent();
        Bundle extras = receivedIntent.getExtras();
        selectedID = extras != null ? extras.getInt("viajeId", -1) : 0;
        tipo = extras.getInt("tipo", -1);

        final SQLRegistro mSQLRegistro = new SQLRegistro(this);
        final SQLFotos mSQLFotos = new SQLFotos(this);
        final SQLAudios mSQLAudios = new SQLAudios(this);

        this.tituloText = findViewById(R.id.editTextTituloRegistro);
        this.descripcionText = findViewById(R.id.editTextDescripcionRegistro);

        this.crearButton = findViewById(R.id.buttonCrearRegistro);
        this.cancelarButton = findViewById(R.id.buttonCancelarRegistro);

        this.grabarAudio = findViewById(R.id.buttonAñadirAudioCrearRegistro);
        this.pararGrabacion = findViewById(R.id.imageButtonPararGrabacion);
        this.aniadirFoto = findViewById(R.id.buttonAñadirFotosCrearRegistro);

        pararGrabacion.setEnabled(false);
        pararGrabacion.setVisibility(View.GONE);

        imagesName = new ArrayList<>();
        recordingsName = new ArrayList<>();

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
        } else {
            actualizarBotonTomarFoto();
        }

        grabarAudio.setOnClickListener(v -> {
            if (CheckPermissions()) {
                pararGrabacion.setEnabled(true);
                pararGrabacion.setVisibility(View.VISIBLE);
                grabarAudio.setEnabled(false);
                grabarAudio.setVisibility(View.GONE);
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                File audio = null;
                try {
                    audio = getAudioFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mRecorder.setOutputFile(Objects.requireNonNull(audio).getAbsolutePath());
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Preparación fallida");
                }
                mRecorder.start();
                Toast.makeText(getApplicationContext(), "Empezando grabación !", Toast.LENGTH_LONG).show();
                recordingsName.add(audio.getAbsolutePath());
            } else {
                RequestPermissions();
            }
        });

        pararGrabacion.setOnClickListener(v -> {
            pararGrabacion.setEnabled(false);
            pararGrabacion.setVisibility(View.GONE);
            grabarAudio.setVisibility(View.VISIBLE);
            grabarAudio.setEnabled(true);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(getApplicationContext(), "Parando grabación !", Toast.LENGTH_LONG).show();
        });

        crearButton.setOnClickListener(v -> {
            String titulo = tituloText.getText().toString();
            textFile = save();

            if (titulo != null && !titulo.equalsIgnoreCase("") && textFile != null && !textFile.equalsIgnoreCase("") && !textFile.equalsIgnoreCase("Error")) {
                int insertData = mSQLRegistro.addData(selectedID, titulo, textFile, tipo);
                recordingsName.forEach(name -> {
                    System.out.println(name);
                    mSQLAudios.addData(insertData, name);
                });

                imagesName.forEach(name -> {
                    System.out.println(name);
                    mSQLFotos.addData(insertData, name);
                });

                Toast.makeText(getApplicationContext(), "Se ha creado de manera exitosa el registro !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Por favor ingrese todos los datos !", Toast.LENGTH_LONG).show();
            }

            Intent editScreenIntent = new Intent(CrearRegistroActivity.this, DetalleViajeActivity.class);
            editScreenIntent.putExtra("id", selectedID);
            startActivity(editScreenIntent);
        });

        cancelarButton.setOnClickListener(v -> {
            tituloText.setText("");
            descripcionText.setText("");
            deleteCurrentFiles();
            super.onBackPressed();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
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
                }
                break;
            }
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(CrearRegistroActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CAPTURE_REQUEST: {
                if (resultCode == RESULT_OK) {
                    File imgFile = new File(currentPhotoPath);
                    if (imgFile.exists()) {

                    }
                }
                break;
            }

        }
    }


    private void actualizarBotonTomarFoto() {
        aniadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTakePictureIntent();
            }
        });
    }

    private String getFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return "Bitacora_" + timeStamp;
    }

    private File getPictureFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(getFileName(), ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File getAudioFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File audio = File.createTempFile(getFileName(), ".3gp", storageDir);
        return audio;
    }

    private File getTextFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File text = File.createTempFile(getFileName(), ".txt", storageDir);
        return text;
    }


    public String save() {
        String text = descripcionText.getText().toString();
        // Don't repeat this please
        if (text.equalsIgnoreCase("")) {
            return "Error";
        }
        FileOutputStream fos = null;
        File texto = null;
        try {
            texto = getTextFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            fos = openFileOutput(texto.getName(), MODE_PRIVATE);
            fos.write(text.getBytes());
            descripcionText.getText().clear();
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
                    //No haga esto en un trabajo real, por favor
                    return "Error";
                }
            }
            return getFilesDir() + "/" + texto.getName();
        }


    }

    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteCurrentFiles() {
        imagesName.forEach(fileName -> deleteDatabase(fileName));
        recordingsName.forEach(fileName -> deleteDatabase(fileName));
        imagesName = new ArrayList<>();
        recordingsName = new ArrayList<>();
    }

    public void load() {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(new File(textFile));
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

    private void sendTakePictureIntent() {
        Intent tomarFoto_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        tomarFoto_intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (tomarFoto_intent.resolveActivity(getPackageManager()) != null) {
            //  startActivityForResult(tomarFoto_intent, IMAGE_CAPTURE_REQUEST);
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
                imagesName.add(pictureFile.getAbsolutePath());
            }
        }
    }

}
