/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import java.util.Calendar;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author Mau
 */
@Entity
@DiscriminatorValue(value = "GENERICA")
public class AtencionGenerica extends Atencion{

    public AtencionGenerica() {
    }
    
    public AtencionGenerica(Calendar fechaAtencion, String motivoConsultaOdontologica, String comoComenzo, String cuantoTiempoHace, String donde, String aQueLoAtribuye, String queHizo, String descripcionProcedimiento, AsignacionPaciente asignacion){
        super(fechaAtencion, motivoConsultaOdontologica, comoComenzo, cuantoTiempoHace, donde,  aQueLoAtribuye, queHizo, descripcionProcedimiento, asignacion);
    }
    
    @Override
    public void validar() throws GenericException {
        super.validar(); //To change body of generated methods, choose Tools | Templates.
    }
}