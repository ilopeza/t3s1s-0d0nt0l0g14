/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import tesis.odontologia.core.dao.AsignacionPacienteDao;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;

/**
 *
 * @author Mau
 */

public class ScheduleServiceImpl{

    private List<AsignacionPaciente> asignacionesAnalizadas = new ArrayList<AsignacionPaciente>();
    private BooleanExpression predicate;
    private AsignacionPacienteDao asignacionPacienteDao;
    
//    @Scheduled(cron = "*/15 * * * * ?")
//    public void demoServiceMethod() {
//        System.out.println("Method executed at every 5 seconds. Current time is :: " + new Date());
//        //cargarAsignaciones();
//        predicate = (AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE));
//
//
//        asignacionesAnalizadas = (List<AsignacionPaciente>) asignacionPacienteDao.findAll(predicate);
//
//        for (AsignacionPaciente asignacionAnalizada : asignacionesAnalizadas) {
//            Calendar fechaActual = Calendar.getInstance();
//
//            if (fechaActual.get(Calendar.HOUR_OF_DAY) - asignacionAnalizada.getFechaAsignacion().get(Calendar.HOUR_OF_DAY) > 24) {
//                asignacionAnalizada.setEstado(AsignacionPaciente.EstadoAsignacion.ANULADO);
//                //GUARDAR NUEVAMENTE LA ASIGNACION CON EL ESTADO ACTUALIZADO.
//            }
//        }
//
//        System.out.println("No se actualizaron asignaciones. Current time is: " + new Date());
//
//    }

    /**
     * @return the asignacionesAnalizadas
     */
    public List<AsignacionPaciente> getAsignacionesAnalizadas() {
        return asignacionesAnalizadas;
    }

    /**
     * @param asignacionesAnalizadas the asignacionesAnalizadas to set
     */
    public void setAsignacionesAnalizadas(List<AsignacionPaciente> asignacionesAnalizadas) {
        this.asignacionesAnalizadas = asignacionesAnalizadas;
    }

    /**
     * @return the predicate
     */
    public BooleanExpression getPredicate() {
        return predicate;
    }

    /**
     * @param predicate the predicate to set
     */
    public void setPredicate(BooleanExpression predicate) {
        this.predicate = predicate;
    }

}
