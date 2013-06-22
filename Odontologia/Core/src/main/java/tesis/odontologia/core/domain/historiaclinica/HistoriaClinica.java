/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.HistoriaClinicaException;

/**
 *
 * @author alespe
 */
@Entity
public class HistoriaClinica extends Generic{

    @Column(nullable = false)
    private int numero;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar fechaApertura;
    
    //se tiene que definir la persona que va  aca.
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="persona_id")
    private Persona realizoHistoriaClinica;
    
    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="historiaclinica_id")
    private List<Atencion> atencion;
    
    //CONSTRUCTORS
    public HistoriaClinica() {
    }

    public HistoriaClinica(int numero, Calendar fechaApertura, Persona realizoHistoriaClinica, List<Atencion> atencion) {
        this.numero = numero;
        this.fechaApertura = fechaApertura;
        this.realizoHistoriaClinica = realizoHistoriaClinica;
        this.atencion = atencion;
    }
    
    //GETTERS AND SETTERS
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Calendar getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Calendar fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Persona getRealizoHistoriaClinica() {
        return realizoHistoriaClinica;
    }

    public void setRealizoHistoriaClinica(Persona realizoHistoriaClinica) {
        this.realizoHistoriaClinica = realizoHistoriaClinica;
    }

    public List<Atencion> getAtencion() {
        return atencion;
    }

    public void setAtencion(List<Atencion> atencion) {
        this.atencion = atencion;
    }

    
    
    @Override
    public void validar() throws GenericException {
        
        if(realizoHistoriaClinica == null){
            throw new HistoriaClinicaException("La persona que realizó la historia clinica no puede ser nula");
        }
        realizoHistoriaClinica.validar();
    }
    
    
}
