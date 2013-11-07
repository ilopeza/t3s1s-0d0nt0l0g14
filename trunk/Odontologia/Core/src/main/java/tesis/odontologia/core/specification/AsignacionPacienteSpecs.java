/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
    
    public static BooleanExpression byNoMostrarCanceladas() {
        return $.estado.ne(AsignacionPaciente.EstadoAsignacion.CANCELADA);
    }
    
    public static BooleanExpression byFecha(Calendar cal){
        return $.fechaAsignacion.eq(cal);
    }
    
    public static BooleanExpression byFechaDesdeHasta(Calendar calDesde, Calendar calHasta){
        return $.fechaAsignacion.between(calDesde, calHasta);
    }
    
    public static BooleanExpression byDate(Date fecha) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(fecha);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(fecha);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);
        return $.fechaAsignacion.between(cal1, cal2);
    }
    
    public static BooleanExpression byMateria(Materia m){
        QDiagnostico d = QDiagnostico.diagnostico;
        QTrabajoPractico tp = QTrabajoPractico.trabajoPractico;
        return new JPASubQuery().from(d).where($.diagnostico.eq(d).and(new JPASubQuery().from(tp).where(d.trabajoPractico.eq(tp).and(d.trabajoPractico.in(m.getTrabajoPractico()))).exists())).exists();
    }
    
    public static BooleanExpression byMateria2(Materia materia) {
        QDiagnostico d = QDiagnostico.diagnostico;
        return new JPASubQuery().from(d).where($.diagnostico.eq(d).and(d.materia.eq(materia))).exists();
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
     
     public static BooleanExpression byEstados(List<AsignacionPaciente.EstadoAsignacion> estados) {
         return $.estado.in(estados);
     }
     
}
