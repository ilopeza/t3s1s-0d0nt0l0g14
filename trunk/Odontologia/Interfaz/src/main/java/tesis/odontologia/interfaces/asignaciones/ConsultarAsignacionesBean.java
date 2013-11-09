/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import java.text.SimpleDateFormat;
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
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente.EstadoAsignacion;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.mail.SMTPConfig;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.DiagnosticoService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.AlumnoSpecs;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.DiagnosticoSpecs;
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
    private Date fechaFiltro;
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
    private boolean campoEditable;
    //Servicios usados.
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{diagnosticoService}")
    private DiagnosticoService diagnosticoService;

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
            alumno.setNombre("");
            alumno.setApellido("");
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
        asignaciones = buscarAsignaciones();
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno " + alu.getApellido() + ", " + alu.getNombre() + " encontrado.", null));
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
        BooleanExpression predicate = AsignacionPacienteSpecs.byAlumno(alumno).and(AsignacionPacienteSpecs.byNoMostrarCanceladas());
        if (estadoFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byEstadoAsignacion(estadoFiltro));
        }
        if (pacienteFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byNombreOApellido(pacienteFiltro));
        }
        if (materiaFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byMateria2(materiaFiltro));
        }
        if (fechaFiltro != null) {
            predicate = predicate.and(AsignacionPacienteSpecs.byDate(fechaFiltro));
        }
        List<AsignacionPaciente> listaAsignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicate);


        if (listaAsignaciones.isEmpty()) {

            return null;
        }
        return listaAsignaciones;
    }

    /**
     * Para cambiar el estado de una asignación seleccionada de la tabla.
     *
     * @param estado al cual se debe cambiar.
     */
    public void cambiarEstadoAsignacionPaciente(AsignacionPaciente.EstadoAsignacion estado) {
        try {
            if (asignacionSeleccionada != null) {
                if (asignacionSeleccionada.getEstado() == EstadoAsignacion.PENDIENTE && estado == EstadoAsignacion.CANCELADA) {
                    asignacionSeleccionada.setEstado(estado);
                    asignacionSeleccionada = asignacionPacienteService.save(asignacionSeleccionada);
                    asignaciones = buscarAsignaciones();
                } else {
                    asignacionSeleccionada.setEstado(estado);
                    asignacionSeleccionada = asignacionPacienteService.save(asignacionSeleccionada);
                    asignaciones = buscarAsignaciones();
                    notificarPaciente(asignacionSeleccionada);
                }
                if (asignacionSeleccionada.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CANCELADA) == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La asignación ha sido cancelada correctamente."));
                }
                if (asignacionSeleccionada.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CONFIRMADA) == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La asignación ha sido confirmada correctamente."));
                }
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La asignacion no fue actualizada correctamente", null));
            System.out.println(ex.getMessage());
        }
    }

    public void confirmarAsignacion() {
        cambiarEstadoAsignacionPaciente(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
    }

    private void notificarPaciente(AsignacionPaciente asignacion) {
        String textoEmail;

        if (asignacion.getEstado() == EstadoAsignacion.CONFIRMADA) {
            if (asignacion.getPaciente().getEmail() != null && !asignacion.getPaciente().getEmail().isEmpty()) {
                textoEmail = "Estimado " + asignacion.getPaciente().getNombre() + ":\n" + System.getProperty("line.separator");
                textoEmail += "   Se ha confirmado tu asistencia a una práctica odontológica en la facultad de Odontología de la UNC. A continuación te mostramos algunos datos que te serán de utilidad:\n";
                textoEmail += "" + System.getProperty("line.separator");
                textoEmail += "Cátedra donde se te atenderá: " + asignacion.getDiagnostico().getMateria().getNombre() + " " + asignacion.getCatedra().getNombre() + "\n";
                textoEmail += "Práctica a desarrollar: " + asignacion.getDiagnostico().getTrabajoPractico().getNombre() + "\n";
                textoEmail += "Alumno que te atenderá: " + asignacion.getAlumno().getApellido() + ", " + asignacion.getAlumno().getNombre() + "\n";
                textoEmail += "Fecha y hora: " + formatFecha(asignacion.getFechaAsignacion()) + "\n";
                textoEmail += " " + System.getProperty("line.separator");
                textoEmail += "Saludos.";
                try {
                    SMTPConfig.sendMail(true, "Confirmación de asistencia a práctica odontológica", textoEmail, asignacion.getPaciente().getEmail());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se ha notificado correctamente al paciente."));
                } catch (MessagingException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No se ha podido notificar al paciente"));
                }
            }

        } else if (asignacion.getEstado() == EstadoAsignacion.CANCELADA) {
            if (asignacion.getPaciente().getEmail() != null && !asignacion.getPaciente().getEmail().isEmpty()) {
                textoEmail = "Estimado " + asignacion.getPaciente().getNombre() + ":\n" + System.getProperty("line.separator");
                textoEmail += "   El alumno " + asignacion.getAlumno().getApellido() + ", " + asignacion.getAlumno().getNombre() + "perteneciente a la Facultad de Odontología de la UNC ha cancelado una práctica para la que estabas asignado.\n";
                textoEmail += "" + System.getProperty("line.separator");
                textoEmail += " El motivo de la cancelación es el siguiente: " + asignacion.getMotivoCancelacion() + "\n";
                textoEmail += "Saludos.";
                try {
                    SMTPConfig.sendMail(true, "Cancelación de práctica odontológica", textoEmail, asignacion.getPaciente().getEmail());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se ha notificado correctamente al paciente."));
                } catch (MessagingException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No se ha podido notificar al paciente"));
                }
            }
        }
    }

    public void notificarPacientePorCambioFecha(AsignacionPaciente asignacion, String fechaAnterior, String fechaNueva) {
        String textoEmail;
        if (asignacion.getPaciente().getEmail() != null && !asignacion.getPaciente().getEmail().isEmpty()) {
            textoEmail = "Estimado " + asignacion.getPaciente().getNombre() + ":\n" + System.getProperty("line.separator");
            textoEmail += " Le informamos que un alumno de la facultad de odontología de la UNC ha cambiado la fecha/hora de una práctica para la cual ud. se encuentra asignado. A continuación se detalla el cambio.\n";
            textoEmail += " Fecha/Hora anterior: " + fechaAnterior;
            textoEmail += " Fecha/Hora nueva: " + fechaNueva + "\n";
            textoEmail += "Saludos.";
            try {
                SMTPConfig.sendMail(true, "Cambio de Fecha/Hora práctica odontológica", textoEmail, asignacion.getPaciente().getEmail());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Se ha notificado correctamente al paciente."));
            } catch (MessagingException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No se ha podido notificar al paciente."));
            }
        }
    }

    public void cancelarAsignacion() {
        asignacionSeleccionada.setMotivoCancelacion(motivoCancelacion);
        cambiarEstadoAsignacionPaciente(AsignacionPaciente.EstadoAsignacion.CANCELADA);
        motivoCancelacion = "";
        Diagnostico d = diagnosticoService.findOne(asignacionSeleccionada.getDiagnostico().getId());
        d.setEstado(Diagnostico.EstadoDiagnostico.PENDIENTE);
        diagnosticoService.save(d);
        
    }

    public boolean deshabilitarBtnConfirmarAsignacion(AsignacionPaciente a) {

        if (a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.PENDIENTE) == 0) {
            return false;
        }
        return true;
    }

    public boolean deshabilitarBtnCancelarAsignacion(AsignacionPaciente a) {

        if (a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.PENDIENTE) == 0 || a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CONFIRMADA) == 0) {
            return false;
        }
        return true;
    }

    public boolean deshabilitarBtnModificarAsignacion(AsignacionPaciente a) {
        if (a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.PENDIENTE) == 0 || a.getEstado().compareTo(AsignacionPaciente.EstadoAsignacion.CONFIRMADA) == 0) {
            return false;
        }
        return true;
    }

    public void modificar() {
        if (this.asignacionSeleccionada != null) {
            campoEditable = true;
            rendered = false;
        }
    }

    public String getFechaDesde(AsignacionPaciente asignacion) {
        SimpleDateFormat fechaMin = new SimpleDateFormat("dd/MM/yyyy");
        return fechaMin.format(asignacion.getFechaAsignacion().getTime());
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage();
        AsignacionPaciente asignacionAnterior = asignacionPacienteService.findOne(asignacionSeleccionada.getId());
        if (asignacionAnterior.getFechaAsignacion().equals(asignacionSeleccionada.getFechaAsignacion())) {
            msg.setSummary("Asignación sin cambios.");
            msg.setDetail("No has realizado modifaciones.");
        } else {
            try {
                String fechaAnterior = formatFecha(asignacionAnterior.getFechaAsignacion());
                String fechaNueva = formatFecha(asignacionSeleccionada.getFechaAsignacion());
                asignacionPacienteService.save(asignacionSeleccionada);
                msg.setSummary("Cambios guardados con éxito");
                msg.setDetail("Fecha/Hora anteriores: " + fechaAnterior + "Fecha/Hora nuevos: " + fechaNueva);
                notificarPacientePorCambioFecha(asignacionSeleccionada, fechaAnterior, fechaNueva);
            } catch (Exception e) {
                msg.setSummary("No se pudo guardar los cambios.");
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modificación cancelada");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    

    public void confirmarModificacion(AsignacionPaciente a) {
        if (this.asignacionSeleccionada != null) {
            asignacionSeleccionada = a;
            this.asignacionPacienteService.save(asignacionSeleccionada);
            this.campoEditable = false;
            rendered = true;
        }
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

    public Date getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Date fechaFiltro) {
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

    public boolean isCampoEditable() {
        return campoEditable;
    }

    public void setCampoEditable(boolean campoEditable) {
        this.campoEditable = campoEditable;
    }

    /**
     * @param diagnosticoService the diagnosticoService to set
     */
    public void setDiagnosticoService(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }
}
