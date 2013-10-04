/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente.EstadoAsignacion;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.AlumnoSpecs;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "consultarAsignacionesBean")
@ViewScoped
public class ConsultarAsignacionesBean {

    //Atributo a manejar.
    private AsignacionPaciente asignacion;
    private Alumno alumno;
    //Atributos para llenar los combos.
    private List<Materia> materias;
    private List<AsignacionPaciente.EstadoAsignacion> estadosAsignacion;
    //Atributos para la búsqueda.
    private Materia materiaFiltro;
    private Catedra catedraFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    private EstadoAsignacion estadoFiltro;
    private String pacienteFiltro;
    private Calendar fechaFiltro;
    private String nroDocumentoFiltro;
    //Atributos para llenar la tabla.
    private List<AsignacionPaciente> asignaciones;
    private AsignacionPaciente asignacionSeleccionada;
    private List<Paciente> pacientes;
    //Sesión
    private Rol rol;
    private boolean rendered = true;
    //Atributos extras.
    private String motivoCancelacion;
    //Servicios usados.
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;

    /**
     * Creates a new instance of ConsultarAsignacionesBean
     */
    public ConsultarAsignacionesBean() {
    }

    // MÉTODOS.
    @PostConstruct
    private void init() {
        if (alumno == null) {
            alumno = new Alumno();
            alumno.setDocumento(new Documento());
        }
        if (asignacion == null) {
            asignacion = new AsignacionPaciente();
        }
        cargarCombos();


        pacientes = new ArrayList<Paciente>();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");

        if (login.getUsuario().getRol().is(Rol.ALUMNO)) {
            rendered = false;
            alumno = (Alumno) login.getPersona();
        }
        if (login.getUsuario().getRol().is(Rol.PROFESOR) || login.getUsuario().getRol().is(Rol.RESPONSABLE)) {
            rendered = true;
        }
    }

    public void cargarAlumno() {
        this.setAlumno(buscarAlumno());
    }

    public void cargarAsignacionesFiltradas() {
        this.setAsignaciones(this.buscarAsignaciones());
    }

    public String formatFecha(Calendar c) {
        return FechaUtils.fechaMaskFormat(c, "dd/MM/yyyy HH:mm");
    }

    /**
     * Método para buscar un alumno sobre el cual se quiere consultar. POR AHORA
     * SOLO SE USA EL NÚM DE DOC PARA BUSCAR
     *
     * @return alumno buscado
     */
    private Alumno buscarAlumno() {
        Alumno alu = new Alumno();
        if (nroDocumentoFiltro == null || nroDocumentoFiltro.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de documento del alumno nulo o vacio.", null));
            return null;
        }

        Predicate p = AlumnoSpecs.byNumeroDocumento(nroDocumentoFiltro);
        try {
            alu = (Alumno) personaService.findOne(p);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro al alumno.", null));
        }
        if (alu == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro al alumno.", null));
            return null;
        }

        return alu;
    }

    /**
     * Busca las asignaciones de un alumno determinado, cumpliendo ciertos
     * filtros.
     *
     * @return lista de asignaciones filtradas.
     */
    private List<AsignacionPaciente> buscarAsignaciones() {
        BooleanExpression predicate = AsignacionPacienteSpecs.byAlumno(alumno);
        if (estadoFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byEstadoAsignacion(estadoFiltro));
        }
        if (pacienteFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byNombreOApellido(pacienteFiltro));
        }
        if (materiaFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byMateria(materiaFiltro));
        }
        List<AsignacionPaciente> listaAsignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicate);


        if (listaAsignaciones.isEmpty()) {

            return null;
        }
        return listaAsignaciones;
    }

    public void buscarAsignacionesConfirmadas() {
        estadoFiltro = EstadoAsignacion.CONFIRMADA;
        /*asignaciones = (List<AsignacionPaciente>) asignacionService.findAll(AsignacionPacienteSpecs.byEstadoAsignacion(estadoFiltro) );*/
        asignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll();

    }

    /**
     * Para cambiar el estado de una asignación seleccionada de la tabla.
     *
     * @param estado al cual se debe cambiar.
     */
    public void cambiarEstadoAsignacionPaciente(AsignacionPaciente.EstadoAsignacion estado) {
        try {
            if (asignacionSeleccionada != null) {
                asignacionSeleccionada.setEstado(estado);
                getAsignacionPacienteService().save(asignacionSeleccionada);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Asignación actualizada correctamente"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La asignacion no fue actualizada correctamente", null));
            System.out.println(ex.getMessage());
        }
    }

    public void confirmarAsignacion() {
        if (motivoCancelacion != null && !motivoCancelacion.isEmpty()) {
            asignacionSeleccionada.setMotivoCancelación(motivoCancelacion);
        }

        cambiarEstadoAsignacionPaciente(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
    }

    public void cancelarAsignacion() {
        cambiarEstadoAsignacionPaciente(AsignacionPaciente.EstadoAsignacion.CANCELADO);
        motivoCancelacion = "";
    }
    
    
    
    public boolean deshabilitarBtnConfirmarAsignacion(AsignacionPaciente a){
        if (a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CANCELADO)==0 || a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CONFIRMADA)==0 ) {
            return true;
        }
        return false;   
    }
    
    public boolean deshabilitarBtnCancelarAsignacion(AsignacionPaciente a){
        if (a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CANCELADO)==0) {
            return true;
        }
        return false;
    }
    
    public boolean deshabilitarBtnModificarAsignacion(AsignacionPaciente a){
        if (a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CANCELADO)==0) {
            return true;
        }
        return false;
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
    public List<EstadoAsignacion> getEstadosAsignacion() {
        return estadosAsignacion;
    }

    public void setEstadosAsignacion(List<EstadoAsignacion> estadosAsignacion) {
        this.estadosAsignacion = estadosAsignacion;
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

    public String getNroDocumentoFiltro() {
        return nroDocumentoFiltro;
    }

    public void setNroDocumentoFiltro(String nroDocumentoFiltro) {
        this.nroDocumentoFiltro = nroDocumentoFiltro;
    }

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

    public String getPacienteFiltro() {
        return pacienteFiltro;
    }

    public void setPacienteFiltro(String pacienteFiltro) {
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

    public AsignacionPacienteService getAsignacionPacienteService() {
        return asignacionPacienteService;
    }

    public void setAsignacionPacienteService(AsignacionPacienteService asignacionService) {
        this.asignacionPacienteService = asignacionService;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
}
