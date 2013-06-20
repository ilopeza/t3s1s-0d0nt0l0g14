/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.paciente;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Maxi
 */
@Embeddable
public class Domicilio implements Serializable {

    @Column(nullable = false)
    private String domicilioActual;
    
    @Column(nullable = false)
    private String ciudadActual;
    
    @Column(nullable = true)
    private String residenciaAnterior;
    
    @Column(nullable = true)
    private String ciudadAnterior;

    //CONSTRUCTORS
    public Domicilio() {
    }

    public Domicilio(String domicilioActual, String ciudadActual) {
        this.domicilioActual = domicilioActual;
        this.ciudadActual = ciudadActual;
    }

    public Domicilio(String domicilioActual, String ciudadActual, String residenciaAnterior, String ciudadAnterior) {
        this.domicilioActual = domicilioActual;
        this.ciudadActual = ciudadActual;
        this.residenciaAnterior = residenciaAnterior;
        this.ciudadAnterior = ciudadAnterior;
    }

    //GETTERS AND SETTERS
    public String getDomicilioActual() {
        return domicilioActual;
    }

    public void setDomicilioActual(String domicilioActual) {
        this.domicilioActual = domicilioActual;
    }

    public String getCiudadActual() {
        return ciudadActual;
    }

    public void setCiudadActual(String ciudadActual) {
        this.ciudadActual = ciudadActual;
    }

    public String getResidenciaAnterior() {
        return residenciaAnterior;
    }

    public void setResidenciaAnterior(String residenciaAnterior) {
        this.residenciaAnterior = residenciaAnterior;
    }

    public String getCiudadAnterior() {
        return ciudadAnterior;
    }

    public void setCiudadAnterior(String ciudadAnterior) {
        this.ciudadAnterior = ciudadAnterior;
    }

    //VALIDATES
    void validar() throws Exception {
    }
}
