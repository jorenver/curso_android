package com.ponlemas.app.usuario;

import com.ponlemas.app.dominio.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class Usuario {
    private long id;
    private String nombre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Vector<Cliente> getClientes(){
        Vector<Cliente> dat = new Vector<Cliente>();
        Cliente objCLiente;
        for(int i=0; i<6; i++){
            objCLiente = new Cliente();
            objCLiente.setId(i+1);
            objCLiente.setNombre("Nombre "+String.valueOf(i+1));
            objCLiente.setNombre("Correo "+String.valueOf(i+1));
            objCLiente.setNombre("Telefono "+String.valueOf(i+1));
            dat.add(objCLiente);
        }
        return dat;
    }

    public JSONArray getClientesJSON(){
        JSONArray array = new JSONArray();
        Vector<Cliente> listaClientes;
        JSONObject jsonCliente;
        listaClientes = getClientes();
        for(int i=0; i<listaClientes.size(); i++){
            jsonCliente = listaClientes.elementAt(i).toJSON();
            array.put(jsonCliente);
        }
        return array;
    }
}

