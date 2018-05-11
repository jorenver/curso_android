package com.ponlemas.app.acceso_datos.dbhandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String BD_NOMBRE ="DB_BROADNET";
    public static final int BD_VERSION = 1;
    public static final String BD_TABLA = "Cliente";

    public DbHelper(Context contexto){
        super(contexto, BD_NOMBRE, null, BD_VERSION);
    }

    // se crea la base de datos en el telefono
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+BD_TABLA+"(" +
                "id integer primary key, " +
                "nombre text, " +
                "telefono text, " +
                "correo text)");
    }

    //actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+BD_TABLA);
        sqLiteDatabase.execSQL("create table "+BD_TABLA+"(" +
                "id integer primary key, " +
                "nombre text, " +
                "telefono text, " +
                "correo text)");
    }

}
