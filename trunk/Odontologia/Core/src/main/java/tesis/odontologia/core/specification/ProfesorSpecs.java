/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.profesor.QProfesor;


/**
 *
 * @author Maxi
 */
public class ProfesorSpecs {

    private static final QProfesor $ = QProfesor.profesor;

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.equalsIgnoreCase(nombre);
    }

    public static BooleanExpression byId(Long id) {
        return $.id.eq(id);
    }
}
