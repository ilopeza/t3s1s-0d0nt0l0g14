/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.materia.QCatedra;

/**
 *
 * @author Maxi
 */
public class CatedraSpecs {

    private static final QCatedra $ = QCatedra.catedra;

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.equalsIgnoreCase(nombre);
    }

    public static BooleanExpression byId(Long id) {
        return $.id.eq(id);
    }
}
