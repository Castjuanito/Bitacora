package com.example.bitacora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLRegistro extends SQLiteOpenHelper {

    private static final String TAG = "SQLRegistro";

    private static final String TABLE_NAME = "registro_table";
    private static final String COL1 = "id";
    private static final String COL2 = "idViaje";
    private static final String COL3 = "titulo";
    private static final String COL4 = "descripcion";
    private static final String COL5 = "tipo";

//FOREIGN KEY(trackartist) REFERENCES artist(artistid)

    public SQLRegistro(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " INTEGER," +
                COL3 + " TEXT," +
                COL4 + " TEXT," +
                COL5 + " INTEGER," +
                "FOREIGN KEY(idViaje) REFERENCES viaje_table(id))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int addData(int idViaje, String titulo, String descripcion, int tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, idViaje);
        contentValues.put(COL3, titulo);
        contentValues.put(COL4, descripcion);
        contentValues.put(COL5, tipo);


        Log.d(TAG, "addData: Adding " + idViaje + " " + titulo + " " + descripcion + " " + tipo + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return -1;
        } else {
            return (int) result;
        }
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataByViajeAndTipo(int idViaje, int tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + idViaje + "' AND " + COL5 + " = '" + tipo + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     *
     * @param id
     * @return
     */
    public Cursor getItemByID(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemByIDPadre(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


/*
    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     *
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     *
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    */

}
