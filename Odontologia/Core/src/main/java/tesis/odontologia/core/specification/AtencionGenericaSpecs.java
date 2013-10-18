/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import tesis.odontologia.core.domain.asignaciones.QAsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.historiaclinica.QAtencion;
import tesis.odontologia.core.domain.historiaclinica.QAtencionGenerica;
import tesis.odontologia.core.domain.historiaclinica.QDiagnostico;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.QTrabajoPractico;
import tesis.odontologia.core.domain.materia.TrabajoPractico;


/**
 *
 * @author Maxi
 */
public class AtencionGenericaSpecs {

    private static final QAtencionGenerica $ = QAtencion.atencion.as(QAtencionGenerica.class);

    public static BooleanExpression byCatedra(Catedra c) {
        QAsignacionPaciente ap =QAsignacionPaciente.asignacionPaciente;
        return new JPASubQuery().from(ap).where($.asignacionPaciente.eq(ap).and(ap.catedra.eq(c))).exists();
    }

    public static BooleanExpression byTrabajoPractico(TrabajoPractico tp) {
        QAsignacionPaciente ap =QAsignacionPaciente.asignacionPaciente;
        QDiagnostico d = QDiagnostico.diagnostico;
        return new JPASubQuery().from(ap).where($.asignacionPaciente.eq(ap).and(new JPASubQuery().from(d).where(ap.diagnostico.eq(d).and(d.trabajoPractico.eq(tp))).exists())).exists();
    }
    
    public static BooleanExpression byMateria(Materia m) {
        QAsignacionPaciente ap =QAsignacionPaciente.asignacionPaciente;
        QDiagnostico d = QDiagnostico.diagnostico;
         QTrabajoPractico tp = QTrabajoPractico.trabajoPractico;
        //return new JPASubQuery().from(d).where($.diagnostico.eq(d).and(new JPASubQuery().from(tp).where(d.trabajoPractico.eq(tp).and(d.trabajoPractico.in(m.getTrabajoPractico()))).exists())).exists();
        return new JPASubQuery().from(ap).where($.asignacionPaciente.eq(ap).and(new JPASubQuery().from(d).where(ap.diagnostico.eq(d).and(new JPASubQuery().from(tp).where(d.trabajoPractico.eq(tp).and(d.trabajoPractico.in(m.getTrabajoPractico()))).exists())).exists())).exists();
        //return null;
    }
    
    public static BooleanExpression byFecha(Calendar cal){
        return $.fechaAtencion.eq(cal);
    }
    
    public static BooleanExpression byFechaDesdeHasta(Calendar calDesde, Calendar calHasta){
        return $.fechaAtencion.between(calDesde, calHasta);
    }
}
