package com.example.bitacora.util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.bitacora.SQLAudios;
import com.example.bitacora.SQLFotos;
import com.example.bitacora.SQLRegistro;
import com.example.bitacora.models.Registro;

import java.util.ArrayList;
import java.util.List;

public class SQLRegistroToRegistroParser {

    public static List<Registro> toRegistros(int idViaje, int tipo, Context context) {
        SQLRegistro mSQLRegistro1 = new SQLRegistro(context);
        Cursor registros1 = mSQLRegistro1.getData();
        List<Registro> registrosList1 = new ArrayList<>();
        SQLRegistro mSQLRegistro = new SQLRegistro(context);
        Cursor registros = mSQLRegistro.getDataByViajeAndTipo(idViaje,tipo);
        List<Registro> registrosList = new ArrayList<>();

        while (registros.moveToNext()) {
            int id = Integer.parseInt(registros.getString(0));
            Log.d("------id", registros.getString(0));
            //registrosList.add(toRegistro(registros, context));
        }

        return registrosList;
    }

    public static Registro toRegistro(Cursor data, Context context) {
        return new Registro(
                Integer.parseInt(data.getString(0)),
                data.getString(2),
                getImagenesList(Integer.parseInt(data.getString(0)), context),
                data.getString(3),
                getAudiosList(Integer.parseInt(data.getString(0)), context)
        );
    }

    private static List<String> getImagenesList(int registroId, Context context) {
        SQLFotos mSQLFotos = new SQLFotos(context);
        List<String> imageList = new ArrayList<>();
        Cursor data = mSQLFotos.getDataidRegistro(registroId);
        while (data.moveToNext()) {
            imageList.add(data.getString(2));
        }
        return imageList;
    }

    private static List<String> getAudiosList(int registroId, Context context) {
        SQLAudios mSQLAudios = new SQLAudios(context);
        List<String> audioList = new ArrayList<>();
        Cursor data = mSQLAudios.getDataidRegistro(registroId);
        while (data.moveToNext()) {
            audioList.add(data.getString(2));
        }
        return audioList;
    }
}
