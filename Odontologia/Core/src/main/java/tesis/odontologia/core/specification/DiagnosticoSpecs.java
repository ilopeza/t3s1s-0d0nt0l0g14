/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.historiaclinica.QDiagnostico;
import tesis.odontologia.core.domain.historiaclinica.QHistoriaClinica;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.paciente.QPaciente;


/**
 *
 * @author Maxi
 */
public class DiagnosticoSpecs {

    private static final QDiagnostico $ = QDiagnostico.diagnostico;
    
    public static BooleanExpression byId(Long nro) {
        return $.id.eq(nro);
    }
    
    public static BooleanExpression byMateria(Materia materia) {
        return $.materia.eq(materia);
    }

    public static BooleanExpression byEstado(Diagnostico.EstadoDiagnostico estado) {
        return $.estado.eq(estado);
    }

    public static BooleanExpression byTrabajoPractico(TrabajoPractico tp) {
        return $.trabajoPractico.eq(tp);
    }
    
    public static BooleanExpression byNombreODocPaciente(String filtro) {
        QHistoriaClinica h = QHistoriaClinica.historiaClinica;
        QPaciente p = QPaciente.paciente;
        return new JPASubQuery().from(h).where($.in(h.diagnostico).and(new JPASubQuery().from(p).where(p.historiaClinica.id.eq(h.id).and(p.nombre.containsIgnoreCase(filtro).or(p.apellido.containsIgnoreCase(filtro).or(p.documento.numero.eq(filtro))))).exists())).exists();
    }

    public static BooleanExpression byAsignacion(AsignacionPaciente asignacion) {
        return $.asignacion.eq(asignacion);
    }
    
}
