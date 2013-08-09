/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Ignacio
 */
public class Utiles {

    /**
     * Convierte una cadena en un objeto Integer.
     *
     * @param original String a convertir
     * @return Integer convertido
     */
    public static Integer convertStringToInt(String original) throws NumberFormatException{
        Integer conv = null;
        conv = Integer.parseInt(original);
        return conv;
    }

    public static Calendar convertIntegerToCalendarYear(Integer year){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year.intValue());
        
        return c;
    }

    public static String fechaConHora(Calendar fecha) {
        if(fecha == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm" );
        return format.format(fecha.getTime());
    }
}
