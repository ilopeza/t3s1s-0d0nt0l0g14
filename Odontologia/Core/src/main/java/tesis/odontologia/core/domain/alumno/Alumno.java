/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.alumno;

import tesis.odontologia.core.domain.paciente.*;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import tesis.odontologia.core.domain.Persona;

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

    
    
}
