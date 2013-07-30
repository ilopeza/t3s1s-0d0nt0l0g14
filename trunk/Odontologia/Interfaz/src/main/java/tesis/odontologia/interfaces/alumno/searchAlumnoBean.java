/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;

import com.mysema.query.types.Predicate;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.AlumnoSpecs;

/**
 *
 * @author Mau
 */
@ManagedBean (name = "searchAlumnoBean")
@RequestScoped
public class searchAlumnoBean {

    private Alumno alumno;
    
    private String nroDocumentoFiltro;
    
    //Servicio
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
   
    public searchAlumnoBean() {
    }
    
    @PostConstruct
    private void init(){

    }
    
    
    /**
     * @return the alumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * @return the nroDocumentoFiltro
     */
    public String getNroDocumentoFiltro() {
        return nroDocumentoFiltro;
    }
        
    
    /**
     * @param nroDocumentoFiltro the nroDocumentoFiltro to set
     */
    public void setNroDocumentoFiltro(String nroDocumentoFiltro) {
        this.nroDocumentoFiltro = nroDocumentoFiltro;
    }
    
    public String buscarAlumno() {
        Predicate p = AlumnoSpecs.byNumeroDocumento(nroDocumentoFiltro);
        alumno = (Alumno) getPersonaService().findOne(p);
        
        return "searchAlumno";
    }

    /**
     * @return the personaService
     */
    public PersonaService getPersonaService() {
        return personaService;
    }

    /**
     * @param personaService the personaService to set
     */
    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }
    
    
}
