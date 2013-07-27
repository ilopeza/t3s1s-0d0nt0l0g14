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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import tesis.odontologia.core.domain.Generic;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.HistoriaClinicaException;

/**
 *
 * @author alespe
 */
@Entity
public class HistoriaClinica extends Generic {

    @Column(nullable = false)
    @NotNull
    private int numero;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Calendar fechaApertura;
    //se tiene que definir la persona que va  aca.
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id")
    @Valid
    private Persona realizoHistoriaClinica;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "historiaclinica_id")
    @Valid
    private List<Atencion> atencion;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "historiaclinica_id")
    @Valid
    private List<Diagnostico> diagnostico;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    @NotNull
    @JoinColumn(name = "historiaClinica_id")
    private List<DetalleHistoriaClinica> detallesHC;

    //CONSTRUCTORS
    public HistoriaClinica() {
    }

    public HistoriaClinica(int numero, Calendar fechaApertura, Persona realizoHistoriaClinica, List<Atencion> atencion, List<Diagnostico> diagnostico) {
        this.numero = numero;
        this.fechaApertura = fechaApertura;
        this.realizoHistoriaClinica = realizoHistoriaClinica;
        this.atencion = atencion;
        this.diagnostico = diagnostico;
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

    public List<Diagnostico> getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(List<Diagnostico> diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<DetalleHistoriaClinica> getDetallesHC() {
        return detallesHC;
    }

    public void setDetallesHC(List<DetalleHistoriaClinica> detallesHC) {
        this.detallesHC = detallesHC;
    }

    private void addDetalle(DetalleHistoriaClinica dhc) {
        if (dhc == null) {
            return;
        }
        detallesHC.add(dhc);
    }

    @Override
    public void validar() throws GenericException {

        if (realizoHistoriaClinica == null) {
            throw new HistoriaClinicaException("La persona que realizó la historia clinica no puede ser nula");
        }
        realizoHistoriaClinica.validar();
    }

    public static HistoriaClinica createDefault() {
        HistoriaClinica hc = new HistoriaClinica();

        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G1P1, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G1P2, 1));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G1P3, 1));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G2P1, 2));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G2P2, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G2P3, 2));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G2P4, 2));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P1, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P2, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P3, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P4, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P5, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P6, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P7, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P8, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P9, 3));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G3P10, 3));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G3P11, 3));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G4P1, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G4P2, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G4P3, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G4P4, 4));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P1, 5));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P2, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P3, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P4, 5));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P5, 5));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P6, 4));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P7, 4));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G5P8, 5));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G5P9, 4));
        
        
        return hc;
    }
}
