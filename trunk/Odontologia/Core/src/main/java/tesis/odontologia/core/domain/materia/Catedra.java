/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.materia;

import javax.persistence.Entity;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.exception.CatedraException;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author alespe
 */
@Entity
public class Catedra extends Generic {

    private String nombre;

    public Catedra() {
    }

    public Catedra(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void validar() throws GenericException {
        if (nombre == null || nombre.isEmpty()) {
            throw new CatedraException("El nombre de la cátedra no puede ser nulo o vacio.");
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Catedra other = (Catedra) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
