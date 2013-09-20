/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.RolException;

/**
 *
 * @author alespe
 */
@Entity
public class Rol extends Generic{
    
    public static String ALUMNO = "Alumno";
    public static String PACIENTE = "Paciente";
    public static String PROFESOR = "Profesor";
    public static String RESPONSABLE = "Responsable";
    public static String ADMINACADEMICO = "AdminAcademico";
        
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El rol debe tener entre 1 y 50 caracteres.")
    @NotNull(message = "El nombre del rol no puede ser nulo.")
    private String nombre;

    public Rol() {
    }

    public Rol(String nombre) {
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
        if(nombre ==null || nombre.isEmpty()){
            throw new RolException("El nombre del rol no puede ser nulo o vacio");
        }
    }

    public boolean is(String nombreRol) {
        return this.equals(new Rol(nombreRol));
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final Rol other = (Rol) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

}
