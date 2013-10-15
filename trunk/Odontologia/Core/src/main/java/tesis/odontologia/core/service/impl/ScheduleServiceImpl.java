/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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

    /**
     * Método que se ejecuta cada 6 horas. Busca todas las asignaciones en estado PENDIENTE
     * y verifica que no hayan transcurrido mas de 24 horas desde la asignacion a la fecha actual. 
     * En caso de que el tiempo sea mayor a 24 horas, se actualiza el estado de la asignacion al estado ANULADA.
     */
    @Scheduled(cron = "* */6 * * * ?")
    public void demoServiceMethod() {
        List<AsignacionPaciente> asigAnuladas = new ArrayList<AsignacionPaciente>();
        List<AsignacionPaciente> asignacionesBD = (List<AsignacionPaciente>) asignacionPacienteDao.findAll(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE));

        for (AsignacionPaciente asignacionAnalizada : asignacionesBD) {
            Calendar fechaActual = Calendar.getInstance();
            long milis1 = fechaActual.getTimeInMillis();
            long milis2 = asignacionAnalizada.getFechaAsignacion().getTimeInMillis();

            // calcula la diferencia en milisengundos
            long diff = milis2 - milis1;

            // calcula la diferencia en horas
            long diffHours = diff / (60 * 60 * 1000);

            if (Math.abs(diffHours) > 24) {
                asignacionAnalizada.setEstado(AsignacionPaciente.EstadoAsignacion.ANULADA);
                asigAnuladas.add(asignacionAnalizada);                
            }
        }        
        asignacionPacienteDao.save(asigAnuladas);        
    }
}
