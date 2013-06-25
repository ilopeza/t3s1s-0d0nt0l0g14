/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;


import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;



/**
 *
 * @author alespe
 */
@ManagedBean(name = "formAlumnoBean")
@SessionScoped

public class AlumnoBean {

    private Alumno alumno;
    private List<Documento.TipoDocumento> listaTipoDocumento;

    public AlumnoBean() {
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno Alumno) {
        this.alumno = Alumno;
    }

    public List<Documento.TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(Documento.TipoDocumento.values());
        return listaTipoDocumento;
    }

    public String navigate() {
        return "showAlumno";
    }

    @PostConstruct
    public void init() {
        
        if(alumno == null){
            alumno = new Alumno();
            alumno.setDocumento(new Documento());
        }

    }
    
    
    public void savePaciente() {
        //AlumnoService.create(nuevoAlumno);
    }

    public void updatePaciente() {
        //AlumnoService.update(nuevoAlumno);
    }

    public void deletePaciente() {
        //AlumnoService.delete(nuevoAlumno);
    }

    public Alumno searchAlumno() {
        Alumno alu = new Alumno();
        //AlumnoService.searchAlumno(alu);
        return alu;
    }
}
