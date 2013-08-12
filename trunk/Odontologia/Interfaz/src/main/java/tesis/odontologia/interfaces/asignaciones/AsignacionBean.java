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
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
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
    private List<Paciente> pacientes;
    private Materia materia;
    private List<Materia> materias;
    private List<AsignacionPaciente> asignaciones;
    private Date fechaAsignacion;
    //Atributos búsqueda tabla.
    private String filtroPaciente;
    private Paciente pacienteSeleccionado;
    private List<Paciente> pacientesSeleccionados;
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private String edadDesdeFiltro;
    private String edadHastaFiltro;
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

    /**
     * Creates a new instance of AsignacionBean
     */
    public AsignacionBean() {
    }

    @PostConstruct
    public void init() {
        materias = materiaService.findAll();
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
        pacientes = (List<Paciente>) personaService.findAll(PacienteSpecs.byNombreOApellido(filtroPaciente).and(PersonaSpecs.byClass(Paciente.class)));
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

    public void buscarAsignaciones() {
        asignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll(AsignacionPacienteSpecs.byAlumno(alumnoBuscado).and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE)));
        if (asignaciones == null || asignaciones.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El alumno no posee asignaciones pendientes.", null));
        }
    }

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

    public String getEdadDesdeFiltro() {
        return edadDesdeFiltro;
    }

    public void setEdadDesdeFiltro(String edadDesdeFiltro) {
        this.edadDesdeFiltro = edadDesdeFiltro;
    }

    public String getEdadHastaFiltro() {
        return edadHastaFiltro;
    }

    public void setEdadHastaFiltro(String edadHastaFiltro) {
        this.edadHastaFiltro = edadHastaFiltro;
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
