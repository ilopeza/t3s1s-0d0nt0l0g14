/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.historiaClinica;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Init;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Atencion;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.AtencionService;
import tesis.odontologia.core.service.DiagnosticoService;
import tesis.odontologia.core.service.HistoriaClinicaService;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.HistoriaClinicaSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.Web;
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
    private Date fechaAtencion;
    private Diagnostico diagnostico;
    private String estadoDiagnostico;
    @ManagedProperty(value = "#{historiaClinicaService}")
    private HistoriaClinicaService historiaClinicaService;
    private HistoriaClinica hc;

    public formAtencionesBean() {
    }

    @PostConstruct
    public void init() {
        alumno = (Alumno) Web.getLoginBean().getPersona();
        asignacionesAutorizadas = (List<AsignacionPaciente>) asignacionPacienteService.findAll(AsignacionPacienteSpecs.byAlumno(alumno).and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.AUTORIZADA)));
        estadoDiagnostico = "pendiente";
    }

    public void validarAsignacionSeleccionada() {
        if (asignacionAutorizada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No se selecciono una asignacion autorizada para cargar su atencion.", null));
            return;
        }

        Web.callDialog("dlgRegistrarAtencion.show()");
        Thread t = new Thread() {
            @Override
            public void run() {
                atencion = new AtencionGenerica();
                atencion.setAsignacionPaciente(asignacionAutorizada);
                fechaAtencion = asignacionAutorizada.getFechaAsignacion().getTime();
                diagnostico = asignacionAutorizada.getDiagnostico();
                hc = historiaClinicaService.findOne(HistoriaClinicaSpecs.byPaciente(asignacionAutorizada.getPaciente()));
                hc = historiaClinicaService.reload(hc, 1);
            }
        };
        t.start();
    }

    public void guardarAsignacion() throws IOException {
        if (!validar()) {
            return;
        }
        try {
            //actualizo el estado de la asignacion a registrada
            asignacionAutorizada.setEstado(AsignacionPaciente.EstadoAsignacion.REGISTRADA);

            //actualizo el estado del diagnostico si el mismo ya no es pendiente
            if (estadoDiagnostico.equalsIgnoreCase("solucionado")) {
                diagnostico.setAsignacion(asignacionAutorizada);
                diagnostico.setEstado(Diagnostico.EstadoDiagnostico.SOLUCIONADO_EN_FACULTAD);
            }
            if (estadoDiagnostico.equalsIgnoreCase("nosolucionado")) {
                diagnostico.setAsignacion(asignacionAutorizada);
                diagnostico.setEstado(Diagnostico.EstadoDiagnostico.NO_SOLUCIONADO);
            }
            //agrego la atencion y el diagnostico a la historia clinica
            hc.addAtencion(atencion);
            hc.updateDiagnostico(diagnostico);

            //guardo los cambios en la asignacion y la historia clinica
            asignacionAutorizada = asignacionPacienteService.save(asignacionAutorizada);
            historiaClinicaService.save(hc);

            Web.callDialog("dlgRegistrarAtencion.hide()");
            //necesario recargar la lista, para actualizar la lista de asignaciones y volver el estado 
            //del diagnostico a pendiente (por defecto)
            asignacionesAutorizadas = (List<AsignacionPaciente>) asignacionPacienteService.findAll(AsignacionPacienteSpecs.byAlumno(alumno).and(AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.AUTORIZADA)));
            estadoDiagnostico = "pendiente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "La atencion se registro correctamente.", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Error al guardar." + ex.getMessage(), null));
        }
    }

    private boolean validar() {
        boolean varValidacion = true;

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

    public Date getFechaAtencion() {
        if (atencion == null) {
            return null;
        }
        return atencion.getFechaAtencion() != null ? atencion.getFechaAtencion().getTime() : null;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
        if (atencion != null) {
            if (fechaAtencion == null) {
                atencion.setFechaAtencion(null);
            } else {
                Calendar c = new GregorianCalendar();
                c.setTime(fechaAtencion);
                atencion.setFechaAtencion(c);
            }
        }
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getEstadoDiagnostico() {
        return estadoDiagnostico;
    }

    public void setEstadoDiagnostico(String estadoDiagnostico) {
        this.estadoDiagnostico = estadoDiagnostico;
    }

    public HistoriaClinicaService getHistoriaClinicaService() {
        return historiaClinicaService;
    }

    public void setHistoriaClinicaService(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    public HistoriaClinica getHc() {
        return hc;
    }

    public void setHc(HistoriaClinica hc) {
        this.hc = hc;
    }
}
