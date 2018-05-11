package com.ponlemas.app.acceso_datos.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ponlemas.app.dominio.Cliente;

import java.util.ArrayList;

public class ClienteContract {
    SQLiteDatabase db;
    Context context;

    public static final String NOMBRE_TABLA="Cliente";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_EMAIL = "email";

    public ClienteContract(Context context){
        this.context= context;
    }

    public long insert(Cliente cliente){
        ContentValues miContent = new ContentValues();
        miContent.put(CAMPO_ID,cliente.getId());
        miContent.put(CAMPO_NOMBRE,cliente.getNombre());
        miContent.put(CAMPO_TELEFONO,cliente.getTelefono());
        miContent.put(CAMPO_EMAIL,cliente.getEmail());
        DbHelper dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        long salida = -1;
        salida = db.insert(NOMBRE_TABLA, null, miContent);
        db.close();
        return  salida;
    }

    public int update(Cliente cliente){
        ContentValues miContent = new ContentValues();
        miContent.put(CAMPO_NOMBRE,cliente.getNombre());
        miContent.put(CAMPO_TELEFONO,cliente.getTelefono());
        miContent.put(CAMPO_EMAIL,cliente.getEmail());
        String where= CAMPO_ID+"= ?";
        String[] where_arguments = new String[]{String.valueOf(cliente.getId())};
        DbHelper dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        int salida = -1;
        salida = db.update(NOMBRE_TABLA, miContent, where, where_arguments);
        db.close();
        return  salida;
    }

    public int delete(int id){
        String where= CAMPO_ID+"= ?";
        String[] where_arguments = new String[]{String.valueOf(id)};
        DbHelper dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        int salida = -1;
        //db.beginTransaction();
        salida = db.delete(NOMBRE_TABLA, where, where_arguments);
        //db.setTransactionSuccessful(); //commit
        //db.endTransaction(); // si no encuentro el commit se hace rollback
        db.close();
        return  salida;
    }

    public Cliente getClienteById(int id){
        String[] columnas = {CAMPO_ID, CAMPO_NOMBRE, CAMPO_TELEFONO, CAMPO_EMAIL};
        String where= CAMPO_ID+"= ?";
        String[] where_arguments = new String[]{String.valueOf(id)};
        DbHelper dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        Cursor miCursor;
        miCursor = db.query(NOMBRE_TABLA, columnas, where, where_arguments, null, null, null);
        Cliente cliente = new Cliente();
        for(miCursor.moveToFirst(); miCursor.isAfterLast(); miCursor.moveToNext()){
            cliente.setId(miCursor.getInt(miCursor.getColumnIndex(CAMPO_ID)));
            cliente.setNombre(miCursor.getString(miCursor.getColumnIndex(CAMPO_NOMBRE)));
            cliente.setEmail(miCursor.getString(miCursor.getColumnIndex(CAMPO_EMAIL)));
            cliente.setTelefono(miCursor.getString(miCursor.getColumnIndex(CAMPO_TELEFONO)));
        }
        db.close();
        return cliente;
    }

    public ArrayList<Cliente> getClientes(int id){
        String[] columnas = {CAMPO_ID, CAMPO_NOMBRE, CAMPO_TELEFONO, CAMPO_EMAIL};
        DbHelper dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        Cursor miCursor;
        miCursor = db.query(NOMBRE_TABLA, columnas, null, null, null, null, null);
        ArrayList<Cliente> array_clientes = new ArrayList<Cliente>();
        for(miCursor.moveToFirst(); miCursor.isAfterLast(); miCursor.moveToNext()){
            Cliente cliente = new Cliente();
            cliente.setId(miCursor.getInt(miCursor.getColumnIndex(CAMPO_ID)));
            cliente.setNombre(miCursor.getString(miCursor.getColumnIndex(CAMPO_NOMBRE)));
            cliente.setEmail(miCursor.getString(miCursor.getColumnIndex(CAMPO_EMAIL)));
            cliente.setTelefono(miCursor.getString(miCursor.getColumnIndex(CAMPO_TELEFONO)));
            array_clientes.add(cliente);
        }
        db.close();
        return array_clientes;
    }
}
