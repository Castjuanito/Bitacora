package com.example.bitacora.activities.Alistamiento;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitacora.R;
import com.example.bitacora.SQLAlistamiento;
import com.example.bitacora.activities.Viaje.adapters.AlistamientoItemAdapter;
import com.example.bitacora.models.AlistamientoItem;

import java.util.ArrayList;
import java.util.List;

public class AlistamientoActivity extends AppCompatActivity {

    SQLAlistamiento mSQLAlistamiento = new SQLAlistamiento(this);
    List<AlistamientoItem> rowItems;
    ListView mylistview;
    CheckBox checkBox;
    EditText nuevoItem;
    ImageButton añadir;
    Context con;
    AlistamientoItemAdapter adapter;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alistamiento);

        mSQLAlistamiento = new SQLAlistamiento(this);
        con = this;
        rowItems = new ArrayList<AlistamientoItem>();
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        rowItems = cargarItems();
        nuevoItem = findViewById(R.id.editTextItem);
        añadir = findViewById(R.id.imageButtonAñadirItem);
        mylistview = findViewById(R.id.AlistListView);
        adapter = new AlistamientoItemAdapter(this, rowItems);
        mylistview.setAdapter(adapter);

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"------------------algo",Toast.LENGTH_SHORT).show();
                checkBox = findViewById(i);

                //TODO actualizar en las bases de datos
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    mSQLAlistamiento.update(0, rowItems.get(i).getId(), 1);
                    rowItems.get(i).setEstado(false);
                } else {
                    checkBox.setChecked(true);
                    mSQLAlistamiento.update(1, rowItems.get(i).getId(), 0);
                    rowItems.get(i).setEstado(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nuevoItem.getText().toString().length() > 0) {
                    int id = mSQLAlistamiento.addData(selectedID, nuevoItem.getText().toString(), 0);
                    Cursor data = mSQLAlistamiento.getDataIdItem(selectedID, id);
                    while (data.moveToNext()) {
                        int id1 = Integer.parseInt(data.getString(0));
                        Log.d("id", data.getString(0));
                        int estado = Integer.parseInt(data.getString(3));
                        boolean est = (estado != 0);
                        AlistamientoItem mAlistamientoItem = new AlistamientoItem(id1, data.getString(2), est);
                        rowItems.add(mAlistamientoItem);

                    }
                    adapter.notifyDataSetChanged();


                }
            }
        });

    }


    private List<AlistamientoItem> cargarItems() {
        Cursor data = mSQLAlistamiento.getDataId(selectedID);
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            int id = Integer.parseInt(data.getString(0));
            Log.d("id", data.getString(0));
            int estado = Integer.parseInt(data.getString(3));
            boolean est = (estado != 0);
            AlistamientoItem mAlistamientoItem = new AlistamientoItem(id, data.getString(2), est);
            rowItems.add(mAlistamientoItem);
        }
        return rowItems;
    }
}
