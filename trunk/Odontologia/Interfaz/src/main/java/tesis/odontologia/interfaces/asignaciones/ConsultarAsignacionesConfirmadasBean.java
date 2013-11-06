/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.MateriaSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author alespe
 */
@ManagedBean(name = "consultarAsignacionesConfirmadasBean")
@ViewScoped
public class ConsultarAsignacionesConfirmadasBean {
    //Lista para cargar Tablas

    private List<AsignacionPaciente> asignaciones;
    private List<AsignacionPacienteAux> asignacionesConfirmadas;
    private List<AsignacionPacienteAux> selectedAsignacionesAutorizadas;
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private Catedra catedraFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    private AsignacionPaciente.EstadoAsignacion estadoFiltro;
    private Profesor profesor;
    private Date fechaDesdeFiltro;
    private Date fechaHastaFiltro;
    private boolean estaAutorizada;
    private boolean habilitarBotonAutorizar = false;
    //Listas para cargar combos.
    private List<TrabajoPractico> trabajosPracticos;
    private List<Catedra> catedras;
    private List<Materia> materias;
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

    public ConsultarAsignacionesConfirmadasBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");
        profesor = (Profesor) login.getPersona();

        //Se cargan los combos.
        cargarCombos();
        asignaciones = new ArrayList<AsignacionPaciente>();
        asignacionesConfirmadas = new ArrayList<AsignacionPacienteAux>();
    }

    public List<AsignacionPacienteAux> buscarAsignacionesConfirmadas() {

        asignaciones = new ArrayList<AsignacionPaciente>();
        asignacionesConfirmadas = new ArrayList<AsignacionPacienteAux>();
        BooleanExpression predicate = null;
        if (materiaFiltro != null) {
            if (validarFechaDesdeHasta(fechaDesdeFiltro, fechaHastaFiltro) == true) {

                materiaFiltro = materiaService.reload(materiaFiltro, 1);
                predicate = (AsignacionPacienteSpecs.byMateria(materiaFiltro));

                if (catedraFiltro != null) {
                    predicate = predicate.and(AsignacionPacienteSpecs.byCatedra(catedraFiltro));
                }
                if (trabajoPracticoFiltro != null) {
                    predicate = predicate.and(AsignacionPacienteSpecs.byTrabajoPractico(trabajoPracticoFiltro));
                }
                if (fechaDesdeFiltro == null && fechaHastaFiltro == null) {
                } else if (fechaDesdeFiltro != null && fechaHastaFiltro == null) {
                    predicate = predicate.and(AsignacionPacienteSpecs.byFecha(FechaUtils.convertDateToCalendar(fechaDesdeFiltro)));
                } else {
                    predicate = predicate.and(AsignacionPacienteSpecs.byFechaDesdeHasta(FechaUtils.convertDateToCalendar(fechaDesdeFiltro), FechaUtils.convertDateToCalendar(fechaHastaFiltro)));
                }

                if (estaAutorizada == true) {
                    predicate = predicate.and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.AUTORIZADA));
                } else {
                    predicate = predicate.and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.CONFIRMADA));
                }
                asignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicate);
                for (AsignacionPaciente a : asignaciones) {
                    asignacionesConfirmadas.add(new AsignacionPacienteAux(a));
                }

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar una materia", null));
        }

        return asignacionesConfirmadas;

    }

    public void autorizarAsignaciones() {
        AsignacionPaciente.EstadoAsignacion estado = AsignacionPaciente.EstadoAsignacion.AUTORIZADA;
        for (AsignacionPacienteAux aux : selectedAsignacionesAutorizadas) {

            for (AsignacionPaciente a : asignaciones) {
                if (a.getId() == aux.getId()) {
                    a.setEstado(estado);
                    a.setProfesor(profesor);
                    asignacionPacienteService.save(a);
                }
            }
        }
        buscarAsignacionesConfirmadas();
    }

    //MÉTODOS AUXILIARES
    public void habilitarAutorizar() {
        if (estaAutorizada == true) {
            habilitarBotonAutorizar = true;
        } else {
            habilitarBotonAutorizar = false;
        }
    }

    private boolean validarFechaDesdeHasta(Date fechaDesde, Date fechaHasta) {
        if (fechaDesde != null && fechaDesde.compareTo(fechaHasta) <= 0) {
            return true;
        } else if (fechaDesde == null) {
            if (fechaHasta != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe ingresar una Fecha de Atención Desde", null));
                return false;
            } else {
                return true;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "La Fecha de Atención Desde debe ser mayor que la fecha de Atención Hasta", null));
            return false;
        }
    }

    private void cargarCombos() {
        materias = buscarMaterias();
        catedras = buscarCatedras();
        trabajosPracticos = buscarTrabajosPracticos();
    }

    public void filtrarCombosPorMateria() {
        if (materiaFiltro != null) {
            materiaFiltro = materiaService.reload(materiaFiltro, 1);
            buscarCatedras();
            buscarTrabajosPracticos();
        } else {
            catedras = new ArrayList<Catedra>();
            trabajosPracticos = new ArrayList<TrabajoPractico>();
        }
    }

    private List<Materia> buscarMaterias() {
        if (profesor == null) {
            return null;
        } else {
            profesor = personaService.reload(profesor, 1);
            return profesor.getListaMaterias();
        }

    }

    public List<Catedra> buscarCatedras() {

        if (materiaFiltro == null) {
            return null;
        } else {
            catedras = materiaFiltro.getCatedra();
            return catedras;
        }
    }

    public List<TrabajoPractico> buscarTrabajosPracticos() {
        if (materiaFiltro == null) {
            return null;
        } else {
            trabajosPracticos = materiaFiltro.getTrabajoPractico();
            return trabajosPracticos;
        }
    }

    // GETTERS Y SETTERS 
    public boolean isHabilitarBotonAutorizar() {
        return habilitarBotonAutorizar;
    }

    public void setHabilitarBotonAutorizar(boolean habilitarBotonAutorizar) {
        this.habilitarBotonAutorizar = habilitarBotonAutorizar;
    }

    public List<AsignacionPaciente> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionPaciente> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<AsignacionPacienteAux> getSelectedAsignacionesAutorizadas() {
        return selectedAsignacionesAutorizadas;
    }

    public void setSelectedAsignacionesAutorizadas(List<AsignacionPacienteAux> selectedAsignacionesAutorizadas) {
        this.selectedAsignacionesAutorizadas = selectedAsignacionesAutorizadas;
    }

    public boolean isEstaAutorizada() {
        return estaAutorizada;
    }

    public void setEstaAutorizada(boolean estaAutorizada) {
        this.estaAutorizada = estaAutorizada;
    }

    public Date getFechaDesdeFiltro() {
        return fechaDesdeFiltro;
    }

    public void setFechaDesdeFiltro(Date fechaDesdeFiltro) {
        this.fechaDesdeFiltro = fechaDesdeFiltro;
    }

    public Date getFechaHastaFiltro() {
        return fechaHastaFiltro;
    }

    public void setFechaHastaFiltro(Date fechaHastaFiltro) {
        this.fechaHastaFiltro = fechaHastaFiltro;
    }

    public List<AsignacionPacienteAux> getAsignacionesConfirmadas() {
        return asignacionesConfirmadas;
    }

    public void setAsignacionesConfirmadas(List<AsignacionPacienteAux> asignacionesConfirmadas) {
        this.asignacionesConfirmadas = asignacionesConfirmadas;
    }

    public AsignacionPaciente.EstadoAsignacion getEstadoFiltro() {
        return estadoFiltro;
    }

    public void setEstadoFiltro(AsignacionPaciente.EstadoAsignacion estadoFiltro) {
        this.estadoFiltro = estadoFiltro;
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

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public Materia getMateriaFiltro() {
        return materiaFiltro;
    }

    public void setMateriaFiltro(Materia materiaFiltro) {
        this.materiaFiltro = materiaFiltro;
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

    public AsignacionPacienteService getAsignacionPacienteService() {
        return asignacionPacienteService;
    }

    public void setAsignacionPacienteService(AsignacionPacienteService asignacionPacienteService) {
        this.asignacionPacienteService = asignacionPacienteService;
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

    public CatedraService getCatedraService() {
        return catedraService;
    }

    public void setCatedraService(CatedraService catedraService) {
        this.catedraService = catedraService;
    }

    public TrabajoPracticoService getTrabajoPracticoService() {
        return trabajoPracticoService;
    }

    public void setTrabajoPracticoService(TrabajoPracticoService trabajoPracticoService) {
        this.trabajoPracticoService = trabajoPracticoService;
    }
    // Clase Auxiliar

    public class AsignacionPacienteAux {

        private String paciente;
        private String alumno;
        private String fecha;
        private String descripcionDiagnostico;
        private String materia;
        private String catedra;
        private Long id;
        private String trabajoPractico;
        private Materia mat;

        public AsignacionPacienteAux(AsignacionPaciente asignacionPaciente) {
            this.id = asignacionPaciente.getId();
            this.paciente = asignacionPaciente.getPaciente().toString();
            this.alumno = asignacionPaciente.getAlumno().toString();
            this.fecha = FechaUtils.fechaMaskFormat(asignacionPaciente.getFechaAsignacion(), "dd/MM/yyyy HH:mm");
            this.descripcionDiagnostico = asignacionPaciente.getDiagnostico().getDescripcion();
            mat = materiaService.findOne(MateriaSpecs.byCatedra(asignacionPaciente.getCatedra()));
            this.materia = mat.getNombre();
            this.catedra = asignacionPaciente.getCatedra().toString();
            this.trabajoPractico = asignacionPaciente.getDiagnostico().getTrabajoPractico().getNombre();
        }

        // GETTERS Y SETTERS
        public String getMateria() {
            return materia;
        }

        public void setMateria(String materia) {
            this.materia = materia;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDescripcionDiagnostico() {
            return descripcionDiagnostico;
        }

        public void setDescripcionDiagnostico(String descripcionDiagnostico) {
            this.descripcionDiagnostico = descripcionDiagnostico;
        }

        public String getTrabajoPractico() {
            return trabajoPractico;
        }

        public void setTrabajoPractico(String trabajoPractico) {
            this.trabajoPractico = trabajoPractico;
        }

        public String getCatedra() {
            return catedra;
        }

        public void setCatedra(String catedra) {
            this.catedra = catedra;
        }

        public String getPaciente() {
            return paciente;
        }

        public void setPaciente(String paciente) {
            this.paciente = paciente;
        }

        public String getAlumno() {
            return alumno;
        }

        public void setAlumno(String alumno) {
            this.alumno = alumno;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
}
