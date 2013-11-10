/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.validacion;

/**
 *
 * @author Mau
 */
public class Validacion {

    public Validacion() {
    }

    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return false;
        } catch (NumberFormatException nfe) {
            return true;
        }
    }

    public boolean nullEmpty(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarNumero(String cadenaNumerica) {
        boolean var;
        var = cadenaNumerica.matches("\\d+");
        return var;
    }

    public boolean validarTexto(String cadenaCaracteres) {
        boolean var;
        var = cadenaCaracteres.matches("([a-z A-Z ñáéíóú]{2,50})");
        return var;
    }
    
    public boolean validarMail(String cadenaCaracteres) {
        boolean var;
        var = cadenaCaracteres.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return var;
    }
    
}
