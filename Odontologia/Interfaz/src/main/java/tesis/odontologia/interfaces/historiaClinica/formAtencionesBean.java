/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.historiaClinica;

import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Init;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Atencion;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.AtencionService;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.login.LoginBean;
import tesis.odontologia.interfaces.validacion.Validacion;

/**
 *
 * @author Maxi
 */
@ManagedBean
@ViewScoped
public class formAtencionesBean {

    private Alumno alumno;
    private List<AsignacionPaciente> asignacionesAutorizadas;
    private AsignacionPaciente asignacionAutorizada;
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    private Atencion atencion;
    @ManagedProperty(value = "#{atencionService}")
    private AtencionService atencionService;

    public formAtencionesBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");
        alumno = (Alumno) login.getPersona();
        asignacionesAutorizadas = (List<AsignacionPaciente>) asignacionPacienteService.findAll(AsignacionPacienteSpecs.byAlumno(alumno).and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.AUTORIZADA)));
    }

    public void validarAsignacionSeleccionada() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (asignacionAutorizada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No se selecciono una asignacion autorizada para cargar su atencion.", null));
            return;
        }
        atencion = new AtencionGenerica();
        atencion.setAsignacionPaciente(asignacionAutorizada);

        context.execute("dlgRegistrarAtencion.show()");
    }

    public void guardarAsignacion() {
        RequestContext context = RequestContext.getCurrentInstance();

        if(!validar()) {
            return;
        }
        
        atencionService.save(atencion);
        asignacionPacienteService.save(asignacionAutorizada);

        asignacionesAutorizadas = (List<AsignacionPaciente>) asignacionPacienteService.findAll(AsignacionPacienteSpecs.byAlumno(alumno)
                .and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.AUTORIZADA)));
        context.execute("dlgRegistrarAtencion.hide()");
    }

    public void cancelarRegistro() {
        atencion = null;
        asignacionAutorizada = null;
    }

    private boolean validar() {
        boolean varValidacion = true;

        if (Validacion.nullEmpty(atencion.getDescripcionProcedimiento())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La descripcion del procedimiento no puede ser nula o vacia.", null));
            varValidacion = false;
        }

        if (Validacion.nullEmpty(atencion.getMotivoConsultaOdontologica())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El motivo de la consulta no puede ser nulo o vacio.", null));
            varValidacion = false;
        }

        if (Validacion.nullObject(atencion.getFechaAtencion())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha de atencion no puede ser nula", null));
            varValidacion = false;
        }
        return varValidacion;
    }

    public String formatFecha(Calendar c) {
        return FechaUtils.fechaConSeparador(c);
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public List<AsignacionPaciente> getAsignacionesAutorizadas() {
        return asignacionesAutorizadas;
    }

    public void setAsignacionesAutorizadas(List<AsignacionPaciente> asignacionesAutorizadas) {
        this.asignacionesAutorizadas = asignacionesAutorizadas;
    }

    public AsignacionPacienteService getAsignacionPacienteService() {
        return asignacionPacienteService;
    }

    public void setAsignacionPacienteService(AsignacionPacienteService asignacionPacienteService) {
        this.asignacionPacienteService = asignacionPacienteService;
    }

    public AsignacionPaciente getAsignacionAutorizada() {
        return asignacionAutorizada;
    }

    public void setAsignacionAutorizada(AsignacionPaciente asignacionAutorizada) {
        this.asignacionAutorizada = asignacionAutorizada;
    }

    public Atencion getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencion atencion) {
        this.atencion = atencion;
    }

    public AtencionService getAtencionService() {
        return atencionService;
    }

    public void setAtencionService(AtencionService atencionService) {
        this.atencionService = atencionService;
    }
}
