/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.profesor;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.MateriaException;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "PROFESOR")
public class Profesor extends Persona {

    @ManyToMany
    @JoinTable(name = "profesorXmateria",
            joinColumns = {
        @JoinColumn(name = "Persona_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "materia_id")})
    private List<Materia> listaMaterias;
    
    @Enumerated(EnumType.STRING)
    private EstadoProfesor estado = EstadoProfesor.ACTIVO;

    public Profesor() {
        listaMaterias = new ArrayList<Materia>();
    }

    public Profesor(String nombre, String apellido) {
        super(nombre, apellido);
    }

    public boolean addMateria(Materia m) {
        if (m == null) {
            return false;
        }
        return listaMaterias.add(m);
    }

    public boolean removeMateria(Materia m) {
        if (m == null) {
            return false;
        }
        return listaMaterias.remove(m);
    }

    public void removeAllMaterias() {
        if (listaMaterias == null) {
            return;
        }
        listaMaterias.clear();
    }

    public List<Materia> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<Materia> materias) {
        this.listaMaterias = materias;
    }
    
    public EstadoProfesor getEstado() {
        return estado;
    }

    public void setEstado(EstadoProfesor estado) {
        this.estado = estado;
    }

    @Override
    public void validar() throws GenericException {
        super.validar();
        if (listaMaterias == null) {
            throw new MateriaException("El nombre de la catedra no puede ser nulo o vacio.");
        }
        for (Materia m : listaMaterias) {
            m.validar();
        }
    }

    public enum EstadoProfesor {

        ACTIVO {
            @Override
            public String toString() {
                return "Activo";
            }
        },
        DADO_DE_BAJA {
            @Override
            public String toString() {
                return "Dado de baja";
            }
        };
    }
}
