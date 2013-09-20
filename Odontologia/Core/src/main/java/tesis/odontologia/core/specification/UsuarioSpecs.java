/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.usuario.QUsuario;

/**
 *
 * @author Maxi
 */
public class UsuarioSpecs {

    private static final QUsuario $ = QUsuario.usuario.as(QUsuario.class);

    public static BooleanExpression byNombreOrEmail(String nombEmail) {
         return $.nombreUsuario.containsIgnoreCase(nombEmail).or($.email.containsIgnoreCase(nombEmail));
    }
    
    public static BooleanExpression byUsuario(String usuario) {
         return $.nombreUsuario.equalsIgnoreCase(usuario);
    }
    
    public static BooleanExpression byPassword(String password) {
         return $.contraseña.equalsIgnoreCase(password);
    }

}
