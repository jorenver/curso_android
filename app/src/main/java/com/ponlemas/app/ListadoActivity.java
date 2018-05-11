package com.ponlemas.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ponlemas.app.dominio.Cliente;
import com.ponlemas.app.usuario.Usuario;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Vector;

public class ListadoActivity extends AppCompatActivity {
    private Spinner listado_spinner_ciudades;
    private ListView listado_clientes;
    private Vector<Cliente> vectorClientes;
    private Usuario usuario;


    public class miAdaptador extends ArrayAdapter{
        private Activity contexto;
        LayoutInflater mInflater;

        public miAdaptador(Activity context){
            super(context, R.layout.activity_listado, vectorClientes);
            this.contexto = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            //el tamano de la lista
            return vectorClientes.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            CheckBox listado_listview_checkbox;
            EditText listado_listview_editext;
            ImageButton listado_listView_boton;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.listado_listview, null);
            }
            listado_listview_checkbox =(CheckBox) convertView.findViewById(R.id.listado_listview_check);
            listado_listview_editext = (EditText) convertView.findViewById(R.id.listado_listview_texto);
            listado_listView_boton = (ImageButton) convertView.findViewById(R.id.listado_listview_eliminar);
            //llenar el objeto con los datos del arreglo
            Cliente objCliente = vectorClientes.elementAt(position);
            listado_listview_editext.setText(objCliente.getNombre());
            listado_listView_boton.setTag(position);
            listado_listView_boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vectorClientes.removeElementAt((int)view.getTag());
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        this.listado_spinner_ciudades = (Spinner) findViewById(R.id.listado_ciudades);
        this.listado_clientes = (ListView) findViewById(R.id.listado_productos);
        this.usuario = new Usuario();
        this.vectorClientes = usuario.getClientes();
        this.listado_clientes.setAdapter(new miAdaptador(ListadoActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listado_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.listado_menu_cargar){
            Toast.makeText(ListadoActivity.this,"Preciono el boton Cargar" ,Toast.LENGTH_SHORT).show();
            leerEnPreferencia();
        }else if(item.getItemId() == R.id.listado_menu_guardar){
            guardarEnPreferencia();
        }
        return true;
    }

    public void guardarEnPreferencia(){
        SharedPreferences miPreferencia= getSharedPreferences("prefsBroadnet", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = miPreferencia.edit();
        editor.putString("listado", usuario.getClientesJSON().toString());
        editor.commit();
    }

    public void leerEnPreferencia() {
        SharedPreferences miPreferencia;
        miPreferencia = getSharedPreferences("prefsBroadnet", Context.MODE_PRIVATE);

        JSONArray miArreglo = new JSONArray();
        try {
            miArreglo = new JSONArray(miPreferencia.getString("listado", ""));
            Toast.makeText(getBaseContext(), miArreglo.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
