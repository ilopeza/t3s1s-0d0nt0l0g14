/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.QPersona;
import tesis.odontologia.core.domain.paciente.QPaciente;

/**
 *
 * @author Maxi
 */
public class PacienteSpecs {

    private static final QPaciente $ = QPersona.persona.as(QPaciente.class);

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.containsIgnoreCase(nombre);
    }
}
