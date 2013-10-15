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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import tesis.odontologia.core.dao.AsignacionPacienteDao;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;

/**
 *
 * @author Mau
 */

public class ScheduleServiceImpl {

    @Autowired
    private AsignacionPacienteDao asignacionPacienteDao;
    
//    @Scheduled(cron = "*/15 * * * * ?")
//    public void demoServiceMethod() {
//        System.out.println("Method executed at every 5 seconds. Current time is :: " + new Date());
//        List<AsignacionPaciente> asigAnuladas = new ArrayList<AsignacionPaciente>();
//        List<AsignacionPaciente> asignacionesBD = (List<AsignacionPaciente>) asignacionPacienteDao.findAll(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE));
//
//        for (AsignacionPaciente asignacionAnalizada : asignacionesBD) {
//            Calendar fechaActual = Calendar.getInstance();
//
//            if (fechaActual.get(Calendar.HOUR_OF_DAY) - asignacionAnalizada.getFechaAsignacion().get(Calendar.HOUR_OF_DAY) > 24) {
//                asignacionAnalizada.setEstado(AsignacionPaciente.EstadoAsignacion.ANULADA);
//                asigAnuladas.add(asignacionAnalizada);
//                //GUARDAR NUEVAMENTE LA ASIGNACION CON EL ESTADO ACTUALIZADO.
//            }
//        }
//        System.out.println("Actualizando asignaciones");
//        asignacionPacienteDao.save(asigAnuladas);
//        System.out.println("Asignaciones guardadas: " + asigAnuladas.toString());
//        System.out.println("No se actualizaron asignaciones. Current time is: " + new Date());
//}

    }
