/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.alumno;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "ALUMNO")
public class Alumno extends Persona {

    public Alumno() {
    }

    public Alumno(String nombre, String apellido) {
        super(nombre, apellido);
    }

    @Override
    public void validar() throws GenericException {
        super.validar();
    }

}
