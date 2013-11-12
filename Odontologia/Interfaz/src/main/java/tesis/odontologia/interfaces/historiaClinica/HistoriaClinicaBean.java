/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.historiaClinica;

import java.util.Calendar;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.historiaclinica.Atencion;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.HistoriaClinicaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.interfaces.util.Utiles;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "historiaClinicaBean")
@ViewScoped
public class HistoriaClinicaBean {

    private Paciente paciente;
    private HistoriaClinica hc;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{historiaClinicaService}")
    private HistoriaClinicaService historiaClinicaService;
    
    //Pagina de atenciones
    private Atencion atencionSeleccionada;

    public HistoriaClinicaBean() {
    }

    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        paciente = (Paciente) sessionMap.get("paciente");
        hc = (HistoriaClinica) sessionMap.get("historiaClinica");
    }

    public void saveHistoriaClinica() {
        hc = historiaClinicaService.save(hc);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Historia clinica actualizada correctamente", null));
    }

    public HistoriaClinicaService getHistoriaClinicaService() {
        return historiaClinicaService;
    }

    public void setHistoriaClinicaService(HistoriaClinicaService historiaClinicaService) {
        this.historiaClinicaService = historiaClinicaService;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public HistoriaClinica getHc() {
        return hc;
    }

    public void setHc(HistoriaClinica hc) {
        this.hc = hc;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public Atencion getAtencionSeleccionada() {
        return atencionSeleccionada;
    }

    public void setAtencionSeleccionada(Atencion atencionSeleccionada) {
        this.atencionSeleccionada = atencionSeleccionada;
    }
    
    public String formatFecha(Calendar c) {
        return Utiles.fechaSinHora(c);
    }
}
