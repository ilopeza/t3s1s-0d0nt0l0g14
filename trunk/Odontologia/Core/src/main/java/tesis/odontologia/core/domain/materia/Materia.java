/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.materia;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.MateriaException;

/**
 *
 * @author alespe
 */
@Entity
public class Materia extends Generic {

    @Column(length = 150)
    @Size(min = 1, max = 150, message = "El nombre de la materia debe tener entre 1 y 150 caracteres")
    @NotNull(message = "El nombre de la materia no puede ser nulo.")
    private String nombre;
    
    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "materia_id")
    private List<Catedra> catedra;
    
    public Materia() {
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Materia(String nombre, List<Catedra> catedra) {
        this.nombre = nombre;
        this.catedra = catedra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
        public List<Catedra> getCatedra() {
        return catedra;
    }

    public void setCatedra(List<Catedra> catedra) {
        this.catedra = catedra;
    }
    

    @Override
    public void validar() throws GenericException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MateriaException("El nombre de la materia no puede ser nulo o vacio.");
        }
        if (catedra == null) {
            throw new MateriaException("El nombre de la catedra no puede ser nulo o vacio.");
        }
        
        for(Catedra c : catedra){
            c.validar();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final Materia other = (Materia) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
    
}
