/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @NotNull(message = "El numero de historia clinica no puede ser nulo.")
    private int numero;
    
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "La fecha de apertura de historia clinica no puede ser nula.")
    private Calendar fechaApertura;
    
    //se tiene que definir la persona que va  aca.
    @OneToOne
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
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Valid
    @NotNull(message = "Error en generacion de historia clinica. Imposible guardar historia clinica vacia.")
    @JoinColumn(name = "historiaClinica_id")
    private List<DetalleHistoriaClinica> detallesHC;

    //CONSTRUCTORS
    public HistoriaClinica() {
        fechaApertura = Calendar.getInstance();
        atencion = new ArrayList<Atencion>();
        diagnostico = new ArrayList<Diagnostico>();
        detallesHC = new ArrayList<DetalleHistoriaClinica>();
    }

    public HistoriaClinica(int numero, Calendar fechaApertura, Persona realizoHistoriaClinica,
            List<Atencion> atencion, List<Diagnostico> diagnostico) {
        fechaApertura = Calendar.getInstance();
        atencion = new ArrayList<Atencion>();
        diagnostico = new ArrayList<Diagnostico>();
        detallesHC = new ArrayList<DetalleHistoriaClinica>();
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
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G6P1, 6));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G6P2, 6));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G6P3, 6));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G6P4, 6));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G7P1, 7));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G7P2, 7));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G8P1, 8));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P2, 8));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P3, 8));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P4, 8));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G8P5, 8));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G8P6, 8));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G9P1, 9));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G9P2, 9));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G9P3, 9));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G9P4, 9));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P1, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G10P2, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G10P3, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G10P4, 10));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P5, 10));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P6, 10));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G10P7, 10));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P8, 10));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G10P9, 10));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G11P1, 11));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G11P2, 11));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G11P3, 11));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G11P4, 11));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G11P5, 11));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G11P6, 11));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G12P1, 12));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G12P2, 12));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G13P1, 13));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G13P2, 13));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G13P3, 13));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G13P4, 13));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G14P1, 14));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G14P2, 14));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G14P3, 14));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G14P4, 14));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P1, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P2, 15));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P3, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P4, 15));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P5, 15));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P6, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P7, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P8, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P9, 15));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G15P10, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P11, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P12, 15));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G15P13, 15));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G16P1, 16));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G16P2, 16));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G16P3, 16));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G17P1, 17));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P2, 17));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P3, 17));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P4, 17));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G17P5, 17));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P6, 17));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G17P7, 17));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G18P1, 18));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G18P2, 18));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G18P3, 18));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G18P4, 18));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G18P5, 18));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G19P1, 19));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P2, 19));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P3, 19));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P4, 19));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G19P5, 19));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G19P6, 19));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P7, 19));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P8, 19));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G19P9, 19));
        
        //Preguntas para mujeres
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P1, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P2, 20));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P3, 20));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P4, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P5, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P6, 20));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P7, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P8, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P9, 20));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P10, 20));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P11, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P12, 20));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G20P13, 20));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G20P14, 20));
        //Fin de preguntas para mujeres
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P1, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P2, 21));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P3, 21));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P4, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P5, 21));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P6, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P7, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P8, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P9, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P10, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P11, 21));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G21P12, 21));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G21P13, 21));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G22P1, 22));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G22P2, 22));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G22P3, 22));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G22P4, 22));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G22P5, 22));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G23P1, 23));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G23P2, 23));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G24P1, 24));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G24P2, 24));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G25P1, 25));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G25P2, 25));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G25P3, 25));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G25P4, 25));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G25P5, 25));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G26P1, 26));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G26P2, 26));
        
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G27P1, 27));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G27P2, 27));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G27P3, 27));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G27P4, 27));
        
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P1, 28));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P2, 28));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G28P3, 28));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P4, 28));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G28P5, 28));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G28P6, 28));
        
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G29P1, 29));
        hc.addDetalle(new CampoSiNo(DetalleHistoriaClinica.G29P2, 29));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G29P3, 29));
        hc.addDetalle(new CampoEnumerable(DetalleHistoriaClinica.G29P4, 29));
        hc.addDetalle(new CampoDetalle(DetalleHistoriaClinica.G29P5, 29));
        
        return hc;
    }
}
