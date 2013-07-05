/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.paciente.Domicilio;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.PersonaService;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "formPacienteBean")
@ViewScoped
public class FormPacienteBean {

    private Paciente paciente;
    private List<Documento.TipoDocumento> listaTipoDocumento;
    //Lista de EstadoCivil
    private List<Paciente.EstadoCivilTipo> listaEstadoCivilTipo;
    //Lista de EstudiosTipo
    private List<Paciente.EstudiosTipo> listaEstudioTipo;
    //Lista de pacientes.
    private List<Paciente> pacientes;
    private String docBusqueda;
    //Fecha de nacimiento del paciente
    private Date fechaNacimiento;
    // Para conocer si estudia o no.
    private int estudia;
    // Para saber si tiene servicio de emergencia.
    private int servicio;
 
    
    public int getEstudia() {
        if(paciente != null && paciente.getTipoEstudios() != null){
            estudia = 1;
        }else{
            estudia = 0;
        }
        return estudia;
    }

    public void setEstudia(int estudia) {
        this.estudia = estudia;
        if(estudia == 0){
            paciente.setTipoEstudios(null);
        }
    }

    public int getServicio() {
        if(paciente != null && paciente.getServicioEmergencia()!=null){
            servicio = 1;
        }else{
            servicio = 0;
        }
        return servicio;
    }

    public void setServicio(int servicio) {
        
        this.servicio = servicio;
    }
        
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    
    
    public Date getFechaNacimiento() {
        return paciente.getFechaNacimiento() == null? null: paciente.getFechaNacimiento().getTime();
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        
        this.fechaNacimiento = fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        paciente.setFechaNacimiento(cal);
    }
    
    public String getDocBusqueda() {
        return docBusqueda;
    }

    public void setDocBusqueda(String docBusqueda) {
        this.docBusqueda = docBusqueda;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPaciente(Paciente p) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        p.setFechaNacimiento(cal);
        
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

    public String save() {
        paciente.toString();
        paciente = personaService.save(paciente);
        return "showPaciente";
    }

    @PostConstruct
    public void init() {
        
        if(paciente == null){
            paciente = new Paciente();
            paciente.setDomicilio(new Domicilio());
            paciente.setDocumento(new Documento());
            
            

          
        }
            
        pacientes = new ArrayList<Paciente>();
        pacientes.addAll(construirLista());
    }

    private List<Paciente> construirLista() {
        List<Paciente> lista = new ArrayList<Paciente>();
        Paciente p1 = new Paciente();
        p1.setDocumento(new Documento());
        p1.getDocumento().setNumero("123");
        p1.setDomicilio(new Domicilio());
        p1.setApellido("Jojo");

        Paciente p2 = new Paciente();
        p2.setDocumento(new Documento());
        p2.getDocumento().setNumero("456");
        p2.setDomicilio(new Domicilio());
        p2.setApellido("Jeje");

        Paciente p3 = new Paciente();
        p3.setDocumento(new Documento());
        p3.getDocumento().setNumero("789");
        p3.setDomicilio(new Domicilio());
        p3.setApellido("Jiji");
        
        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        return lista;
    }

    public String buscarPaciente() {
        for (Paciente p : pacientes) {
            if (p.getDocumento().getNumero().equals(docBusqueda)==true) {
                paciente = p;
                break;
            }
        }
        return "formPaciente";
    }

    /**
     * Creates a new instance of FormPacienteBean
     */
    public FormPacienteBean() {
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }
}
