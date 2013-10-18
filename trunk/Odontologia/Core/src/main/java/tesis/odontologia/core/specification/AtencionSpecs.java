/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import tesis.odontologia.core.domain.QPersona;
import tesis.odontologia.core.domain.historiaclinica.QAtencion;
import tesis.odontologia.core.domain.usuario.Usuario;

/**
 *
 * @author Ignacio
 */
public class AtencionSpecs {

    private static final QAtencion $ = QAtencion.atencion.as(QAtencion.class);

    public static BooleanExpression byClass(Class clazz) {
        return $.instanceOf(clazz);
    }
    }
