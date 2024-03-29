/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.asignaciones;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.materia.Catedra;
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
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @NotNull(message = "La fecha de asignacion no puede ser nula.")
    private Calendar fechaAsignacion;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @NotNull
    private Calendar fechaCreacionAsignacion;
    
    @Column(length = 100)
    @Size(min = 1, max = 100, message = "La descripción de la cancelación debe tener entre 1 y 100 caracteres.")
    private String motivoCancelacion;
    
    @OneToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "El paciente de la asignacion no puede ser nulo.")
    private Paciente paciente;
    
    @OneToOne
    @JoinColumn(name = "alumno_id")
    @NotNull(message = "El alumno de la asignacion no puede ser nulo.")
    private Alumno alumno;
    
    @OneToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
    
    @ManyToOne
    @JoinColumn(name = "diagnostico_id")
    @NotNull(message = "El diagnóstico de la asignacion no puede ser nulo.")
    private Diagnostico diagnostico;
    
    @ManyToOne
    @JoinColumn(name = "catedra_id")
    @NotNull(message = "La cátedra de la asignacion no puede ser nulo.")
    private Catedra catedra;
    
    @Enumerated(EnumType.STRING)
    private EstadoAsignacion estado = EstadoAsignacion.PENDIENTE;

    public AsignacionPaciente() {
    }

    public AsignacionPaciente(Calendar fechaAsignacion, Paciente paciente, Alumno alumno, Profesor profesor, Diagnostico diagnostico, Catedra catedra, String motivoCancelacion) {
        this.fechaAsignacion = fechaAsignacion;
        this.paciente = paciente;
        this.alumno = alumno;
        this.profesor = profesor;
        this.diagnostico = diagnostico;
        this.catedra = catedra;
        this.motivoCancelacion = motivoCancelacion;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Catedra getCatedra() {
        return catedra;
    }

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    
    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    /**
     * @return the fechaCreacionAsignacion
     */
    public Calendar getFechaCreacionAsignacion() {
        return fechaCreacionAsignacion;
    }

    /**
     * @param fechaCreacionAsignacion the fechaCreacionAsignacion to set
     */
    public void setFechaCreacionAsignacion(Calendar fechaCreacionAsignacion) {
        this.fechaCreacionAsignacion = fechaCreacionAsignacion;
    }

   
    public enum EstadoAsignacion {

    ANULADA {
        @Override
        public String toString() {
            return "Anulada";
        }
    },
    PENDIENTE {
        @Override
        public String toString() {
            return "Pendiente";
        }
    },
    CONFIRMADA {
        @Override
        public String toString() {
            return "Confirmada";
        }
    },
    CANCELADA {
        @Override
        public String toString() {
            return "Cancelada";
        }
    },
    AUTORIZADA {
        @Override
        public String toString() {
            return "Autorizada";
        }
        },
    REGISTRADA {
        @Override
        public String toString() {
            return "Atención Registrada";
        }
        },
    NOREGISTRADA {
        @Override
        public String toString() {
            return "Atención no Registrada";
        }
        }
    }; 
    
}
