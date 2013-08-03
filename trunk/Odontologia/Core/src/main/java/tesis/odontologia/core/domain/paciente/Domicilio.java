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

    @Column(length = 100)
    @Size(min = 1, max = 100, message = "La calle debe tener entre 1 y 100 caracteres.")
    @NotNull(message = "La calle no puede ser nula.")
    private String calle;
    
    @Column(length = 10)
    @Size(min = 1, max = 10, message = "El numero de la calle debe tener entre 1 y 10 caracteres.")
    @NotNull(message = "El numero de la calle no puede ser nulo.")
    private String calleNumero;
    
    @Column(length = 100)
    @Size(min = 1, max = 100, message = "El barrio debe tener entre 1 y 100 caracteres.")
    private String barrio;
    
    @Column(length = 10)
    @Size(min = 1, max = 10, message = "El departamento debe tener entre 1 y 10 caracteres.")
    private String departamento;
    
    @Column(length = 10)
    @Size(min = 1, max = 10, message = "El piso debe tener entre 1 y 10 caracteres.")
    private String piso;
    
    @Column(length = 100)
    @Size(min = 1, max = 100, message = "La ciudad actual debe tener entre 1 y 100 caracteres.")
    @NotNull(message = "La ciudad actual no puede ser nula.")
    private String ciudadActual;
    
    @Column(length = 150)
    @Size(min = 1, max = 150, message = "La residencia anterior debe tener entre 1 y 150 caracteres.")
    private String residenciaAnterior;
    
    @Column(length = 150)
    @Size(min = 1, max = 150, message = "La ciudad anterior debe tener entre 1 y 150 caracteres.")
    private String ciudadAnterior;

    //CONSTRUCTORS
    public Domicilio() {
    }

    public Domicilio(String calle, String calleNumero, String ciudadActual) {
        this.calle = calle;
        this.calleNumero = calleNumero;
        this.ciudadActual = ciudadActual;
    }
    
    //GETTERS AND SETTERS
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCalleNumero() {
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero) {
        this.calleNumero = calleNumero;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
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
