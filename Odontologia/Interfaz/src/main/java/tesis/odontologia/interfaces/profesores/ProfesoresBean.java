/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.profesores;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.ProfesorSpecs;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "profesoresBean")
@ViewScoped
public class ProfesoresBean {

    //Atributos para crear.
    private Profesor profesor;
    private Date fechaNacimiento;

    //Atributos para búsqueda.
    private String filtroBusqueda;
    private Materia materiaBusqueda;
    //Atributos tabla.
    private List<Profesor> profesoresEncontrados;
    private Profesor selectedProfesor;
    //Atributos para llenar combos y listas.
    private List<Materia> materias;
    private List<Documento.TipoDocumento> tiposDocuementos;
    //Atributo para el pickList
    private DualListModel<Materia> materiasElegidas;
    //Atributos habilitar.
    private boolean habilitarNuevoProfesor;
    private boolean habilitarBotonNuevo;
    //Servicios.
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;

    //CONSTRUCTORES
    public ProfesoresBean() {
    }

    //MÉTODOS
    @PostConstruct
    public void init() {
         //Inicialización
        cargarMaterias();
        habilitarNuevoProfesor = false;
        habilitarBotonNuevo = true;
        profesoresEncontrados = new ArrayList<Profesor>();
        
        materiasElegidas = new DualListModel<Materia>();
        if (profesor == null) {
            profesor = new Profesor();
            profesor.setDocumento(new Documento());
            materiasElegidas.setSource(materias);
        }
    }

    private void cargarMaterias() {
        materias = materiaService.findAll();
    }

    /**
     * Método para buscar los pacientes en el panel consultar paciente.
     */
    public void buscarProfesores() {
        boolean aux = false;
        BooleanExpression p = ProfesorSpecs.byClaseProfesor();

        if (filtroBusqueda != null && filtroBusqueda.isEmpty() == false) {
            if(filtroBusqueda.matches("[0-9]*")){
                p = p.and(ProfesorSpecs.byNumeroDocumento(filtroBusqueda));
                aux = true;
            }else{
                p= ProfesorSpecs.byNombreOrApellido(filtroBusqueda);
                aux = true;
            }
        }
        
        if (materiaBusqueda != null) {
            p = p.and(ProfesorSpecs.byMateria(materiaBusqueda));
            aux= true;
        }
        
        profesoresEncontrados = (ArrayList)personaService.findAll(p);
        
        if(profesoresEncontrados == null || profesoresEncontrados.isEmpty()){
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_ERROR, "No se encontraron profesores registrados para los parámetros ingresados.", null));
        }
        
        if (aux == false) {
            FacesContext.getCurrentInstance().
                   addMessage(null, new FacesMessage
                    (FacesMessage.SEVERITY_ERROR, "Debe especificar al menos un parámetro de búsqueda para consultar los profesores registrados.", null));
        }
    }
    

    public void seleccionarProfesor() {
        if (selectedProfesor == null) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione un profesor de la lista.", null));
        } else {
            profesor = selectedProfesor;
            materiasElegidas.setTarget(profesor.getMateria());
            habilitarNuevoProfesor = true;
            habilitarBotonNuevo = true;
        }
    }

    public void saveProfesor() {
        try {
            if (selectedProfesor != null) {
                actualizarProfesor();
            } else {
                nuevoProfesor();
            }
            habilitarNuevoProfesor = false;

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El profesor " + profesor.toString() + " no fue cargado correctamente", null));
            System.out.println(ex.getMessage());
        }finally{
            materiasElegidas.setSource(materias);
            materiasElegidas.setTarget(null);
            profesor = new Profesor();
            profesor.setDocumento(new Documento());
        }
    }

    private void nuevoProfesor() {
        profesor.setMateria(materiasElegidas.getTarget());
        getPersonaService().save(profesor);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profesor " + profesor.toString()
                + " guardado correctamente."));
    }

    private void actualizarProfesor() {
        profesor = personaService.save(profesor);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profesor " + profesor.toString()
                + " actualizado correctamente."));

    }

    public void registrarNuevoProfesor() {
        habilitarNuevoProfesor = true;
        habilitarBotonNuevo = false;
    }

    //GETTERS Y SETTERS.
    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getFiltroBusqueda() {
        return filtroBusqueda;
    }

    public void setFiltroBusqueda(String filtroBusqueda) {
        this.filtroBusqueda = filtroBusqueda;
    }

    public Materia getMateriaBusqueda() {
        return materiaBusqueda;
    }

    public void setMateriaBusqueda(Materia materiaBusqueda) {
        this.materiaBusqueda = materiaBusqueda;
    }

    public List<Profesor> getProfesoresEncontrados() {
        return profesoresEncontrados;
    }

    public void setProfesoresEncontrados(List<Profesor> profesoresEncontrados) {
        this.profesoresEncontrados = profesoresEncontrados;
    }

    public Profesor getSelectedProfesor() {
        return selectedProfesor;
    }

    public void setSelectedProfesor(Profesor selectedProfesor) {
        this.selectedProfesor = selectedProfesor;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Documento.TipoDocumento> getTiposDocuementos() {
        tiposDocuementos = Arrays.asList(Documento.TipoDocumento.values());
        return tiposDocuementos;
    }

    public void setTiposDocuementos(List<Documento.TipoDocumento> tiposDocuementos) {
        this.tiposDocuementos = tiposDocuementos;
    }

    public DualListModel<Materia> getMateriasElegidas() {
        return materiasElegidas;
    }

    public void setMateriasElegidas(DualListModel<Materia> materiasElegidas) {
        this.materiasElegidas = materiasElegidas;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public MateriaService getMateriaService() {
        return materiaService;
    }

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    public boolean isHabilitarNuevoProfesor() {
        return habilitarNuevoProfesor;
    }

    public void setHabilitarNuevoProfesor(boolean habilitarNuevoProfesor) {
        this.habilitarNuevoProfesor = habilitarNuevoProfesor;
    }

    public boolean isHabilitarBotonNuevo() {
        return habilitarBotonNuevo;
    }

    public void setHabilitarBotonNuevo(boolean habilitarBotonNuevo) {
        this.habilitarBotonNuevo = habilitarBotonNuevo;
    }
    
    
    public Date getFechaNacimiento() {
        return profesor.getFechaNacimiento() == null ? null : profesor.getFechaNacimiento().getTime();
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        if (fechaNacimiento == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha de nacimiento no puede ser nula.", null));
            return;
        }
        this.fechaNacimiento = fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        profesor.setFechaNacimiento(cal);
    }
}
