package com.example.bitacora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class CrearViajeActivity extends AppCompatActivity {

    private static final String KEY_STATE = "state";
    private static final String KEY_CITIES = "cities";


    SQLViaje mSQLViaje;
    Spinner stateSpinner;
    Spinner citiesSpinner;
    CalendarView calendario;
    private ProgressDialog pDialog;
    Button crear;
    EditText materia;
    EditText profesor;
    Button cancelar;
    int year,month,dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_viaje);
        mSQLViaje = new SQLViaje(this);
        stateSpinner = findViewById(R.id.spinnerDep);
        citiesSpinner = findViewById(R.id.spinnerMun);
        calendario = (CalendarView)findViewById(R.id.calendarView2);
        materia = (EditText)findViewById(R.id.editTextMateria);
        profesor = (EditText)findViewById(R.id.editTextProfeor);
        crear = (Button)findViewById(R.id.buttonCrearViaje);
        cancelar = (Button)findViewById(R.id.buttonCancelar);

        JSONArray jsonArray=loadDepartamentos("Departamentos.json");
        loadStateCityDetails(jsonArray);


        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year1, int month1,
                                            int dayOfMonth1) {
                year=year1;
                month=month1;
                dayOfMonth=dayOfMonth1;

            }
        });

        //Display state and city name when submit button is pressed
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Departamento state = (Departamento) stateSpinner.getSelectedItem();
                String city = citiesSpinner.getSelectedItem().toString();
                String profe = profesor.getText().toString();
                String mat = materia.getText().toString();
                String fechaSalida = dayOfMonth +" / " + (month+1) + " / " + year;
                Toast.makeText(getApplicationContext(), "Selected State: " + state.getDepartamentoName()
                        + " City: " + city
                        +" Date is : " + dayOfMonth +" / " + (month+1) + " / " + year
                        +" teacher : " + profe +" materia: " + mat, Toast.LENGTH_SHORT).show();

                if (profe.length() != 0 && mat.length() != 0) {
                    int insertData = (int) mSQLViaje.addData(fechaSalida,  mat,  profe,  city+"-"+state.getDepartamentoName(),"-1",  "-1");


                    Intent editScreenIntent = new Intent(CrearViajeActivity.this, DetalleViajeActivity.class);
                    editScreenIntent.putExtra("id",insertData);
                    startActivity(editScreenIntent);

                } else {
                    Toast.makeText(getApplicationContext(),"Complete los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearViajeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private JSONArray loadDepartamentos(String file  ) {
        JSONArray jsonArray=null;
        try {


            InputStream is = getResources().getAssets().open(file);

            int size = is.available();

            byte[] data = new byte[size];

            is.read(data);

            is.close();

            String json = new String(data, "UTF-8");
            Log.d(KEY_STATE, json);
            jsonArray=new JSONArray(json);

        }catch (IOException e){

            e.printStackTrace();

        }catch (JSONException je){

            je.printStackTrace();

        }

        return jsonArray;

    }


    private void loadStateCityDetails(JSONArray jsonArray) {
        final List<Departamento> statesList = new ArrayList<>();
        final List<String> departamentos = new ArrayList<>();
                        try {
                            //Parse the JSON response array by iterating over it
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject response = jsonArray.getJSONObject(i);

                                String state = response.getString("departamento");
                                JSONArray cities = response.getJSONArray("ciudades");
                                Log.d(KEY_STATE,state );
                                List<String> citiesList = new ArrayList<>();
                                for (int j = 0; j < cities.length(); j++) {
                                    citiesList.add(cities.getString(j));
                                    Log.d(KEY_CITIES,cities.getString(j) );
                                }
                                statesList.add(new Departamento(state, citiesList));
                                departamentos.add(state);
                            }
                            final DepartamentoAdapter stateAdapter = new DepartamentoAdapter(CrearViajeActivity.this,
                                    R.layout.departamento_list, R.id.spinnerDep, statesList);
                            stateSpinner.setAdapter(stateAdapter);

                            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    //Populate City list to the second spinner when
                                    // a state is chosen from the first spinner
                                    Departamento cityDetails = stateAdapter.getItem(position);
                                    List<String> cityList = cityDetails.getMunicipios();
                                    ArrayAdapter citiesAdapter = new ArrayAdapter<>(CrearViajeActivity.this,
                                            R.layout.municipio_list, R.id.municipioSpinnerText, cityList);
                                    citiesSpinner.setAdapter(citiesAdapter);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


    }
}
