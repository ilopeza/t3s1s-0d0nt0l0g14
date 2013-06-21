/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Maxi
 */
public class FechaUtils {
    
    public static String fechaMaskFormat(Calendar fecha, String patron) {
        SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyy");
        return date_format.format(fecha.getTime());
    }
    
    public static String fechaConSeparador(Calendar fecha) {
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        return date_format.format(fecha.getTime());
    }
    
}
