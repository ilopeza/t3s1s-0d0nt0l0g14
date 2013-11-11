/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.historiaclinica.QHistoriaClinica;
import tesis.odontologia.core.domain.paciente.Paciente;


/**
 *
 * @author Maxi
 */
public class HistoriaClinicaSpecs {

    private static final QHistoriaClinica $ = QHistoriaClinica.historiaClinica;

    public static BooleanExpression byNumero(Integer numero) {
        return $.numero.eq(numero);
    }
    
    public static BooleanExpression byPaciente(Paciente p) {
        return $.eq(p.getHistoriaClinica());
    }

}
