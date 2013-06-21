/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.paciente.Paciente.EstadoCivilTipo;
import tesis.odontologia.core.domain.paciente.Paciente.EstudiosTipo;


/**
 *
 * @author Ignacio
 */
@ManagedBean
@SessionScoped
public class PacienteBean {

   
    private Paciente nuevoPaciente = new Paciente();
    private long idPaciente;
    private String nombre;
    private String apellido;
    private long numDoc;
    private EstadoCivilTipo estadoCivil;
    private String religion;
    private int edad;
    private String nacionalidad;
    private String obraSocial;
    private String lugarNac;
    private String provincia;
    private Date fechaNacimiento;
    private boolean estudia;
    private EstudiosTipo estudioTipo;
    private String trabajo;
    private String domicilioActual;
    private String ciudadActual;
    private String residenciaAnt;
    private String ciudadAnt;
    private long telefono;
    private long celular;
    private String medicoCabecera;
    private long telMedicoCabecera;
    private boolean tieneServEmergencia;
    private String servEmergencia;
    private boolean estaPrivadoLibertad;
    private String lugar;
    
    public long getIdPaciente() {
        return idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(long numDoc) {
        this.numDoc = numDoc;
    }

    public EstadoCivilTipo getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilTipo estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getLugarNac() {
        return lugarNac;
    }

    public void setLugarNac(String lugarNac) {
        this.lugarNac = lugarNac;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isEstudia() {
        return estudia;
    }

    public void setEstudia(boolean estudia) {
        this.estudia = estudia;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getDomicilioActual() {
        return domicilioActual;
    }

    public void setDomicilioActual(String domicilioActual) {
        this.domicilioActual = domicilioActual;
    }

    public String getCiudadActual() {
        return ciudadActual;
    }

    public void setCiudadActual(String ciudadActual) {
        this.ciudadActual = ciudadActual;
    }

    public String getResidenciaAnt() {
        return residenciaAnt;
    }

    public void setResidenciaAnt(String residenciaAnt) {
        this.residenciaAnt = residenciaAnt;
    }

    public String getCiudadAnt() {
        return ciudadAnt;
    }

    public void setCiudadAnt(String ciudadAnt) {
        this.ciudadAnt = ciudadAnt;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public String getMedicoCabecera() {
        return medicoCabecera;
    }

    public void setMedicoCabecera(String medicoCabecera) {
        this.medicoCabecera = medicoCabecera;
    }

    public long getTelMedicoCabecera() {
        return telMedicoCabecera;
    }

    public void setTelMedicoCabecera(long telMedicoCabecera) {
        this.telMedicoCabecera = telMedicoCabecera;
    }

    public boolean isTieneServEmergencia() {
        return tieneServEmergencia;
    }

    public void setTieneServEmergencia(boolean tieneServEmergencia) {
        this.tieneServEmergencia = tieneServEmergencia;
    }

    public String getServEmergencia() {
        return servEmergencia;
    }

    public void setServEmergencia(String servEmergencia) {
        this.servEmergencia = servEmergencia;
    }

    public boolean isEstaPrivadoLibertad() {
        return estaPrivadoLibertad;
    }

    public void setEstaPrivadoLibertad(boolean estaPrivadoLibertad) {
        this.estaPrivadoLibertad = estaPrivadoLibertad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }     
    
    
    public EstudiosTipo getEstudioTipo() {
        return estudioTipo;
    }

    public void setEstudioTipo(EstudiosTipo estudioTipo) {
        this.estudioTipo = estudioTipo;
    }
    
    /**
     * Creates a new instance of PacienteBean
     */
    public PacienteBean() {
        
    }
    
    public void savePaciente(){
        //PacienteService.create(nuevoPaciente);
    }
    
    public void updatePaciente(){
        //PacienteService.update(nuevoPaciente);
    }
    
    public void deletePaciente(){
        //PacienteService.delete(nuevoPaciente);
    }
    
    public Paciente searchPaciente(){
        Paciente pa = new Paciente();
        //PacienteService.searchPaciente(pa);
        return pa;
    }
    
    
}
