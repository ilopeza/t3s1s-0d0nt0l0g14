/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.DiagnosticoService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.AlumnoSpecs;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.DiagnosticoSpecs;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.interfaces.Web;
import tesis.odontologia.interfaces.login.LoginBean;
import tesis.odontologia.interfaces.util.Utiles;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "asignacionBean")
@ViewScoped
public class AsignacionBean {

    private AsignacionPaciente asignacion;
    private Date fechaAsignacion;
    //Listas para cargar combos.
    private List<TrabajoPractico> trabajosPracticos;
    private List<Catedra> catedras;
    private List<Materia> materias;
    //Listas para cargar tablas
    private List<AsignacionPaciente> asignaciones;
    private List<Paciente> pacientes;
    private List<Diagnostico> diagnosticos;
    //Atributos búsqueda tabla.
    private String filtroPaciente;
    private Paciente pacienteSeleccionado;
    private ResultadoConsulta diagnosticoSeleccionado;
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private Catedra catedraFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    //Atributos para buscar el alumno.
    private String nroDocumentoAlumnoBuscado;
    private Alumno alumnoBuscado;
    private boolean mostrarBuscarAlumno = true;
    private List<ResultadoConsulta> resultadoBusqueda = new ArrayList<ResultadoConsulta>();
    //Atributos habilitar/deshabilitar
    private boolean habilitarPanel;
    private Web webAux;

    //Servicio
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{catedraService}")
    private CatedraService catedraService;
    @ManagedProperty(value = "#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;
    @ManagedProperty(value = "#{diagnosticoService}")
    private DiagnosticoService diagnosticoService;

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

        if (Web.getLoginBean().getUsuario().getRol().is(Rol.ALUMNO)) {
            mostrarBuscarAlumno = false;
            alumnoBuscado = (Alumno) Web.getLoginBean().getPersona();
            habilitarPanel = true;
            buscarAsignaciones();
        }else{
            habilitarPanel = false;
        }
    }

    public void filtrarCombosPorMateria() {
        if (materiaFiltro != null) {
            materiaFiltro = materiaService.reload(materiaFiltro, 1);
            //buscarCatedras();
            buscarTrabajosPracticos();
        } else {
            //catedras = new ArrayList<Catedra>();
            trabajosPracticos = new ArrayList<TrabajoPractico>();
        }
    }

    public String save() {
        Boolean validacion = false;
        if (true) {
            if (alumnoBuscado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono un alumno.", null));
                validacion = true;
            }
            if (diagnosticoSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono un paciente.", null));
                validacion = true;
            }
            if (catedraFiltro == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono una cátedra", null));
                validacion = true;
            }
            if (fechaAsignacion == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se selecciono una fecha para la asignacion.", null));
                validacion = true;
            }
        }
        if (validacion) {
            return null;
        } else {
            
            if (!permitirAsignacion()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puedes reservar más de 2 pacientes para una práctica.", null));
                return null;
            }

            AsignacionPaciente asig = new AsignacionPaciente();
            Diagnostico diag = diagnosticoService.findOne(diagnosticoSeleccionado.getIdDiagnostico());
            asig.setDiagnostico(diag);
            asig.setFechaCreacionAsignacion(GregorianCalendar.getInstance());
            Calendar fecha = new GregorianCalendar();
            fecha.setTime(fechaAsignacion);
            asig.setFechaAsignacion(fecha);
            asig.setAlumno(alumnoBuscado);
            asig.setPaciente(diagnosticoSeleccionado.getPaciente());
            asig.setCatedra(catedraFiltro);
            diag.setEstado(Diagnostico.EstadoDiagnostico.RESERVADO);


            try {
                asig = asignacionPacienteService.save(asig);
                diag = diagnosticoService.save(diag);
                buscarAsignaciones();
                resultadoBusqueda.remove(diagnosticoSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha registrado la asignación.", null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, "La asignacion no ha sido guardada.", null));
            }

            return null;
        }
    }
    
    public boolean permitirAsignacion() {
        Diagnostico diagnostico = diagnosticoService.findOne(diagnosticoSeleccionado.getIdDiagnostico());
        List<AsignacionPaciente> asigTP = new ArrayList<AsignacionPaciente>();
        Materia materia = diagnostico.getMateria();
        TrabajoPractico practico = diagnostico.getTrabajoPractico();
        
        BooleanExpression predicate = AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE).and(AsignacionPacienteSpecs.byMateria2(materia)).and(AsignacionPacienteSpecs.byTrabajoPractico(practico));
        asigTP = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicate);
        
