/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.exception.AtencionException;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author alespe
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Atencion extends Generic {
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "La fecha de atencion no puede ser nula.")
    private Calendar fechaAtencion;
    
    @Column(nullable = false, length = 700)
    @Size(min = 1, max = 700, message = "El motivo de la consulta debe tener entre 1 y 700 caracteres")
    @NotNull(message = "El motivo de la consulta no puede ser nulo.")
    private String motivoConsultaOdontologica;
        
    @Column(nullable = false, length = 700)
    @Size(min = 1, max = 700, message = "El campo descripcion debe tener entre 1 y 700 caracteres")
    @NotNull(message = "La descripcion del procedimiento no puede ser nula.")
    private String descripcionProcedimiento;
    
    @OneToOne
    @JoinColumn(name = "AsignacionPaciente_id")
    private AsignacionPaciente asignacionPaciente;
    
    //CONSTRUCTORS
    public Atencion() {
    }

    public Atencion(Calendar fechaAtencion, String motivoConsultaOdontologica, String descripcionProcedimiento, AsignacionPaciente asignacionPaciente) {
        this.fechaAtencion = fechaAtencion;
        this.motivoConsultaOdontologica = motivoConsultaOdontologica;
        this.descripcionProcedimiento = descripcionProcedimiento;
        this.asignacionPaciente = asignacionPaciente;
    }

    public Calendar getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Calendar fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getMotivoConsultaOdontologica() {
        return motivoConsultaOdontologica;
    }

    public void setMotivoConsultaOdontologica(String motivoConsultaOdontologica) {
        this.motivoConsultaOdontologica = motivoConsultaOdontologica;
    }

    public String getDescripcionProcedimiento() {
        return descripcionProcedimiento;
    }

    public void setDescripcionProcedimiento(String descripcionProcedimiento) {
        this.descripcionProcedimiento = descripcionProcedimiento;
    }
    
    public AsignacionPaciente getAsignacionPaciente() {
        return asignacionPaciente;
    }

    public void setAsignacionPaciente(AsignacionPaciente asignacionPaciente) {
        this.asignacionPaciente = asignacionPaciente;
    }
    
    @Override
    public void validar() throws GenericException {
        if(fechaAtencion == null){
            throw new AtencionException("La fecha no puede ser nula");
        }
        if(motivoConsultaOdontologica == null || motivoConsultaOdontologica.isEmpty()){
            throw new AtencionException("El motivo de consulta odontologica no puede ser nulo o vacio");
        }
    }

}
