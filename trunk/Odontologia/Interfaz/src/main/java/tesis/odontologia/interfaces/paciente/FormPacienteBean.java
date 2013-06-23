/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.paciente.Domicilio;
import tesis.odontologia.core.domain.paciente.Paciente;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "formPacienteBean")
@ApplicationScoped
public class FormPacienteBean {

    private Paciente paciente;
    private List<Documento.TipoDocumento> listaTipoDocumento;
    //Lista de EstadoCivil
    private List<Paciente.EstadoCivilTipo> listaEstadoCivilTipo;
    //Lita de EstudiosTipo
    private List<Paciente.EstudiosTipo> listaEstudioTipo;

    public void setPaciente(Paciente p) {
        this.paciente = p;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public List<Documento.TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(Documento.TipoDocumento.values());
        return listaTipoDocumento;
    }

    public List<Paciente.EstadoCivilTipo> getListaEstadoCivilTipo() {
        listaEstadoCivilTipo = Arrays.asList(Paciente.EstadoCivilTipo.values());
        return listaEstadoCivilTipo;
    }

    public List<Paciente.EstudiosTipo> getListaEstudioTipo() {
        listaEstudioTipo = Arrays.asList(Paciente.EstudiosTipo.values());
        return listaEstudioTipo;
    }
    
    public String navigate(){
        return "showPaciente";
    }
    
    @PostConstruct
    public void init(){
        paciente = new Paciente("Javier", "López");
        paciente.setDocumento(new Documento());
        paciente.setDomicilio(new Domicilio());
    }
    
    /**
     * Creates a new instance of FormPacienteBean
     */
    public FormPacienteBean() {
    }
}
