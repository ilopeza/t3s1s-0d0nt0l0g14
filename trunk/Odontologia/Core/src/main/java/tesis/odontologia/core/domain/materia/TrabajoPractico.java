/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.materia;

import javax.persistence.Column;
import javax.persistence.Entity;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.TrabajoPracticoException;

/**
 *
 * @author alespe
 */
@Entity
public class TrabajoPractico extends Generic {
    
    @Column(length = 150, nullable = false)
    private String nombre;
    
    @Column(length = 300)
    private String descripcion;

    public TrabajoPractico() {
    }

    public TrabajoPractico(String nombre) {
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
        if(nombre == null || nombre.isEmpty()){
            throw new TrabajoPracticoException("El nombre del trabajo práctico no puede estar vacio o nulo");
        }
    }
    
    
    
}
