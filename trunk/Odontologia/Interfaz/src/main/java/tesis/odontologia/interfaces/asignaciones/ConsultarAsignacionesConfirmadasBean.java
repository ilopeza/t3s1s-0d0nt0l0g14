/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.utils.FechaUtils;


/**
 *
 * @author alespe
 */
@ManagedBean(name = "consultarAsignacionesConfirmadasBean")
@ViewScoped
public class ConsultarAsignacionesConfirmadasBean {
    //Lista para cargar Tablas
    private List<AsignacionPacienteAux> asignacionesConfirmadas;
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
    @ManagedProperty(value = "#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;

    public ConsultarAsignacionesConfirmadasBean() {
    }

    @PostConstruct
    public void init() {
        //Se cargan los combos.
        cargarCombos();
         asignacionesConfirmadas = new ArrayList<AsignacionPacienteAux>();
    }

    public List<AsignacionPacienteAux> buscarAsignacionesConfirmadas() {
        
        List<AsignacionPaciente> asignaciones;
        asignacionesConfirmadas = new ArrayList<AsignacionPacienteAux>();
        
        BooleanExpression predicate = AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);        
        if(catedraFiltro != null) {
            //predicate = predicate.and(AsignacionPacienteSpecs.byCatedra(catedraFiltro));
        }
        asignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicate);
        for(AsignacionPaciente a: asignaciones){
            asignacionesConfirmadas.add(new AsignacionPacienteAux(a));
        }
        
        if (asignacionesConfirmadas == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existen Asignaciones Confirmadas Actualmente.", null));
            return null;
        }
        return asignacionesConfirmadas;

    }

    
    //MÉTODOS AUXILIARES

    private void cargarCombos() {
        materias = buscarMaterias();
        catedras = buscarCatedras();
        trabajosPracticos = buscarTrabajosPracticos();
    }

    private List<Materia> buscarMaterias() {
        return materiaService.findAll();
    }

    public List<Catedra> buscarCatedras() {

        if (materiaFiltro == null) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una materia", null));
            return null;
        } else {
            catedras = materiaFiltro.getCatedra();
            return catedras;
        }
    }

    public List<TrabajoPractico> buscarTrabajosPracticos() {
         if (materiaFiltro == null) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una materia", null));
            return null;
        } else {
            trabajosPracticos = materiaFiltro.getTrabajoPractico();
            return trabajosPracticos;
        }
    }

    // GETTERS Y SETTERS
    public List<AsignacionPacienteAux> getAsignacionesConfirmadas() {
        return asignacionesConfirmadas;
    }

    public void setAsignacionesConfirmadas(List<AsignacionPacienteAux> asignacionesConfirmadas) {
        this.asignacionesConfirmadas = asignacionesConfirmadas;
    }
   
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
    // Clase Auxiliar
    public class AsignacionPacienteAux {

    private String paciente;
    private String alumno;
    private String fecha;

    public AsignacionPacienteAux(AsignacionPaciente asignacionPaciente) {
    this.paciente=asignacionPaciente.getPaciente().toString();
    this.alumno=asignacionPaciente.getAlumno().toString();
    this.fecha= FechaUtils.fechaMaskFormat(asignacionPaciente.getFechaAsignacion(),"dd/MM/yyyy HH:mm");
    }
    

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
}


