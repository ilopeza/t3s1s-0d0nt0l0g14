/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.historiaclinica.QAtencion;
import tesis.odontologia.core.domain.historiaclinica.QAtencionGenerica;


/**
 *
 * @author Maxi
 */
public class AtencionGenericaSpecs {

    private static final QAtencionGenerica $ = QAtencion.atencion.as(QAtencionGenerica.class);

    public static BooleanExpression byNumeroDocumento(String nroDocumento) {
        return null;
    }

}
