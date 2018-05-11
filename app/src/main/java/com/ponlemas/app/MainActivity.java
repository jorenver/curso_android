package com.ponlemas.app;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ponlemas.app.acceso_datos.dbhandler.ClienteContract;
import com.ponlemas.app.acceso_datos.xmlhandler.XmlHandler;
import com.ponlemas.app.dominio.Cliente;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener, MainActivityDialog.DialogListener{
    private RadioButton main_radio1, main_radio2, main_radio3, main_radio4;
    private Button main_boton_mostrar, main_boton_abrir;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_radio1 = (RadioButton) findViewById(R.id.main_opcion1);
        main_radio2 = (RadioButton) findViewById(R.id.main_opcion2);
        main_radio3 = (RadioButton) findViewById(R.id.main_opcion3);
        main_radio4 = (RadioButton) findViewById(R.id.main_opcion4);
        main_boton_mostrar = (Button) findViewById(R.id.main_boton_mostrar);
        main_boton_abrir = (Button) findViewById(R.id.main_boton_abrir);

        main_boton_mostrar.setOnClickListener(this);
        main_boton_abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir nueva actividad
                Intent intent = new Intent(MainActivity.this, ListadoActivity.class);
                startActivity(intent);
            }
        });

        ClienteContract clienteContract = new ClienteContract(this);
        Cliente obj_cliente = new Cliente();
        obj_cliente.setId(1);
        obj_cliente.setNombre("Nombre 1");
        obj_cliente.setTelefono("Telefono 1");
        obj_cliente.setEmail("Email");
        clienteContract.insert(obj_cliente);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);
        XMLTaskParser xmlTaskParser = new XMLTaskParser();
        xmlTaskParser.execute("https://www.eluniverso.com/rss/politica.xml");
    }

    @Override
    public void onClick(View view) {
        if(main_radio1.isChecked()){
            Toast.makeText(getBaseContext(), "Hola mundo", Toast.LENGTH_SHORT).show();
        }else if(main_radio2.isChecked()){
            FragmentManager fragmentManager = getSupportFragmentManager();
            MainActivityDialog dialog = new MainActivityDialog();
            dialog.setMensaje("Hola mundo");
            dialog.show(fragmentManager, "Mi Dialogo");
        }else if(main_radio3.isChecked()){
            Snackbar.make(view, "Hola Mundo",Snackbar.LENGTH_SHORT ).show();
        }else if(main_radio4.isChecked()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(android.R.drawable.ic_media_play);
            mBuilder.setContentTitle("Mi notificacion");
            mBuilder.setContentText("El contenido de mi notificación");
            mBuilder.setStyle(new NotificationCompat.BigTextStyle());
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            // mostrar notificacion
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(1,mBuilder.build());
        }else{

        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        Toast.makeText(MainActivity.this, "Preciono Aceptar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        Toast.makeText(getBaseContext(), "Preciono Cancelar", Toast.LENGTH_SHORT).show();
    }

    class XMLTaskParser extends AsyncTask<String, Void, ArrayList> {

        @Override
        protected ArrayList doInBackground(String... strings) {
            XmlHandler xmlHandler = new XmlHandler(MainActivity.this);
            return xmlHandler.parseXMLSAX(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Descargando...");
            progressDialog.setTitle("Archivo RSS");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this,"SE ENCONTRARON : "+String.valueOf(arrayList.size())+" NOTICIAS" , Toast.LENGTH_SHORT).show();
        }
    }
}
