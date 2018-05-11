package com.ponlemas.app.acceso_datos.ficheros;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ExternosHandler {
    public static final String TAG = "ExternosHandler";
    public static final String FICHERO = "broadnet.txt";
    private Context context;

    public ExternosHandler(Context context){
        this.context = context;
    }

    public void guardarTexto(String texto){
        File ruta = Environment.getExternalStorageDirectory();
        File f = new File(ruta.getAbsolutePath(), FICHERO);
        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write(texto);
            osw.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public String leerTexto(String texto){
        File ruta = Environment.getExternalStorageDirectory();
        File f = new File(ruta.getAbsolutePath(), FICHERO);
        String salida = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            salida = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        return salida;
    }
}
