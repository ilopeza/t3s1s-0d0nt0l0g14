/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.Calendar;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.asignaciones.QAsignacionPaciente;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;

/**
 *
 * @author Ignacio
 */
public class AsignacionPacienteSpecs {
    
    private static final QAsignacionPaciente $ = QAsignacionPaciente.asignacionPaciente;
    
    public static BooleanExpression byAlumno(Alumno a){
        return $.alumno.id.eq(a.getId());
    }
    
    public static BooleanExpression byPaciente(Paciente p){
        return $.paciente.id.eq(p.getId());
    }
    
    public static BooleanExpression byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion ea){
        return $.estado.eq(ea);
    }
    
    public static BooleanExpression byFecha(Calendar cal){
        return $.fechaAsignacion.eq(cal);
    }
}
