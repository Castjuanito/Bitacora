package com.example.bitacora;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MisViajesActivity extends AppCompatActivity {

    SQLViaje mSQLViaje = new SQLViaje(this);;
    List<Viaje> rowItems;
    ListView mylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_viajes);

        mSQLViaje = new SQLViaje(this);
        rowItems = new ArrayList<Viaje>();

        //TODO pasar de la base de datos a arrayList de viaje
        rowItems = cargarViajes();
        mylistview = (ListView) findViewById(R.id.listViajes);
        ViajeAdapter adapter = new ViajeAdapter(this, rowItems);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int member_name = rowItems.get(i).getId();
                Intent editScreenIntent = new Intent(MisViajesActivity.this, DetalleViajeActivity.class);
                editScreenIntent.putExtra("id",member_name);
                startActivity(editScreenIntent);

            }
        });

    }




    private List<Viaje> cargarViajes() {

        Cursor data = mSQLViaje.getData();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            int id = Integer.parseInt(data.getString(0));
            Log.d("id", data.getString(0));
            Viaje mViaje = new Viaje(id,data.getString(1),data.getString(4));
            rowItems.add(mViaje);
        }
        return rowItems;
    }
}
