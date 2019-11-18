package com.example.bitacora.activities.Registro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bitacora.R;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.SQLFotos;
import com.example.bitacora.SQLAudios;
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
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CrearRegistroActivity extends AppCompatActivity {

    private  String textFile;
    String currentPhotoPath;
    private MediaRecorder mRecorder;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;


    private static final int REQUEST_READ_EXTERNAL_STORAGE = 392;
    private static final int REQUEST_CAMERA = 485;
    private static final int IMAGE_CAPTURE_REQUEST = 615;
    private static final int IMAGE_PICKER_REQUEST = 31;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    private List<String> imagesName;
    private List<String> recordingsName;
    //private ImageAddAdapter adapter;

    private int selectedID;
    private int tipo;

    private EditText tituloText;
    private EditText descripcionText;

    private Button crearButton;
    private Button cancelarButton;

    private ImageButton gravarAudio;
    private ImageButton pararGravacion;
    private ImageButton aniadirFoto;


    double latitude = 0; // latitude
    double longitude = 0; // longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_registro);
        Intent receivedIntent = getIntent();
        Bundle extras = receivedIntent.getExtras();
        selectedID = extras.getInt("id",-1);
        tipo = extras.getInt("tipo",-1);

        final SQLRegistro mSQLRegistro = new SQLRegistro(this);
        final SQLFotos mSQLFotos = new SQLFotos(this);
        final SQLAudios mSQLAudios = new SQLAudios(this);

        this.tituloText = (EditText) findViewById(R.id.editTextTituloRegistro);
        this.descripcionText = (EditText) findViewById(R.id.editTextDescripcionRegistro);

        this.crearButton = (Button) findViewById(R.id.buttonCrearRegistro);
        this.cancelarButton = (Button) findViewById(R.id.buttonCancelarRegistro);

        this.gravarAudio = (ImageButton) findViewById(R.id.buttonAñadirAudioCrearRegistro);
        this.pararGravacion = (ImageButton) findViewById(R.id.imageButtonPararGrabacion);
        this.aniadirFoto = (ImageButton) findViewById(R.id.buttonAñadirFotosCrearRegistro);

        pararGravacion.setEnabled(false);
        pararGravacion.setVisibility(View.GONE);

        imagesName = new ArrayList<String>();
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
        }
        else {
            updateTomarFoto();
        }

        gravarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermissions()) {
                    pararGravacion.setEnabled(true);
                    pararGravacion.setVisibility(View.VISIBLE);
                    gravarAudio.setEnabled(false);
                    gravarAudio.setVisibility(View.GONE);
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

                    mRecorder.setOutputFile(audio.getAbsolutePath());
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    mRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                    recordingsName.add(audio.getAbsolutePath());
                }
                else
                {
                    RequestPermissions();
                }
            }
        });

        pararGravacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararGravacion.setEnabled(false);
                pararGravacion.setVisibility(View.GONE);
                gravarAudio.setVisibility(View.VISIBLE);
                gravarAudio.setEnabled(true);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();

            }
        });

        crearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloText.getText().toString();
                textFile =  save();
                int insertData = (int) mSQLRegistro.addData(selectedID,titulo,textFile,tipo);
                for (int i = 0; i < imagesName.size(); i++) {
                    System.out.println(recordingsName.get(i));
                    int insertData2 = (int) mSQLAudios.addData(insertData,recordingsName.get(i));
                }
                for (int i = 0; i < imagesName.size(); i++) {
                    System.out.println(imagesName.get(i));
                    int insertData3 = (int) mSQLFotos.addData(insertData,imagesName.get(i));
                }
                // addToGallery();
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
                    File imgFile = new  File(currentPhotoPath);
                    if(imgFile.exists())            {

                    }
                }
                break;
            }

        }
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
    private File getAudioFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "Bitacora_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File image = File.createTempFile(pictureFile,  ".3gp", storageDir);

        return image;
    }
    private File getTextFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "Bitacora_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File image = File.createTempFile(pictureFile,  ".txt", storageDir);

        return image;
    }



    public String save() {
        String text = descripcionText.getText().toString();
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
                }
            }
        }
        return getFilesDir() + "/" + texto.getName();
    }

    public void load() {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream (new File(textFile));
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