        if (asigTP.size() < 2) {
            return true;
        }
        return false;
    }

    // Métodos de la interfaz.
    public void buscarPacientes() {

        if (materiaFiltro == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione una materia.", null));
        } else {
            
            buscarCatedras();
            diagnosticos = new ArrayList<Diagnostico>();
            resultadoBusqueda = new ArrayList<ResultadoConsulta>();
            BooleanExpression predicate = DiagnosticoSpecs.byEstado(Diagnostico.EstadoDiagnostico.PENDIENTE);

            if (materiaFiltro != null) {
                predicate = predicate.and(DiagnosticoSpecs.byMateria(materiaFiltro));
            }

            if (trabajoPracticoFiltro != null) {
                predicate = predicate.and(DiagnosticoSpecs.byTrabajoPractico(trabajoPracticoFiltro));
            }

            if (filtroPaciente != null && !filtroPaciente.isEmpty()) {
                if (filtroPaciente.matches("[0-9]*")) {
                    predicate = predicate.and(DiagnosticoSpecs.byNombreODocPaciente(filtroPaciente));
                } else {
                    predicate = predicate.and(DiagnosticoSpecs.byNombreODocPaciente(filtroPaciente));
                }

            }
            diagnosticos = (List<Diagnostico>) diagnosticoService.findAll(predicate);

            for (Diagnostico d : diagnosticos) {
                resultadoBusqueda.add(new ResultadoConsulta(d));
            }
            if (resultadoBusqueda.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron pacientes.", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Enhorabuena, se encontraron " + resultadoBusqueda.size() + " pacientes.", null));
            }
        }

    }

    public void buscarAlumno() {
        alumnoBuscado = new Alumno();
        asignaciones = new ArrayList<AsignacionPaciente>();
        resultadoBusqueda = new ArrayList<ResultadoConsulta>();
        if (nroDocumentoAlumnoBuscado == null || nroDocumentoAlumnoBuscado.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Numero de documento del alumno nulo o vacio.", null));
            habilitarPanel = false;
            return;
        }

        Predicate p = AlumnoSpecs.byNumeroDocumento(nroDocumentoAlumnoBuscado);
        alumnoBuscado = (Alumno) getPersonaService().findOne(p);
        if (alumnoBuscado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro al alumno.", null));
            habilitarPanel = false;
            return;
        }else{
            habilitarPanel = true;
        }
        buscarAsignaciones();
    }

    /**
     * Busca las asignaciones PENDIENTES de un paciente para una materia y TP
     * seleccionados.
     */
    public void buscarAsignaciones() {
        //asignaciones = new ArrayList<AsignacionPaciente>();
        asignaciones = (List<AsignacionPaciente>) asignacionPacienteService.
                findAll(AsignacionPacienteSpecs.byAlumno(alumnoBuscado).
                and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.PENDIENTE)));;

        if (asignaciones == null || asignaciones.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El alumno no posee asignaciones pendientes.", null));
        }
    }

    //MÉTODOS AUXILIARES
    private void cargarCombos() {
        materias = buscarMaterias();
        catedras = buscarCatedras();
        trabajosPracticos = buscarTrabajosPracticos();
    }

    private List<Materia> buscarMaterias() {
        return materiaService.findAll();
    }

    public List<Catedra> buscarCatedras() {
        if (materiaFiltro == null) {
            return null;
        } else {
            materiaFiltro = materiaService.reload(materiaFiltro, 1);
            catedras = materiaFiltro.getCatedra();
            return catedras;
        }

    }

    private List<TrabajoPractico> buscarTrabajosPracticos() {
        if (materiaFiltro == null) {
            return null;
        } else {
            trabajosPracticos = materiaFiltro.getTrabajoPractico();
            return trabajosPracticos;
        }
    }
    
    public void resetCampos(){
        if(!Web.getLoginBean().getUsuario().getRol().is(Rol.ALUMNO)){
            alumnoBuscado = new Alumno();
            asignaciones = new ArrayList<AsignacionPaciente>();
            habilitarPanel = false;
        }else{
            buscarAsignaciones();
            habilitarPanel = true;
        }
        resultadoBusqueda = new ArrayList<ResultadoConsulta>();
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

    /**
     * @return the mostrarBuscarAlumno
     */
    public boolean isMostrarBuscarAlumno() {
        return mostrarBuscarAlumno;
    }

    public String getFechaDesde() {
        SimpleDateFormat fechaMin = new SimpleDateFormat("dd/MM/yyyy");
        return fechaMin.format(GregorianCalendar.getInstance().getTime());
    }

    /**
     * @param mostrarBuscarAlumno the mostrarBuscarAlumno to set
     */
    public void setMostrarBuscarAlumno(boolean mostrarBuscarAlumno) {
        this.mostrarBuscarAlumno = mostrarBuscarAlumno;
    }

    /**
     * @return the resultadoBusqueda
     */
    public List<ResultadoConsulta> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    /**
     * @param resultadoBusqueda the resultadoBusqueda to set
     */
    public void setResultadoBusqueda(List<ResultadoConsulta> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    /**
     * @param diagnosticoService the diagnosticoService to set
     */
    public void setDiagnosticoService(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    /**
     * @return the diagnosticos
     */
    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    /**
     * @param diagnosticos the diagnosticos to set
     */
    public void setDiagnosticos(List<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    /**
     * @return the diagnosticoSeleccionado
     */
    public ResultadoConsulta getDiagnosticoSeleccionado() {
        return diagnosticoSeleccionado;
    }

    /**
     * @param diagnosticoSeleccionado the diagnosticoSeleccionado to set
     */
    public void setDiagnosticoSeleccionado(ResultadoConsulta diagnosticoSeleccionado) {
        this.diagnosticoSeleccionado = diagnosticoSeleccionado;
    }
    
     public boolean isHabilitarPanel() {
        return habilitarPanel;
    }

    public void setHabilitarPanel(boolean habilitarPanel) {
        this.habilitarPanel = habilitarPanel;
    }

    public class ResultadoConsulta {

        private String materia;
        private String practico;
        private String descripcionDiagnostico;
        private Long idDiagnostico;
        private Paciente paciente;

        public ResultadoConsulta(Diagnostico diagnostico) {
            this.materia = diagnostico.getMateria().getNombre();
            this.practico = diagnostico.getTrabajoPractico().getNombre();
            this.descripcionDiagnostico = diagnostico.getDescripcion();
            this.idDiagnostico = diagnostico.getId();
            this.paciente = personaService.findOne(PacienteSpecs.byDiagnostico(diagnostico));

        }

        /**
         * @return the materia
         */
        public String getMateria() {
            return materia;
        }

        /**
         * @param materia the materia to set
         */
        public void setMateria(String materia) {
            this.materia = materia;
        }

        /**
         * @return the practico
         */
        public String getPractico() {
            return practico;
        }

        /**
         * @param practico the practico to set
         */
        public void setPractico(String practico) {
            this.practico = practico;
        }

        /**
         * @return the descripcionDiagnostico
         */
        public String getDescripcionDiagnostico() {
            return descripcionDiagnostico;
        }

        /**
         * @param descripcionDiagnostico the descripcionDiagnostico to set
         */
        public void setDescripcionDiagnostico(String descripcionDiagnostico) {
            this.descripcionDiagnostico = descripcionDiagnostico;
        }

        /**
         * @return the idDiagnostico
         */
        public Long getIdDiagnostico() {
            return idDiagnostico;
        }

        /**
         * @param idDiagnostico the idDiagnostico to set
         */
        public void setIdDiagnostico(Long idDiagnostico) {
            this.idDiagnostico = idDiagnostico;
        }

        /**
         * @return the paciente
         */
        public Paciente getPaciente() {
            return paciente;
        }

        /**
         * @param paciente the paciente to set
         */
        public void setPaciente(Paciente paciente) {
            this.paciente = paciente;
        }
    }
}
