package com.ponlemas.app.acceso_datos.ficheros;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InternosHandler {
    public static final String TAG = "InternosHandler";
    public static final String FICHERO = "broadnet.txt";
    private Context context;

    public InternosHandler(Context context){
        this.context = context;
    }

    public void guardarTexto(String texto){
        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(context.openFileOutput(FICHERO, Context.MODE_PRIVATE));
            osw.write(texto);
            osw.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public String leerTexto(String texto){
        String salida = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.context.openFileInput(FICHERO)));
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
