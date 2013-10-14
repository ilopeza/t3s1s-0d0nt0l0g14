/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.profesor;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.MateriaException;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "PROFESOR")
public class Profesor extends Persona {
    
    @ManyToMany
    @JoinTable(name="profesorXmateria")
    private List<Materia> materia;

    
    public Profesor() {
        materia= new ArrayList<Materia>();
    }

    public Profesor(String nombre, String apellido) {
        super(nombre, apellido);
    }

    public boolean addMateria(Materia m) {
        if (m == null) {
            return false;
        }        
        return materia.add(m);
    }

    public boolean removeMateria(Materia m) {
        if (m == null) {
            return false;
        }
        return materia.remove(m);
    }

    public void removeAllMaterias() {
        if (materia == null) {
            return;
        }
        materia.clear();
    }
    public List<Materia> getMateria() {
        return materia;
    }

    public void setMateria(List<Materia> materia) {
        this.materia = materia;
    }

    @Override
    public void validar() throws GenericException {
        super.validar();
        if (materia == null) {
            throw new MateriaException("El nombre de la catedra no puede ser nulo o vacio.");
        }
        for (Materia m : materia) {
            m.validar();
        }
    }

}
