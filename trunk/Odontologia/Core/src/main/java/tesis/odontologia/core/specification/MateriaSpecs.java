/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.materia.QMateria;
import tesis.odontologia.core.domain.usuario.QRol;

/**
 *
 * @author Maxi
 */
public class MateriaSpecs {

    private static final QMateria $ = QMateria.materia.as(QMateria.class);

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.equalsIgnoreCase(nombre);
    }

    public static BooleanExpression byId(Long id) {
        return $.id.eq(id);
    }
}
