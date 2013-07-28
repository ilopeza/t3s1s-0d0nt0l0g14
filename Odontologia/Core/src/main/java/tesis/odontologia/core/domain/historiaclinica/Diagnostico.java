/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.exception.DiagnosticoticoException;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author alespe
 */
@Entity
public class Diagnostico extends Generic{
    
    //@ManyToOne
    //@JoinColumn(name = "trabajopractico_id")
    //private TrabajoPractico trabajoPractico;
    
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;
    
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoDiagnostico estado;

    public Diagnostico() {
    }
// CONSTRUCTOR
    public Diagnostico(TrabajoPractico trabajoPractico, String descripcion, EstadoDiagnostico estado) {
        //this.trabajoPractico = trabajoPractico;
        this.descripcion = descripcion;
        this.estado = estado;
    }
//GETTER AND SETTER
//    public TrabajoPractico getTrabajoPractico() {
//        return trabajoPractico;
//    }
//
//    public void setTrabajoPractico(TrabajoPractico trabajoPractico) {
//        this.trabajoPractico = trabajoPractico;
//    }
    
     public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoDiagnostico getEstado() {
        return estado;
    }

    public void setEstado(EstadoDiagnostico estado) {
        this.estado = estado;
    }

    @Override
    public void validar() throws GenericException {
        if(materia == null){ // trabajoPractico
             throw new DiagnosticoticoException("El trabajo practico no debe ser nulo");
        }
    }
    
public enum EstadoDiagnostico {

    ANULADO {
        @Override
        public String toString() {
            return "Anulado";
        }
    },
    PENDIENTE {
        @Override
        public String toString() {
            return "Pendiente";
        }
    },
    EN_PROCESO {
        @Override
        public String toString() {
            return "En Proceso";
        }
    },
    REALIZADO {
        @Override
        public String toString() {
            return "Realizado";
        }
    };
}    
    
}