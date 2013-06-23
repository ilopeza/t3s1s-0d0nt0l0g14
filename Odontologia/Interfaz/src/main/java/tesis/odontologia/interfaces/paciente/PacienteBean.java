/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.Documento.TipoDocumento;
import tesis.odontologia.core.domain.paciente.Domicilio;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.paciente.Paciente.EstadoCivilTipo;
import tesis.odontologia.core.domain.paciente.Paciente.EstudiosTipo;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "PacienteBean")
@ApplicationScoped
public class PacienteBean {

    // Paciente que va a ser manejado.
    private Paciente paciente;
    //Lista de TipoDocumento para poblar los select
    private List<TipoDocumento> listaTipoDocumento;
    //Lista de EstadoCivil
    private List<EstadoCivilTipo> listaEstadoCivilTipo;
    //Lita de EstudiosTipo
    private List<EstudiosTipo> listaEstudioTipo;

    public List<EstadoCivilTipo> getListaEstadoCivilTipo() {
        listaEstadoCivilTipo = Arrays.asList(EstadoCivilTipo.values());
        return listaEstadoCivilTipo;
    }

    public void setListaEstadoCivilTipo(List<EstadoCivilTipo> listaEstadoCivilTipo) {
        this.listaEstadoCivilTipo = listaEstadoCivilTipo;
    }

    public List<EstudiosTipo> getListaEstudioTipo() {
        listaEstudioTipo = Arrays.asList(EstudiosTipo.values());
        return listaEstudioTipo;
    }

    public void setListaEstudioTipo(List<EstudiosTipo> listaEstudioTipo) {
        this.listaEstudioTipo = listaEstudioTipo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente p) {
        this.paciente = p;
    }

    /**
     * Creates a new instance of PacienteBean
     */
    public PacienteBean() {
    }

    public String savePaciente() {
        //PacienteService.create(nuevoPaciente);
        return "show";
    }

    public void updatePaciente() {
        //PacienteService.update(nuevoPaciente);
    }

    public void deletePaciente() {
        //PacienteService.delete(nuevoPaciente);
    }


    public List<TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(TipoDocumento.values());
        return listaTipoDocumento;
    }

    @PostConstruct
    public void init() {
        paciente = new Paciente("Javier", "López");
        paciente.setDocumento(new Documento());
        paciente.setDomicilio(new Domicilio());
    }
}
