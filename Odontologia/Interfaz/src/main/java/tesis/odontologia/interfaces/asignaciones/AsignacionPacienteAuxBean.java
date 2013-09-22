/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.utils.FechaUtils;

/**
 *
 * @author alespe
 */
public class AsignacionPacienteAuxBean {

    private String paciente;
    private String alumno;
    private String fecha;

    public AsignacionPacienteAuxBean(AsignacionPaciente asignacionPaciente) {
    this.paciente=asignacionPaciente.getPaciente().toString();
    this.alumno=asignacionPaciente.getAlumno().toString();
    this.fecha= FechaUtils.fechaConSeparador(asignacionPaciente.getFechaAsignacion());
    }
    

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
