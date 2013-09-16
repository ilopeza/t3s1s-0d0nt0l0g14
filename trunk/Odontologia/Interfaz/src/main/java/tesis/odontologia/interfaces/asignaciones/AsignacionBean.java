/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.AlumnoSpecs;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.interfaces.util.Utiles;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "asignacionBean")
@ViewScoped
public class AsignacionBean {

    private AsignacionPaciente asignacion;
   
    private Materia materia;

    private Date fechaAsignacion;
    //Listas para cargar combos.
    private List<TrabajoPractico> trabajosPracticos;
    private List<Catedra> catedras;
    private List<Materia> materias;
    //Listas para cargar tablas
    private List<AsignacionPaciente> asignaciones;
    private List<Paciente> pacientes;
    //Atributos búsqueda tabla.
    private String filtroPaciente;
    private Paciente pacienteSeleccionado;
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private Catedra catedraFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    //Atributos para buscar el alumno.
    private String nroDocumentoAlumnoBuscado;
    private Alumno alumnoBuscado;
    //Servicio
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{catedraService}")
    private CatedraService catedraService;
    @ManagedProperty(value="#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;

   

    /**
     * Creates a new instance of AsignacionBean
     */
    public AsignacionBean() {
    }

    @PostConstruct
    public void init() {
        //Se cargan los combos.
        cargarCombos();
        pacientes = new ArrayList<Paciente>();
    }

    public String save() {
        if (alumnoBuscado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono un alumno.", null));
            return null;
        }
        if (pacienteSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono un paciente.", null));
            return null;
        }
        if (materia == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono una materia.", null));
            return null;
        }
        if (fechaAsignacion == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono una fecha para la asignacion.", null));
            return null;
        }

        asignacion = new AsignacionPaciente();
        asignacion.setAlumno(alumnoBuscado);
        asignacion.setPaciente(pacienteSeleccionado);
        Calendar fecha = new GregorianCalendar();
        fecha.setTime(fechaAsignacion);
        asignacion.setFechaAsignacion(fecha);
        asignacion.setMateria(materia);

        try {
            asignacion = asignacionPacienteService.save(asignacion);
            asignaciones.add(asignacion);
            if (asignacion != null && !asignacion.isNew()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignacion guardada", null));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "La asignacion no ha sido guardada", null));
        }

        return "asignacionPaciente";
    }

    // Métodos de la interfaz.
    public void buscarPacientes() {
//        if (filtroPaciente == null || filtroPaciente.isEmpty()) {
//            if ((edadDesdeFiltro == null || edadDesdeFiltro.isEmpty()) && (edadHastaFiltro.isEmpty() || edadHastaFiltro == null)) {
//                buscarTodosLosPacientes();
//            } else {
//                busquedaAvanzada();
//            }
//        } else {
        busquedaSimple();
//        }
    }

    //Métodos auxiliares.
    private void busquedaSimple() {
        pacientes.clear();
        if (filtroPaciente == null || filtroPaciente.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El filtro de busqueda de paciente no puede estar vacio.", null));
            return;
        }
        pacientes = (List<Paciente>) personaService
                .findAll(PacienteSpecs.byNombreOApellido(filtroPaciente).
                and(PersonaSpecs.byClass(Paciente.class)));
        if (pacientes == null || pacientes.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontraron pacientes.", null));
        }
    }

    public void buscarAlumno() {
        if (nroDocumentoAlumnoBuscado == null || nroDocumentoAlumnoBuscado.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de documento del alumno nulo o vacio.", null));
            return;
        }

        Predicate p = AlumnoSpecs.byNumeroDocumento(nroDocumentoAlumnoBuscado);
        alumnoBuscado = (Alumno) getPersonaService().findOne(p);
        if (alumnoBuscado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro al alumno.", null));
            return;
        }
        buscarAsignaciones();
    }
    /**
     * Busca las asignaciones PENDIENTES de un paciente para una materia y TP seleccionados.
     */
    public void buscarAsignaciones() {
        
        asignaciones = (List<AsignacionPaciente>) 
                asignacionPacienteService.findAll(AsignacionPacienteSpecs.byAlumno(alumnoBuscado).
                and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE)).
                and(AsignacionPacienteSpecs.byMateria(materiaFiltro)).
                and(AsignacionPacienteSpecs.byTrabajoPractico(trabajoPracticoFiltro)));
        
        if (asignaciones == null || asignaciones.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El alumno no posee asignaciones pendientes.", null));
        }
    }

    //MÉTODOS AUXILIARES
    
    private void cargarCombos(){
        materias = buscarMaterias();
        catedras = buscarCatedras();
        trabajosPracticos = buscarTrabajosPracticos();
    } 
    private  List<Materia> buscarMaterias(){
        return materiaService.findAll();
    }
    
