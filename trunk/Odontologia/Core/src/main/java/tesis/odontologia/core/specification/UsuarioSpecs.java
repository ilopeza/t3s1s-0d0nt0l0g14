/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.domain.usuario.QUsuario;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;

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
    
    public static BooleanExpression byEmail(String email) {
        return $.email.equalsIgnoreCase(email);
    }
    
    public static BooleanExpression byRol(Rol rol) {
        return $.rol.eq(rol);
    }
    
    public static BooleanExpression byClaseUsuario(){
        return $.instanceOf(Usuario.class);
    }

}
