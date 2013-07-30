/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import com.mysema.query.types.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.Documento.TipoDocumento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.AlumnoSpecs;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.interfaces.util.Utiles;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "searchPacienteBean")
@ViewScoped
public class SearchPacienteBean {
    
    //Atributos comunes
    private Paciente paciente;
    private List<Paciente> pacientes;
    private List<Materia> materias;
    
    //Atributos búsqueda tabla.
    private Paciente pacienteSeleccionado;
    
    //Atributos búsqueda simple.
    private String nroDocumentoFiltro;
    
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private String edadDesdeFiltro;
    private String edadHastaFiltro;
    
    //Atributos para buscar el alumno.
    private String nroDocumentoAlumnoBuscado;
    private Alumno alumnoBuscado;

    //Servicio
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    
    /**
     * Creates a new instance of SearchPacienteBean
     */
    public SearchPacienteBean() {
    }

    // Getters y setters.
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
     public Alumno getAlumnoBuscado() {
        return alumnoBuscado;
    }

    public void setAlumnoBuscado(Alumno alumno) {
        this.alumnoBuscado = alumno;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public String getNroDocumentoFiltro() {
        return nroDocumentoFiltro;
    }

    public void setNroDocumentoFiltro(String nroDocumentoFiltro) {
        this.nroDocumentoFiltro = nroDocumentoFiltro;
    }

    public Materia getMateriaFiltro() {
        return materiaFiltro;
    }

    public void setMateriaFiltro(Materia materiaFiltro) {
        this.materiaFiltro = materiaFiltro;
    }

    public String getEdadDesdeFiltro() {
        return edadDesdeFiltro;
    }

    public void setEdadDesdeFiltro(String edadDesdeFiltro) {
        this.edadDesdeFiltro = edadDesdeFiltro;
    }

    public String getEdadHastaFiltro() {
        return edadHastaFiltro;
    }

    public void setEdadHastaFiltro(String edadHastaFiltro) {
        this.edadHastaFiltro = edadHastaFiltro;
    }

    public String getNroDocumentoAlumnoBuscado() {
        return nroDocumentoAlumnoBuscado;
    }

    public void setNroDocumentoAlumnoBuscado(String nroDocumentoAlumno) {
        this.nroDocumentoAlumnoBuscado = nroDocumentoAlumno;
    }
    
    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }
    
    @PostConstruct
    private void init(){
        pacientes = new ArrayList<Paciente>();
    }
    
    // Métodos de la interfaz.
    public void buscarPacientes(){
        if(nroDocumentoFiltro == null || nroDocumentoFiltro.isEmpty()){
            if((edadDesdeFiltro == null || edadDesdeFiltro.isEmpty()) && (edadHastaFiltro.isEmpty() || edadHastaFiltro == null)){
                buscarTodosLosPacientes();
            }else{
                busquedaAvanzada();
            }
        }else{
            busquedaSimple();
        }    
    }
  
    //Métodos auxiliares.
    private void busquedaSimple() {
        pacientes.clear();
        Predicate p = PacienteSpecs.byNumeroDocumento(nroDocumentoFiltro);
        Paciente p1 = (Paciente) personaService.findOne(p);
        pacientes.add(p1);
    }

    private void busquedaAvanzada() {
        pacientes.clear();
        Predicate p = null;

        if (edadDesdeFiltro != null && edadDesdeFiltro.length() > 0) {
            // Busca pacientes que tengan como máximo cierta edad.
            p = PacienteSpecs.byMayorA(convertirFechaDesde());  
        }
        if(edadHastaFiltro != null && edadHastaFiltro.length() > 0){
            p = PacienteSpecs.byMenorA(convertirFechaHasta()).and(p);
        }
        pacientes.addAll((Collection<? extends Paciente>) personaService.findAll(p));
    }
    
    private void buscarTodosLosPacientes(){
        Predicate p = PersonaSpecs.byClass(Paciente.class);
        pacientes = (List<Paciente>) personaService.findAll(p);
    }
    
     private Calendar convertirFechaDesde() {
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioDesde = anioActual - Utiles.convertStringToInt(edadDesdeFiltro).intValue();

        return Utiles.convertIntegerToCalendarYear(anioDesde);
    }

    private Calendar convertirFechaHasta() {
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioHasta = anioActual - Utiles.convertStringToInt(edadHastaFiltro).intValue();

        return Utiles.convertIntegerToCalendarYear(anioHasta);
    }
    
    public void buscarAlumno(){
        Predicate p = AlumnoSpecs.byNumeroDocumento(nroDocumentoAlumnoBuscado);
        Alumno alu = personaService.findOne(p);
        
        alumnoBuscado = alu;
    }
    
    //Carga auxiliar
    private List<Paciente> cargarListaAuxiliar(){
        List<Paciente> lista = new ArrayList<Paciente>();
        Paciente p1= new Paciente();
        Paciente p2 = new Paciente();
        
        Documento d1 = new Documento("123",TipoDocumento.DNI);
        Documento d2 = new Documento("456",TipoDocumento.DNI);
        
        p1.setNombre("Ignacio");
        p1.setApellido("López");
        p1.setDocumento(d1);
        
        p2.setNombre("David");
        p2.setApellido("Trezeguet");
        p2.setDocumento(d2);
        
        lista.add(p1);
        lista.add(p2);

        return lista;
        
    }
    
}
