/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.trabajosPracticos;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.AtencionService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.AtencionGenericaSpecs;
import tesis.odontologia.core.specification.MateriaSpecs;
import tesis.odontologia.core.specification.TrabajoPracticoSpecs;
import tesis.odontologia.interfaces.validacion.Validacion;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "trabajoPracticoBean")
@ViewScoped
public class TrabajoPracticoBean {

    private List<Materia> materias;
    private Materia materia;
    private Materia selectedMateria;
    private TrabajoPractico trabajoPractico;
    private TrabajoPractico selectedTrabajoPractico;
    private List<TrabajoPractico> trabajosPracticos;
    private List<TrabajoPractico> trabajosPracticosMateria;
    private Validacion validacion = new Validacion();
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;

    /**
     * Creates a new instance of TrabajoPracticoBean
     */
    public TrabajoPracticoBean() {
    }

    @PostConstruct
    public void init() {

        materias = new ArrayList<Materia>();
        trabajosPracticos = new ArrayList<TrabajoPractico>();
        cargarCombos();
        resetFields();
    }

    public void resetFields() {
        trabajoPractico = new TrabajoPractico();
    }

    public void modificarTrabajoPractico() {
        trabajoPractico = selectedTrabajoPractico;
        
        retornarMateria();
        
    }

    public void eliminarTrabajoPractico() {
        materia = materiaService.reload(materia, 1);
        materia.getTrabajoPractico().remove(trabajoPractico);
        materia = materiaService.save(materia);
    }

    public void saveTrabajoPractico() {

        //if (validar()) {
            materia = materiaService.reload(materia, 1);
            materia.getTrabajoPractico().add(trabajoPractico);
            materia = materiaService.save(materia);
            
        //}
    }
    
    public void retornarMateria(){
        
        BooleanExpression predicate = null;        
        predicate = (MateriaSpecs.byTrabajoPractico(trabajoPractico));
        
        materia = materiaService.findOne(predicate);
        
        //return materia.getNombre();
    }

    private boolean validar() {
        boolean varValidacion = true;

        if (!validacion.validarTexto(trabajoPractico.getNombre())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo nombre de Trabajo Práctico debe ser texto", null));
            varValidacion = false;
        }

        if (validacion.nullEmpty(trabajoPractico.getDescripcion())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingrese una descripción", null));
            varValidacion = false;
        }

        return varValidacion;
    }

    public void cargarCombos() {
        materias = buscarMaterias();
        trabajosPracticos = buscarTrabajosPracticos();
    }

    private List<Materia> buscarMaterias() {
        return materiaService.findAll();
    }

    private List<TrabajoPractico> buscarTrabajosPracticos() {
        return trabajoPracticoService.findAll();
    }

    /**
     * @return the materias
     */
    public List<Materia> getMaterias() {
        return materias;
    }

    /**
     * @param materias the materias to set
     */
    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    /**
     * @return the materia
     */
    public Materia getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    /**
     * @return the selectedMateria
     */
    public Materia getSelectedMateria() {
        return selectedMateria;
    }

    /**
     * @param selectedMateria the selectedMateria to set
     */
    public void setSelectedMateria(Materia selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    /**
     * @return the trabajoPractico
     */
    public TrabajoPractico getTrabajoPractico() {
        return trabajoPractico;
    }

    /**
     * @param trabajoPractico the trabajoPractico to set
     */
    public void setTrabajoPractico(TrabajoPractico trabajoPractico) {
        this.trabajoPractico = trabajoPractico;
    }

    /**
     * @return the materiaService
     */
    public MateriaService getMateriaService() {
        return materiaService;
    }

    /**
     * @param materiaService the materiaService to set
     */
    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    /**
     * @return the selectedTrabajoPractico
     */
    public TrabajoPractico getSelectedTrabajoPractico() {
        return selectedTrabajoPractico;
    }

    /**
     * @param selectedTrabajoPractico the selectedTrabajoPractico to set
     */
    public void setSelectedTrabajoPractico(TrabajoPractico selectedTrabajoPractico) {
        this.selectedTrabajoPractico = selectedTrabajoPractico;
    }

    /**
     * @return the trabajosPracticos
     */
    public List<TrabajoPractico> getTrabajosPracticos() {
        return trabajosPracticos;
    }

    /**
     * @param trabajosPracticos the trabajosPracticos to set
     */
    public void setTrabajosPracticos(List<TrabajoPractico> trabajosPracticos) {
        this.trabajosPracticos = trabajosPracticos;
    }

    /**
     * @return the trabajoPracticoService
     */
    public TrabajoPracticoService getTrabajoPracticoService() {
        return trabajoPracticoService;
    }

    /**
     * @param trabajoPracticoService the trabajoPracticoService to set
     */
    public void setTrabajoPracticoService(TrabajoPracticoService trabajoPracticoService) {
        this.trabajoPracticoService = trabajoPracticoService;
    }

    /**
     * @return the trabajosPracticosMateria
     */
    public List<TrabajoPractico> getTrabajosPracticosMateria() {
        return trabajosPracticosMateria;
    }

    /**
     * @param trabajosPracticosMateria the trabajosPracticosMateria to set
     */
    public void setTrabajosPracticosMateria(List<TrabajoPractico> trabajosPracticosMateria) {
        this.trabajosPracticosMateria = trabajosPracticosMateria;
    }

    /**
     * @return the validacion
     */
    public Validacion getValidacion() {
        return validacion;
    }

    /**
     * @param validacion the validacion to set
     */
    public void setValidacion(Validacion validacion) {
        this.validacion = validacion;
    }
}
