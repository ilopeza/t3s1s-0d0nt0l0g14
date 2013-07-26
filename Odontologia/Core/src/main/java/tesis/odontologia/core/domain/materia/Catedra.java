/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.materia;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
//    
//    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
//    @JoinColumn(name = "materia_id")
//    private List<TrabajoPractico> trabajoPractico;

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
}
