package com.dam.salesianostriana.proyecto.weatherdam.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Utils {
    /**
     * Almacenará las ciudades que se añadan como favorito.
     */
    public static List<String> listadoCiudadesFav= new ArrayList<String>();

    public static SharedPreferences sharedPreferences;

     /*
        Método que devuelve el bufferedreader asociado a una URL.
     */
    public static BufferedReader Url2BufferedReader(String url) throws IOException {

        //return new BufferedReader(new InputStreamReader((new URL(url)).openStream()));
        URL Url = new URL(url);
        InputStream is = Url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br;
    }

    /*
        Método que devuelve el contenido de una URL como una cadena de caracteres
     */

    public static String getStringContentFromUrl(String url) throws IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader br =  Url2BufferedReader(url);

        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();

        return sb.toString();

    }

    /**
     * Devuelve el día de la semana según la fecha que se le pase.
     * @param fecha
     * @return
     */
    public static String getDiaSemana(String fecha) {
        String valor_dia = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);

        if (diaSemana == 1) {
            valor_dia = "Domingo";
        } else if (diaSemana == 2) {
            valor_dia = "Lunes";
        } else if (diaSemana == 3) {
            valor_dia = "Martes";
        } else if (diaSemana == 4) {
            valor_dia = "Miercoles";
        } else if (diaSemana == 5) {
            valor_dia = "Jueves";
        } else if (diaSemana == 6) {
            valor_dia = "Viernes";
        } else if (diaSemana == 7) {
            valor_dia = "Sabado";
        }
        return valor_dia;
    }

    /**
     * Guarda el array de favoritos en el archivo de preferencias
     * @param mContext
     * @return
     */
    public static boolean guardarArray(Context mContext) {
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences = mContext.getSharedPreferences("mispreferencias", Context.MODE_APPEND);
        SharedPreferences.Editor editar = sharedPreferences.edit();
        editar.putInt("arrayCiudadesSize", listadoCiudadesFav.size());

        for(int i=0;i<listadoCiudadesFav.size();i++) {
            editar.remove("Posicion_" + i);
            editar.putString("Posicion_" + i, listadoCiudadesFav.get(i));
        }
        return editar.commit();
    }

    /**
     * Carga el array guardado en el archivo de preferencias.
     * @param mContext
     */
    public static void cargarArray(Context mContext){
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sharedPreferences = mContext.getSharedPreferences("mispreferencias", Context.MODE_APPEND);
        listadoCiudadesFav.clear();
        int size = sharedPreferences.getInt("arrayCiudadesSize", 0);
        for(int i=0;i<size;i++)
        {
            listadoCiudadesFav.add(sharedPreferences.getString("Posicion_" + i, null));
        }
    }



}
