package com.example.bitacora;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLAlistamiento extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "alistamiento_table";
    private static final String COL1 = "id";//0
    private static final String COL2 = "idViaje";//1
    private static final String COL3 = "contenido";//2
    private static final String COL4 = "estado";//3

    public SQLAlistamiento(Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " INTEGER," +
                COL3 + " TEXT," +
                COL4 + " INTEGER," +
                "FOREIGN KEY(idViaje) REFERENCES viaje_table(id))";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(sqLiteDatabase);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int addData(int idViaje, String contenido, int estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, idViaje);
        contentValues.put(COL3, contenido);
        contentValues.put(COL4, estado);


        Log.d("Alistamiento", "addData: Adding " + idViaje + " " + contenido + " " + estado + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return -1;
        } else {
            return (int) result;
        }
    }


    public void update(int newValue, int id, int oldValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newValue + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldValue + "'";
        db.execSQL(query);
        Log.d("Alistamiento", "update: Adding " + id + TABLE_NAME);

    }

    public Cursor getDataIdItem(int selectedID, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + selectedID + "'" + " AND " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
