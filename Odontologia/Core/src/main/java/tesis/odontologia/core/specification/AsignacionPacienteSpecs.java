/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.Calendar;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.asignaciones.QAsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.QDiagnostico;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.QTrabajoPractico;
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
    
    public static BooleanExpression byFechaDesdeHasta(Calendar calDesde, Calendar calHasta){
        return $.fechaAsignacion.between(calDesde, calHasta);
    }
    
    public static BooleanExpression byMateria(Materia m){
        QDiagnostico d = QDiagnostico.diagnostico;
        QTrabajoPractico tp = QTrabajoPractico.trabajoPractico;
        return new JPASubQuery().from(d).where($.diagnostico.eq(d).and(new JPASubQuery().from(tp).where(d.trabajoPractico.eq(tp).and(d.trabajoPractico.in(m.getTrabajoPractico()))).exists())).exists();
    }
    
    public static BooleanExpression byTrabajoPractico(TrabajoPractico tp){
        QDiagnostico d = QDiagnostico.diagnostico;
        return new JPASubQuery().from(d).where($.diagnostico.eq(d).and(d.trabajoPractico.eq(tp))).exists();
    }
    
    public static QTrabajoPractico getTrabajoPractico(){
        return $.diagnostico.trabajoPractico;
    }
 
    public static BooleanExpression byNombreOApellido(String nombApp) {
        return $.paciente.nombre.containsIgnoreCase(nombApp).or($.paciente.apellido.containsIgnoreCase(nombApp));
    }
    
     public static BooleanExpression byCatedra(Catedra c){
        return $.catedra.id.eq(c.getId());
    }
     
}
