package com.ponlemas.app.acceso_datos.xmlhandler;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

import com.ponlemas.app.dominio.Noticia;

import org.xml.sax.Attributes;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

public class XmlHandler {
    public static final String TAG = XmlHandler.class.getName();
    public static final String ARCHIVO = "broadnet.xml";
    private Context context;
    private Noticia noticia;

    public XmlHandler(Context context){
        this.context = context;
    }

    public void escribirXML(String texto){
        XmlSerializer serializer = Xml.newSerializer();
        OutputStreamWriter osw;
        try {
            osw = new OutputStreamWriter(context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE));
            serializer.setOutput(osw);
            serializer.startTag("", "datos"); //abrir etiqueta

            serializer.startTag("", "cliente");
            serializer.text("Nombre del cliente 1");
            serializer.endTag("", "cliente");

            serializer.startTag("", "cliente");
            serializer.text("Nombre del cliente 2");
            serializer.endTag("", "cliente");

            serializer.endTag("", "datos"); // cerrar etiqueta
            serializer.endDocument();
            osw.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public String leerXML(String texto){
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

    public ArrayList<Noticia> parseXMLSAX(String ruta){
        URL url = null;
        final ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        try {
            url = new URL(ruta);
            RootElement root = new RootElement("rss");
            Element channel = root.getChild("chanel");
            Element item = root.getChild("item");
            item.setStartElementListener(new StartElementListener() {
                @Override
                public void start(Attributes attributes) {
                    noticia = new Noticia();
                }
            });
            item.setEndElementListener(new EndElementListener() {
                @Override
                public void end() {
                    noticias.add(noticia);
                }
            });
            item.getChild("title").setEndTextElementListener(new EndTextElementListener() {
                @Override
                public void end(String s) {
                    noticia.setTitulo(s);
                }
            });
            item.getChild("title").setEndTextElementListener(new EndTextElementListener() {
                @Override
                public void end(String s) {
                    noticia.setTitulo(s);
                }
            });
            item.getChild("descripcion").setEndTextElementListener(new EndTextElementListener() {
                @Override
                public void end(String s) {
                    noticia.setDescripcion(s);
                }
            });
            item.getChild("link").setEndTextElementListener(new EndTextElementListener() {
                @Override
                public void end(String s) {
                    noticia.setLink(s);
                }
            });
            Xml.parse(url.openConnection().getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
        return noticias;
    }
}
