package com.ponlemas.app.dominio;

import org.json.JSONException;
import org.json.JSONObject;

public class Cliente {
    private long id;
    private String nombre;
    private String email;
    private String telefono;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("id", this.id);
            obj.put("nombre", this.nombre);
            obj.put("email", this.email);
            obj.put("telefono", this.telefono);
        }catch ( JSONException e){
            e.printStackTrace();
        }
        return  obj;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