    private List<Catedra> buscarCatedras(){
        return catedraService.findAll();
    }
    
    private List<TrabajoPractico> buscarTrabajosPracticos(){
        return trabajoPracticoService.findAll();
    }
    
    
    // GETTERS Y SETTERS
    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public String fechaFormateada(Calendar fecha) {
        return Utiles.fechaConHora(fecha);
    }

    public AsignacionPaciente getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionPaciente asignacion) {
        this.asignacion = asignacion;
    }

    public String getFiltroPaciente() {
        return filtroPaciente;
    }

    public void setFiltroPaciente(String filtroPaciente) {
        this.filtroPaciente = filtroPaciente;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public AsignacionPacienteService getAsignacionPacienteService() {
        return asignacionPacienteService;
    }

    public void setAsignacionPacienteService(AsignacionPacienteService asignacionPacienteService) {
        this.asignacionPacienteService = asignacionPacienteService;
    }

    public List<AsignacionPaciente> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionPaciente> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public Materia getMateriaFiltro() {
        return materiaFiltro;
    }

    public void setMateriaFiltro(Materia materiaFiltro) {
        this.materiaFiltro = materiaFiltro;
    }

    public String getNroDocumentoAlumnoBuscado() {
        return nroDocumentoAlumnoBuscado;
    }

    public void setNroDocumentoAlumnoBuscado(String nroDocumentoAlumnoBuscado) {
        this.nroDocumentoAlumnoBuscado = nroDocumentoAlumnoBuscado;
    }

    public Alumno getAlumnoBuscado() {
        return alumnoBuscado;
    }

    public void setAlumnoBuscado(Alumno alumnoBuscado) {
        this.alumnoBuscado = alumnoBuscado;
    }

    public MateriaService getMateriaService() {
        return materiaService;
    }

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    public CatedraService getCatedraService() {
        return catedraService;
    }

    public void setCatedraService(CatedraService catedraService) {
        this.catedraService = catedraService;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
    
    
    public List<TrabajoPractico> getTrabajosPracticos() {
        return trabajosPracticos;
    }

    public void setTrabajosPracticos(List<TrabajoPractico> trabajosPracticos) {
        this.trabajosPracticos = trabajosPracticos;
    }

    public List<Catedra> getCatedras() {
        return catedras;
    }

    public void setCatedras(List<Catedra> catedras) {
        this.catedras = catedras;
    }

    public Catedra getCatedraFiltro() {
        return catedraFiltro;
    }

    public void setCatedraFiltro(Catedra catedraFiltro) {
        this.catedraFiltro = catedraFiltro;
    }

    public TrabajoPractico getTrabajoPracticoFiltro() {
        return trabajoPracticoFiltro;
    }

    public void setTrabajoPracticoFiltro(TrabajoPractico trabajoPracticoFiltro) {
        this.trabajoPracticoFiltro = trabajoPracticoFiltro;
    }
    
     public TrabajoPracticoService getTrabajoPracticoService() {
        return trabajoPracticoService;
    }

    public void setTrabajoPracticoService(TrabajoPracticoService trabajoPracticoService) {
        this.trabajoPracticoService = trabajoPracticoService;
    }
//      RESPALDO DE CODIGO DE FILTROS
//    private void busquedaAvanzada() {
//        pacientes.clear();
//        Predicate p = null;
//
//        if (edadDesdeFiltro != null && edadDesdeFiltro.length() > 0) {
//            // Busca pacientes que tengan como máximo cierta edad.
//            p = PacienteSpecs.byMayorA(convertirFechaDesde());
//        }
//        if (edadHastaFiltro != null && edadHastaFiltro.length() > 0) {
//            p = PacienteSpecs.byMenorA(convertirFechaHasta()).and(p);
//        }
//        pacientes.addAll((Collection<? extends Paciente>) personaService.findAll(p));
//    }
//
//    private void buscarTodosLosPacientes() {
//        Predicate p = PersonaSpecs.byClass(Paciente.class);
//        pacientes = (List<Paciente>) personaService.findAll(p);
//    }
//
//    private Calendar convertirFechaDesde() {
//        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
//        int anioDesde = anioActual - Utiles.convertStringToInt(edadDesdeFiltro).intValue();
//
//        return Utiles.convertIntegerToCalendarYear(anioDesde);
//    }
//
//    private Calendar convertirFechaHasta() {
//        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
//        int anioHasta = anioActual - Utiles.convertStringToInt(edadHastaFiltro).intValue();
//
//        return Utiles.convertIntegerToCalendarYear(anioHasta);
//    }
}
