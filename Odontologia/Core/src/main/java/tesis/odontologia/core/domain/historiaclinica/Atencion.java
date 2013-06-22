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
import javax.persistence.Temporal;
import tesis.odontologia.core.domain.Generic;
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
    private Calendar fechaAtencion;
    @Column(nullable = false, length = 700)
    private String motivoConsultaOdontologica;
    @Column(length = 255)
    private String comoComenzo;
    @Column(length = 255)
    private String cuantoTiempoHace;
    @Column(length = 255)
    private String donde;
    @Column(length = 255)
    private String aQueLoAtribuye;
    @Column(length = 255)
    private String queHizo;

    
    //CONSTRUCTORS
    public Atencion() {
    }

    public Atencion(Calendar fechaAtencion, String motivoConsultaOdontologica, String comoComenzo, String cuantoTiempoHace, String donde, String aQueLoAtribuye, String queHizo) {
        this.fechaAtencion = fechaAtencion;
        this.motivoConsultaOdontologica = motivoConsultaOdontologica;
        this.comoComenzo = comoComenzo;
        this.cuantoTiempoHace = cuantoTiempoHace;
        this.donde = donde;
        this.aQueLoAtribuye = aQueLoAtribuye;
        this.queHizo = queHizo;
    }
    
    //GETTERS AND SETTERS
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

    public String getComoComenzo() {
        return comoComenzo;
    }

    public void setComoComenzo(String comoComenzo) {
        this.comoComenzo = comoComenzo;
    }

    public String getCuantoTiempoHace() {
        return cuantoTiempoHace;
    }

    public void setCuantoTiempoHace(String cuantoTiempoHace) {
        this.cuantoTiempoHace = cuantoTiempoHace;
    }

    public String getDonde() {
        return donde;
    }

    public void setDonde(String donde) {
        this.donde = donde;
    }

    public String getaQueLoAtribuye() {
        return aQueLoAtribuye;
    }

    public void setaQueLoAtribuye(String aQueLoAtribuye) {
        this.aQueLoAtribuye = aQueLoAtribuye;
    }

    public String getQueHizo() {
        return queHizo;
    }

    public void setQueHizo(String queHizo) {
        this.queHizo = queHizo;
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
