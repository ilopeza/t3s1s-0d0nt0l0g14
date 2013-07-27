/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.paciente;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Maxi
 */
@Embeddable
public class Domicilio implements Serializable {

    @Column(length = 150)
    @Size(min = 1, max = 150)
    @NotNull
    private String domicilioActual;
    
    @Column(length = 150)
    @Size(min = 1, max = 150)
    @NotNull
    private String ciudadActual;
    
    @Column(length = 150)
    @Size(min = 1, max = 150)
    private String residenciaAnterior;
    
    @Column(length = 150)
    @Size(min = 1, max = 150)
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
