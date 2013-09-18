/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
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
    
    @ManyToOne
    @JoinColumn(name = "trabajopractico_id")
    @NotNull(message = "El trabajo practico al cual aplica el diagnostico no puede ser nulo.")
    private TrabajoPractico trabajoPractico;
    
    @ManyToOne
    @JoinColumn(name = "materia_id")
    @NotNull(message = "La materia del diagnostico no puede ser nulo.")
    private Materia materia;
    /**
     * esto es para saber con que asignacion se resolvio el diagnostico.
     * Si este campo es nulo, el diagnostico deberia estar en estado pendiente o cancelado.
     * Si este campo no es nulo, el diagnostico deberia estar en estado en proceso o realizado
     */
    @ManyToOne
    @JoinColumn(name = "asignacion_id")
    private AsignacionPaciente asignacion;
    
    @Column(length = 255)
    @Size(min = 1, max = 255, message = "La descripcion del medico debe tener entre 1 y 255 caracteres.")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private EstadoDiagnostico estado = EstadoDiagnostico.PENDIENTE;

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

    public AsignacionPaciente getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionPaciente asignacion) {
        this.asignacion = asignacion;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
    //Hashcode & Equals
    
    @Override
    public void validar() throws GenericException {
        if(trabajoPractico == null){
             throw new DiagnosticoticoException("El trabajo practico no debe ser nulo");
        }
    }

    public enum EstadoDiagnostico {

    CANCELADO {
        @Override
        public String toString() {
            return "Cancelado";
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
