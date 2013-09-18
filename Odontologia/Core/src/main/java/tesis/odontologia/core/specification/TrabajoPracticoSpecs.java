/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.materia.QTrabajoPractico;

/**
 *
 * @author Maxi
 */
public class TrabajoPracticoSpecs {

    private static final QTrabajoPractico $ = QTrabajoPractico.trabajoPractico;

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.equalsIgnoreCase(nombre);
    }

    public static BooleanExpression byId(Long id) {
        return $.id.eq(id);
    }
    
    
}
