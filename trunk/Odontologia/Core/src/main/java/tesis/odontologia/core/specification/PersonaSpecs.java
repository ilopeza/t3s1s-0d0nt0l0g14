/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.QPersona;
import tesis.odontologia.core.domain.usuario.Usuario;

/**
 *
 * @author Ignacio
 */
public class PersonaSpecs {
    
    private static final QPersona $ = QPersona.persona.as(QPersona.class);
    
    public static BooleanExpression byClass(Class clazz){
        return $.instanceOf(clazz);
    }

    public static BooleanExpression byUsuario(Usuario us){
        return $.usuario.id.eq(us.getId());
    }
    
}
