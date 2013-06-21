/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.usuario;

import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.RolException;

/**
 *
 * @author alespe
 */
public class Rol extends Generic{
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
