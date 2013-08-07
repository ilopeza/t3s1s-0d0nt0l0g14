/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.specification;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tesis.odontologia.core.domain.QPersona;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.paciente.QPaciente;

/**
 *
 * @author Maxi
 */
public class PacienteSpecs {

    private static final QPaciente $ = QPersona.persona.as(QPaciente.class);

    public static BooleanExpression byNombre(String nombre) {
        return $.nombre.containsIgnoreCase(nombre);
    }
    
    public static BooleanExpression byNombreOApellido(String nombApp) {
        return $.nombre.containsIgnoreCase(nombApp).or($.apellido.containsIgnoreCase(nombApp));
    }

    public static BooleanExpression byNumeroDocumento(String nroDocumento) {
        return $.documento.numero.equalsIgnoreCase(nroDocumento);
    }
    

    public static BooleanExpression byMenorA(Calendar edadHasta) {
        return $.fechaNacimiento.after(edadHasta);
    }
    
    public static BooleanExpression byMayorA(Calendar edadDesde){
        return $.fechaNacimiento.before(edadDesde);
    }
    
    public static BooleanExpression byEdadEntre(Calendar desde, Calendar hasta){
        return $.fechaNacimiento.between(hasta, hasta);
    }
      
    public static BooleanExpression byDiagnosticoMateria(Materia materia){
        List<Diagnostico.EstadoDiagnostico> estados = new ArrayList<Diagnostico.EstadoDiagnostico>();
        estados.add(Diagnostico.EstadoDiagnostico.EN_PROCESO);
        estados.add(Diagnostico.EstadoDiagnostico.PENDIENTE);
        
        return $.historiaClinica.diagnostico.any().estado.in(estados)
                .and($.historiaClinica.diagnostico.any().materia.eq(materia));
    }
    
    public static BooleanExpression byTipoPersona(Object t){
        return $.instanceOf(Paciente.class); 
    }
            

}
