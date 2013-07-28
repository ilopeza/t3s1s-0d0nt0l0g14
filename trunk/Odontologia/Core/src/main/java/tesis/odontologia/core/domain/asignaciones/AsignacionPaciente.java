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
    @NotNull
    private Calendar fechaAsignacion;
    
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;
    
    @Enumerated(EnumType.STRING)
    private AsignacionPaciente.EstadoAsignacion estado;

    public AsignacionPaciente() {
    }

    public AsignacionPaciente(Calendar fechaAsignacion, Paciente paciente, Alumno alumno, AsignacionPaciente.EstadoAsignacion estado) {
        this.fechaAsignacion = fechaAsignacion;
        this.paciente = paciente;
        this.alumno = alumno;
        this.estado = estado;
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

    public AsignacionPaciente.EstadoAsignacion getEstado() {
        return estado;
    }

    public void setEstado(AsignacionPaciente.EstadoAsignacion estado) {
        this.estado = estado;
    }
    
    @Override
    public void validar() throws GenericException {
        if(fechaAsignacion == null){ // trabajoPractico
             throw new AsignacionPacienteException("La fecha de asignación no debe ser nulo");
        }
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
