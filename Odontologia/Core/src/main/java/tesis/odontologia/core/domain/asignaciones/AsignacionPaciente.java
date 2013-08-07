/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.asignaciones;

import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.exception.AsignacionPacienteException;
import tesis.odontologia.core.exception.GenericException;


/**
 *
 * @author alespe
 */

@Entity
public class AsignacionPaciente extends Generic{
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "La fecha de asignacion no puede ser nula.")
    private Calendar fechaAsignacion;
    
    @OneToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "El paciente de la asignacion no puede ser nulo.")
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name = "alumno_id")
    @NotNull(message = "El alumno de la asignacion no puede ser nulo.")
    private Alumno alumno;
    
    @OneToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;
    
    @Enumerated(EnumType.STRING)
    private EstadoAsignacion estado = EstadoAsignacion.PENDIENTE;

    public AsignacionPaciente() {
    }

    public AsignacionPaciente(Calendar fechaAsignacion, Paciente paciente, Alumno alumno, EstadoAsignacion estado, Materia materia) {
        this.fechaAsignacion = fechaAsignacion;
        this.paciente = paciente;
        this.alumno = alumno;
        this.estado = estado;
        this.materia = materia;
    }

    public Calendar getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Calendar fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public EstadoAsignacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignacion estado) {
        this.estado = estado;
    }
    
    
    
    @Override
    public void validar() throws GenericException {
        if(fechaAsignacion == null){ // trabajoPractico
             throw new AsignacionPacienteException("La fecha de asignación no debe ser nulo");
        }
    }

    /**
     * @return the materia
     */
    public Materia getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
    
    
    public enum EstadoAsignacion {

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
