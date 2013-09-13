/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.TrabajoPracticoService;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name= "nuevoDiagnosticoBean")
@ViewScoped
public class NuevoDiagnosticoBean {

    
    //Atributos para rellenar los campos
    private List<Materia> materias;
    private List<TrabajoPractico> trabajosPracticos;

    // Atributos para crear un nuevo diagnóstico
    private Materia selectedMateria;
    private TrabajoPractico selectedTrabajoPractico;
    private Diagnostico diagnostico;
    private Diagnostico selectedDiagnostico;
    
    //Servicios
    @ManagedProperty(value="#materiaService")
    private MateriaService materiaService;
    @ManagedProperty(value="#trabajoPracticoService")
    private TrabajoPracticoService trabajoPracticoService;
    
    /**
     * Creates a new instance of NuevoDiagnosticoBean
     */
    public NuevoDiagnosticoBean() {
    }
    
    //GETTERS Y SETTERS
    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<TrabajoPractico> getTrabajosPracticos() {
        return trabajosPracticos;
    }

    public void setTrabajosPracticos(List<TrabajoPractico> trabajosPracticos) {
        this.trabajosPracticos = trabajosPracticos;
    }

    public Materia getSelectedMateria() {
        return selectedMateria;
    }

    public void setSelectedMateria(Materia selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    public TrabajoPractico getSelectedTrabajoPractico() {
        return selectedTrabajoPractico;
    }

    public void setSelectedTrabajoPractico(TrabajoPractico selectedTrabajoPractico) {
        this.selectedTrabajoPractico = selectedTrabajoPractico;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Diagnostico getSelectedDiagnostico() {
        return selectedDiagnostico;
    }

    public void setSelectedDiagnostico(Diagnostico selectedDiagnostico) {
        this.selectedDiagnostico = selectedDiagnostico;
    }
    
    @PostConstruct
    public void init(){
        //cargar materias
        materias = materiaService.findAll();
        trabajosPracticos = trabajoPracticoService.findAll();
        
        diagnostico = new Diagnostico();
    }
    
    
}
