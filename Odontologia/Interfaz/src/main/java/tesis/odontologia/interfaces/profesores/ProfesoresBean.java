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
    private Profesor.EstadoProfesor estadoBusqueda;
    //Atributos tabla.
    private List<Profesor> profesoresEncontrados;
    private Profesor selectedProfesor;
    //Atributos para llenar combos y listas.
    private List<Materia> materias;
    private List<Documento.TipoDocumento> tiposDocuementos;
    private List<Profesor.EstadoProfesor> estadosProfesor;
    //Atributo para el pickList
    private DualListModel<Materia> materiasElegidas;
    //Atributos habilitar.
    private boolean habilitarNuevoProfesor;
    private boolean habilitarBotonNuevo;
    private boolean habilitarCambioEstado;

    public boolean isHabilitarCambioEstado() {
        return habilitarCambioEstado;
    }

    public void setHabilitarCambioEstado(boolean habilitarCambioEstado) {
        this.habilitarCambioEstado = habilitarCambioEstado;
    }
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
        Profesor profAux = new Profesor();
        ArrayList<Profesor> profesoresAgregadosAux = new ArrayList<Profesor>(); 

        if (filtroBusqueda != null && filtroBusqueda.isEmpty() == false) {
            p = buscarPorNombreOrNumDoc(p);
            aux = true;
        }

        if (materiaBusqueda != null) {
            p = p.and(ProfesorSpecs.byMateria(materiaBusqueda));
            aux = true;
        }

        if (estadoBusqueda != null) {
            p = buscarPorEstado(p);
            aux = true;
        }

        ArrayList<Profesor> profesoresRecorridosAux = (ArrayList) personaService.findAll(p);
        if (profesoresRecorridosAux != null && !profesoresRecorridosAux.isEmpty()) {
            for (Profesor prof : profesoresRecorridosAux) {
                //profesoresRecorridosAux.remove(prof);
                profAux = (Profesor) personaService.reload(prof, 1);
                profesoresAgregadosAux.add(profAux);
            }
            profesoresEncontrados = profesoresAgregadosAux;
        }

        mostrarMensajesBusqueda(aux);
    }

    public void seleccionarProfesor() {
        if (selectedProfesor == null) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione un profesor de la lista.", null));
        } else {
            profesor = selectedProfesor;
            profesor = personaService.reload(profesor, 1);
            materiasElegidas.setTarget(profesor.getListaMaterias());
            materiasElegidas.setSource(this.getSourceMaterias());
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
        } finally {
            materiasElegidas.setSource(materias);
            materiasElegidas.setTarget(null);
            profesor = new Profesor();
            profesor.setDocumento(new Documento());
        }
    }

    private BooleanExpression buscarPorNombreOrNumDoc(BooleanExpression p) {
        if (filtroBusqueda.matches("[0-9]*")) {
            p = p.and(ProfesorSpecs.byNumeroDocumento(filtroBusqueda));

        } else {
            p = ProfesorSpecs.byNombreOrApellido(filtroBusqueda);

        }
        return p;
    }

    private BooleanExpression buscarPorEstado(BooleanExpression p) {
        p = p.and(ProfesorSpecs.byEstadoProfesor(estadoBusqueda));
        if (estadoBusqueda.equals(Profesor.EstadoProfesor.ACTIVO)) {
            habilitarCambioEstado = false;
        } else {
            habilitarCambioEstado = true;
        }
        return p;
    }

    private void mostrarMensajesBusqueda(boolean aux) {
        if (aux == false) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe especificar al menos un parámetro de búsqueda para consultar los profesores registrados.", null));
        }
    }

    private void nuevoProfesor() {
        profesor.setEstado(Profesor.EstadoProfesor.ACTIVO);
        profesor.setListaMaterias(materiasElegidas.getTarget());
        profesor = personaService.save(profesor);
        buscarProfesores();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profesor " + profesor.toString()
                + " guardado correctamente."));
    }

    private void actualizarProfesor() {
        profesor.setListaMaterias(materiasElegidas.getTarget());
        profesor = personaService.save(profesor);
        buscarProfesores();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profesor " + profesor.toString()
                + " actualizado correctamente."));
    }

    public void darBaja() {
        this.cambiarEstado(Profesor.EstadoProfesor.DADO_DE_BAJA);

    }

    public void darAlta() {
        this.cambiarEstado(Profesor.EstadoProfesor.ACTIVO);
    }

    private void cambiarEstado(Profesor.EstadoProfesor nuevoEstado) {
        selectedProfesor.setEstado(nuevoEstado);
        profesor = selectedProfesor;
        saveProfesor();
        buscarProfesores();
    }

    public void registrarNuevoProfesor() {
        habilitarNuevoProfesor = true;
        habilitarBotonNuevo = false;
    }

    private List<Materia> getSourceMaterias() {
        List<Materia> listaAuxMat = new ArrayList<Materia>();
        listaAuxMat.addAll(materias);
        for (Materia m : materiasElegidas.getTarget()) {
            listaAuxMat.remove(m);
        }
        return listaAuxMat;
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

    public Profesor.EstadoProfesor getEstadoBusqueda() {
        return estadoBusqueda;
    }

    public void setEstadoBusqueda(Profesor.EstadoProfesor estadoBusqueda) {
        this.estadoBusqueda = estadoBusqueda;
    }

    public List<Profesor.EstadoProfesor> getEstadosProfesor() {
        estadosProfesor = Arrays.asList(Profesor.EstadoProfesor.values());
        return estadosProfesor;
    }

    public void setEstadosProfesor(List<Profesor.EstadoProfesor> estadosProfesor) {
        this.estadosProfesor = estadosProfesor;
    }
}
