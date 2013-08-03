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


    
    
    
    
}
