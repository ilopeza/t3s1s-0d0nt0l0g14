/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.Predicate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente.EstadoAsignacion;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.AlumnoSpecs;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "consultarAsignacionesBean")
@RequestScoped
public class ConsultarAsignacionesBean {

    //Atributo a manejar.
    private AsignacionPaciente asignacion;
    private Alumno alumno;
    //Atributos para llenar los combos.
    private List<Materia> materias;
    private List<AsignacionPaciente.EstadoAsignacion> estadosAsignacion;
    //Atributos para la búsqueda.
    private Materia materiaFiltro;
    private EstadoAsignacion estadoFiltro;
    private Paciente pacienteFiltro;
    private Calendar fechaFiltro;
    private String nroDocumentoFiltro;
    //Atributos para llenar la tabla.
    private List<AsignacionPaciente> asignaciones;
    private AsignacionPaciente asignacionSeleccionada;
    //Servicios usados.
    @ManagedProperty(value = "#{asignacionService}")
    private AsignacionPacienteService asignacionService;
    @ManagedProperty(value = "#{alumnoService}")
    private PersonaService alumnoService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;

    // MÉTODOS.
    @PostConstruct
    private void init() {
        cargarCombos();
    }

    public void cargarAlumno(){
        this.setAlumno(buscarAlumno());
    }
    
    public void cargarAsignacionesFiltradas(){
        this.setAsignaciones(this.buscarAsignaciones());
    }
    /**
     * Método para buscar un alumno sobre el cual se quiere consultar. 
     * POR AHORA SOLO SE USA EL NÚM DE DOC PARA BUSCAR
     * @return alumno buscado
     */
    private Alumno buscarAlumno() {
        if (nroDocumentoFiltro == null || nroDocumentoFiltro.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de documento del alumno nulo o vacio.", null));
            return null;
        }

        Predicate p = AlumnoSpecs.byNumeroDocumento(nroDocumentoFiltro);
        Alumno alu = (Alumno) alumnoService.findOne(p);
        if (alu == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro al alumno.", null));
            return null;
        }

        return alu;
    }

    /**
     * Busca las asignaciones de un alumno determinado, cumpliendo ciertos filtros.
     * @return lista de asignaciones filtradas.
     */
    private List<AsignacionPaciente> buscarAsignaciones() {

        List<AsignacionPaciente> listaAsignaciones = (List<AsignacionPaciente>) asignacionService.findAll(AsignacionPacienteSpecs.byAlumno(alumno)//.
                //and(AsignacionPacienteSpecs.byMateria(materiaFiltro).
                //and(AsignacionPacienteSpecs.byPaciente(pacienteFiltro)))
                );
        
        if (listaAsignaciones.isEmpty()) {
 
            return null;
        }
        return listaAsignaciones;
    }

    //MÉTODOS AUXILIARES
    private void cargarCombos() {
        this.setMaterias(buscarMaterias());
        this.setEstados(buscarEstadosDeAsignacion());
    }

    private List<Materia> buscarMaterias() {
        return materiaService.findAll();
    }

    private List<EstadoAsignacion> buscarEstadosDeAsignacion() {
        return Arrays.asList(AsignacionPaciente.EstadoAsignacion.values());
    }

    //GETTERS Y SETTERS
    public AsignacionPaciente getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionPaciente asignacion) {
        this.asignacion = asignacion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<AsignacionPaciente.EstadoAsignacion> getEstados() {
        return estadosAsignacion;
    }

    public void setEstados(List<AsignacionPaciente.EstadoAsignacion> estados) {
        this.estadosAsignacion = estados;
    }

    public Materia getMateriaFiltro() {
        return materiaFiltro;
    }

    public void setMateriaFiltro(Materia materiaFiltro) {
        this.materiaFiltro = materiaFiltro;
    }

    public EstadoAsignacion getEstadoFiltro() {
        return estadoFiltro;
    }

    public void setEstadoFiltro(EstadoAsignacion estadoFiltro) {
        this.estadoFiltro = estadoFiltro;
    }

    public Paciente getPacienteFiltro() {
        return pacienteFiltro;
    }

    public void setPacienteFiltro(Paciente pacienteFiltro) {
        this.pacienteFiltro = pacienteFiltro;
    }

    public Calendar getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Calendar fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

    public List<AsignacionPaciente> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionPaciente> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public AsignacionPaciente getAsignacionSeleccionada() {
        return asignacionSeleccionada;
    }

    public void setAsignacionSeleccionada(AsignacionPaciente asignacionSeleccionada) {
        this.asignacionSeleccionada = asignacionSeleccionada;
    }

    public AsignacionPacienteService getAsignacionService() {
        return asignacionService;
    }

    public void setAsignacionService(AsignacionPacienteService asignacionService) {
        this.asignacionService = asignacionService;
    }

    public PersonaService getAlumnoService() {
        return alumnoService;
    }

    public void setAlumnoService(PersonaService alumnoService) {
        this.alumnoService = alumnoService;
    }

    public MateriaService getMateriaService() {
        return materiaService;
    }

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    /**
     * Creates a new instance of ConsultarAsignacionesBean
     */
    public ConsultarAsignacionesBean() {
    }
}
