/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import tesis.odontologia.core.domain.alumno.Alumno;

/**
 *
 * @author alespe
 */

@ManagedBean
@SessionScoped

public class AlumnoBean {
    private Alumno nuevoAlumno= new Alumno();
    
    public AlumnoBean() {
        
    }
        public void savePaciente(){
        //AlumnoService.create(nuevoAlumno);
    }
    
    public void updatePaciente(){
        //AlumnoService.update(nuevoAlumno);
    }
    
    public void deletePaciente(){
        //AlumnoService.delete(nuevoAlumno);
    }
    
    public Alumno searchAlumno(){
        Alumno alu = new Alumno();
        //AlumnoService.searchAlumno(alu);
        return alu;
    }
    
}
