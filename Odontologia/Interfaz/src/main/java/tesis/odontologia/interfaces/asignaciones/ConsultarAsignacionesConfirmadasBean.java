/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.TrabajoPracticoService;

/**
 *
 * @author alespe
 */
@ManagedBean(name = "consultarAsignacionesConfirmadasBean")
@ViewScoped
public class ConsultarAsignacionesConfirmadasBean {
    //Listas para cargar tablas
    private List<AsignacionPaciente> asignacionesConfirmadas;
    
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private Catedra catedraFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    private AsignacionPaciente.EstadoAsignacion estadoFiltro;
    //Listas para cargar combos.
    private List<TrabajoPractico> trabajosPracticos;
    private List<Catedra> catedras;
    private List<Materia> materias;
    //Servicio
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{catedraService}")
    private CatedraService catedraService;
    @ManagedProperty(value="#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;
    
    public ConsultarAsignacionesConfirmadasBean() {
    }
    
    @PostConstruct
    public void init() {
        //Se cargan los combos.
        cargarCombos();
        asignacionesConfirmadas = new ArrayList<AsignacionPaciente>();
    }
    
    public void buscarAsignacionesConfirmadas() {
        estadoFiltro = AsignacionPaciente.EstadoAsignacion.CONFIRMADA;
        /*asignaciones = (List<AsignacionPaciente>) asignacionService.findAll(AsignacionPacienteSpecs.byEstadoAsignacion(estadoFiltro) );*/
         asignacionesConfirmadas = (List<AsignacionPaciente>) asignacionPacienteService.findAll();
        
    }

    
    //MÉTODOS AUXILIARES
    
    private void cargarCombos(){
        materias = buscarMaterias();
        catedras = buscarCatedras();
        trabajosPracticos = buscarTrabajosPracticos();
    } 
    private  List<Materia> buscarMaterias(){
        return materiaService.findAll();
    }
    
    private List<Catedra> buscarCatedras(){
        return catedraService.findAll();
    }
    
    private List<TrabajoPractico> buscarTrabajosPracticos(){
        return trabajoPracticoService.findAll();
    }
    
    
    // GETTERS Y SETTERS
    
    public AsignacionPaciente.EstadoAsignacion getEstadoFiltro() {
        return estadoFiltro;
    }

    public void setEstadoFiltro(AsignacionPaciente.EstadoAsignacion estadoFiltro) {
        this.estadoFiltro = estadoFiltro;
    }

    public List<TrabajoPractico> getTrabajosPracticos() {
        return trabajosPracticos;
    }

    public void setTrabajosPracticos(List<TrabajoPractico> trabajosPracticos) {
        this.trabajosPracticos = trabajosPracticos;
    }

    public List<Catedra> getCatedras() {
        return catedras;
    }

    public void setCatedras(List<Catedra> catedras) {
        this.catedras = catedras;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    
    public List<AsignacionPaciente> getAsignacionesConfirmadas() {
        return asignacionesConfirmadas;
    }

    public void setAsignacionesConfirmadas(List<AsignacionPaciente> asignacionesConfirmadas) {
        this.asignacionesConfirmadas = asignacionesConfirmadas;
    }

    public Materia getMateriaFiltro() {
        return materiaFiltro;
    }

    public void setMateriaFiltro(Materia materiaFiltro) {
        this.materiaFiltro = materiaFiltro;
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

    public AsignacionPacienteService getAsignacionPacienteService() {
        return asignacionPacienteService;
    }

    public void setAsignacionPacienteService(AsignacionPacienteService asignacionPacienteService) {
        this.asignacionPacienteService = asignacionPacienteService;
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

    public CatedraService getCatedraService() {
        return catedraService;
    }

    public void setCatedraService(CatedraService catedraService) {
        this.catedraService = catedraService;
    }

    public TrabajoPracticoService getTrabajoPracticoService() {
        return trabajoPracticoService;
    }

    public void setTrabajoPracticoService(TrabajoPracticoService trabajoPracticoService) {
        this.trabajoPracticoService = trabajoPracticoService;
    }
    
    
    
}
