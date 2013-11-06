/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;

import tesis.odontologia.core.domain.QPersona;
import tesis.odontologia.core.domain.alumno.QAlumno;

/**
 *
 * @author Maxi
 */
public class AlumnoSpecs {

    private static final QAlumno $ = QPersona.persona.as(QAlumno.class);

    public static BooleanExpression byNumeroDocumento(String nroDocumento) {
        return $.documento.numero.equalsIgnoreCase(nroDocumento);
    }

}
