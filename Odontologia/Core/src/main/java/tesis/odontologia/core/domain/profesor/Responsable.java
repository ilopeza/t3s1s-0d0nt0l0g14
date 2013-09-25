/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.profesor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "RESPONSABLE")
public class Responsable extends Persona {

    public Responsable() {
    }

    public Responsable(String nombre, String apellido) {
        super(nombre, apellido);
    }

    @Override
    public void validar() throws GenericException {
        super.validar();
    }

}
