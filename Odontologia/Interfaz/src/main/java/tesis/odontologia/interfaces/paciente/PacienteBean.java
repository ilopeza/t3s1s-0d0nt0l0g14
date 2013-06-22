/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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
@ViewScoped
public class PacienteBean {

    // Paciente que va a ser manejado.
    private Paciente paciente;
    //Lista de TipoDocumento para poblar los select
    private List<TipoDocumento> listaTipoDocumento;
    //Lista de EstadoCivil
    private List<EstadoCivilTipo> listaEstadoCivilTipo;
    //Lita de EstudiosTipo
    private List<EstudiosTipo> listaEstudioTipo;
    private String fechaNacimiento;

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
        return "/pages/pacientes/prueba.xhtml";
    }

    public void updatePaciente() {
        //PacienteService.update(nuevoPaciente);
    }

    public void deletePaciente() {
        //PacienteService.delete(nuevoPaciente);
    }

    public Paciente searchPaciente() {
        Paciente pa = new Paciente();
        //PacienteService.searchPaciente(pa);
        return pa;
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

    public String getFecha() {
        return "02051990";
    }
//    private long idPaciente;
//    private String nombre;
//    private String apellido;
//    private long numDoc;
//    private EstadoCivilTipo listaEstadoCivil;
//    private String religion;
//    private String nacionalidad;
//    private String obraSocial;
//    private String lugarNac;
//    private String provincia;
//    private Date fechaNacimiento;
//    private boolean estudia;
//    private EstudiosTipo listaEstudioTipo;
//    private String trabajo;
//    private String domicilioActual;
//    private String ciudadActual;
//    private String residenciaAnt;
//    private String ciudadAnt;
//    private long telefono;
//    private long celular;
//    private String medicoCabecera;
//    private long telMedicoCabecera;
//    private boolean tieneServEmergencia;
//    private String servEmergencia;
//    private boolean estaPrivadoLibertad;
//    private String lugar;
//    private EstudiosTipo selectedEstudioTipo;
//    private EstadoCivilTipo selectedEstadoCivilTipo;
//    
//    public long getIdPaciente() {
//        return idPaciente;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public long getNumDoc() {
//        return numDoc;
//    }
//
//    public void setNumDoc(long numDoc) {
//        this.numDoc = numDoc;
//    }
//
//    public EstadoCivilTipo getListaEstadoCivil() {
//        return listaEstadoCivil;
//    }
//
//    public void setListaEstadoCivil(EstadoCivilTipo estadoCivil) {
//        this.listaEstadoCivil = estadoCivil;
//    }
//
//    public String getReligion() {
//        return religion;
//    }
//
//    public void setReligion(String religion) {
//        this.religion = religion;
//    }
//
//    public String getNacionalidad() {
//        return nacionalidad;
//    }
//
//    public void setNacionalidad(String nacionalidad) {
//        this.nacionalidad = nacionalidad;
//    }
//
//    public String getObraSocial() {
//        return obraSocial;
//    }
//
//    public void setObraSocial(String obraSocial) {
//        this.obraSocial = obraSocial;
//    }
//
//    public String getLugarNac() {
//        return lugarNac;
//    }
//
//    public void setLugarNac(String lugarNac) {
//        this.lugarNac = lugarNac;
//    }
//
//    public String getProvincia() {
//        return provincia;
//    }
//
//    public void setProvincia(String provincia) {
//        this.provincia = provincia;
//    }
//
//    public Date getFechaNacimiento() {
//        return fechaNacimiento;
//    }
//
//    public void setFechaNacimiento(Date fechaNacimiento) {
//        this.fechaNacimiento = fechaNacimiento;
//    }
//
//    public boolean isEstudia() {
//        return estudia;
//    }
//
//    public void setEstudia(boolean estudia) {
//        this.estudia = estudia;
//    }
//
//    public String getTrabajo() {
//        return trabajo;
//    }
//
//    public void setTrabajo(String trabajo) {
//        this.trabajo = trabajo;
//    }
//
//    public String getDomicilioActual() {
//        return domicilioActual;
//    }
//
//    public void setDomicilioActual(String domicilioActual) {
//        this.domicilioActual = domicilioActual;
//    }
//
//    public String getCiudadActual() {
//        return ciudadActual;
//    }
//
//    public void setCiudadActual(String ciudadActual) {
//        this.ciudadActual = ciudadActual;
//    }
//
//    public String getResidenciaAnt() {
//        return residenciaAnt;
//    }
//
//    public void setResidenciaAnt(String residenciaAnt) {
//        this.residenciaAnt = residenciaAnt;
//    }
//
//    public String getCiudadAnt() {
//        return ciudadAnt;
//    }
//
//    public void setCiudadAnt(String ciudadAnt) {
//        this.ciudadAnt = ciudadAnt;
//    }
//
//    public long getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(long telefono) {
//        this.telefono = telefono;
//    }
//
//    public long getCelular() {
//        return celular;
//    }
//
//    public void setCelular(long celular) {
//        this.celular = celular;
//    }
//
//    public String getMedicoCabecera() {
//        return medicoCabecera;
//    }
//
//    public void setMedicoCabecera(String medicoCabecera) {
//        this.medicoCabecera = medicoCabecera;
//    }
//
//    public long getTelMedicoCabecera() {
//        return telMedicoCabecera;
//    }
//
//    public void setTelMedicoCabecera(long telMedicoCabecera) {
//        this.telMedicoCabecera = telMedicoCabecera;
//    }
//
//    public boolean isTieneServEmergencia() {
//        return tieneServEmergencia;
//    }
//
//    public void setTieneServEmergencia(boolean tieneServEmergencia) {
//        this.tieneServEmergencia = tieneServEmergencia;
//    }
//
//    public String getServEmergencia() {
//        return servEmergencia;
//    }
//
//    public void setServEmergencia(String servEmergencia) {
//        this.servEmergencia = servEmergencia;
//    }
//
//    public boolean isEstaPrivadoLibertad() {
//        return estaPrivadoLibertad;
//    }
//
//    public void setEstaPrivadoLibertad(boolean estaPrivadoLibertad) {
//        this.estaPrivadoLibertad = estaPrivadoLibertad;
//    }
//
//    public String getLugar() {
//        return lugar;
//    }
//
//    public void setLugar(String lugar) {
//        this.lugar = lugar;
//    }     
//    
//    
//    public EstudiosTipo getListaEstudioTipo() {
//        return listaEstudioTipo;
//    }
//
//    public void setEstudioTipo(EstudiosTipo estudioTipo) {
//        this.listaEstudioTipo = estudioTipo;
//    }
}
