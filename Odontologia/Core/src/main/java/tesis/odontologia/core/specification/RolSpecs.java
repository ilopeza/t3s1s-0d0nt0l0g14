/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.usuario.QRol;

/**
 *
 * @author Maxi
 */
public class RolSpecs {

    private static final QRol $ = QRol.rol;

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.containsIgnoreCase(nombre);
    }
    
     public static BooleanExpression byId(Long id) {
        return $.id.eq(id);
    }
}
