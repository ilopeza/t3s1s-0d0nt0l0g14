/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.exception.DiagnosticoticoException;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author alespe
 */
@Entity
public class Diagnostico extends Generic{
    
    private TrabajoPractico trabajoPractico;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoDiagnostico estado;

    public Diagnostico() {
    }
// CONSTRUCTOR
    public Diagnostico(TrabajoPractico trabajoPractico, String descripcion, EstadoDiagnostico estado) {
        this.trabajoPractico = trabajoPractico;
        this.descripcion = descripcion;
        this.estado = estado;
    }
//GETTER AND SETTER
    public TrabajoPractico getTrabajoPractico() {
        return trabajoPractico;
    }

    public void setTrabajoPractico(TrabajoPractico trabajoPractico) {
        this.trabajoPractico = trabajoPractico;
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
        if(trabajoPractico ==null){
             throw new DiagnosticoticoException("El trabajo practico no debe ser nulo");
        }
       trabajoPractico.validar();
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
