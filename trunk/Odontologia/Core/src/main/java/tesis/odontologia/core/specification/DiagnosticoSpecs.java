/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.historiaclinica.QDiagnostico;
import tesis.odontologia.core.domain.materia.TrabajoPractico;


/**
 *
 * @author Maxi
 */
public class DiagnosticoSpecs {

    private static final QDiagnostico $ = QDiagnostico.diagnostico;

    public static BooleanExpression byEstado(Diagnostico.EstadoDiagnostico estado) {
        return $.estado.eq(estado);
    }

    public static BooleanExpression byTrabajoPractico(TrabajoPractico tp) {
        return $.trabajoPractico.eq(tp);
    }

    public static BooleanExpression byAsignacion(AsignacionPaciente asignacion) {
        return $.asignacion.eq(asignacion);
    }

}
