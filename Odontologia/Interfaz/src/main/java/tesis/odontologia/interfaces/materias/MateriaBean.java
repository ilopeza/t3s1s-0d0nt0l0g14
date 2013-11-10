/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.materias;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.DiagnosticoService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.DiagnosticoSpecs;
import tesis.odontologia.core.specification.MateriaSpecs;
import tesis.odontologia.interfaces.validacion.Validacion;

/**
 *
 * @author Maxi
 */
@ManagedBean(name = "materiaBean")
@ViewScoped
public class MateriaBean {

    /**
     * Creates a new instance of MateriasBean
     */
    private TrabajoPractico trabajoPractico;
    private TrabajoPractico selectedTrabajoPractico;
    private List<TrabajoPractico> trabajosPracticos;
    private Validacion validacion = new Validacion();
    private List<Materia> materias;
    private List<Catedra> catedras;
    private Materia materia;
    private Catedra catedra;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{diagnosticoService}")
    private DiagnosticoService diagnosticoService;
    @ManagedProperty(value = "#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;
    @ManagedProperty(value = "#{catedraService}")
    private CatedraService catedraService;
    private boolean nuevoTPHabilitado;
    private List<Diagnostico> diagnosticos;

    @PostConstruct
    public void init() {
        materias = materiaService.findAll();
        catedras = catedraService.findAll();
        trabajosPracticos = new ArrayList<TrabajoPractico>();
        resetFields();
        resetFieldsTP();
    }

    public void resetFields() {
        materia = new Materia();
        catedra = new Catedra();
    }

    public void resetFieldsTP() {
        trabajoPractico = new TrabajoPractico();
    }

    public void nuevoTrabajoPractico() {
        trabajoPractico = new TrabajoPractico();        
        nuevoTPHabilitado = true;
    }

    public void limpiarCancelar() {
        trabajoPractico = new TrabajoPractico();
        nuevoTPHabilitado = false;
    }

    public MateriaBean() {
    }

    public void modificarTrabajoPractico() {
        trabajoPractico = selectedTrabajoPractico;
        nuevoTPHabilitado = true;
        //retornarMateria();
    }

    public void eliminarTrabajoPractico() {
        trabajoPractico = selectedTrabajoPractico;
        if (validarTPconDiagnosticos()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede borrar el Trabajo Práctico. Existe al menos un Diagnóstico con este Trabajo Práctico", null));

            buscarTPs();
            resetFieldsTP();
            nuevoTPHabilitado = false;

        } else {

            materia = materiaService.reload(materia, 1);
            //materia.removeTrabajoPractico(trabajoPractico);
            materia.getTrabajoPractico().remove(trabajoPractico);
            materia = materiaService.save(materia);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(trabajoPractico.getNombre()
                    + " eliminado correctamente."));

            buscarTPs();
            resetFieldsTP();
            nuevoTPHabilitado = false;
        }
    }

    public void saveTrabajoPractico() {

        if (validar()) {
            if (trabajoPractico.isNew()) {
                materia = materiaService.reload(materia, 1);
                //materia.addTrabajoPractico(trabajoPractico);
                materia.getTrabajoPractico().add(trabajoPractico);
                materia = materiaService.save(materia);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(trabajoPractico.getNombre()
                        + " guardado correctamente."));

                buscarTPs();
                resetFieldsTP();

                nuevoTPHabilitado = false;
            } else {
                trabajoPractico = trabajoPracticoService.save(trabajoPractico);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(trabajoPractico.getNombre()
                        + " actualizado correctamente."));
                //trabajoPractico = new TrabajoPractico();
                //retornarMateria();            
                buscarTPs();
                resetFieldsTP();

                nuevoTPHabilitado = false;
            }
        }
    }

    public void retornarMateria() {

        BooleanExpression predicate = null;
        predicate = (MateriaSpecs.byTrabajoPractico(trabajoPractico));

        materia = materiaService.findOne(predicate);

        //return materia.getNombre();
    }

    public boolean validarTPconDiagnosticos() {
        diagnosticos = new ArrayList<Diagnostico>();
        BooleanExpression predicate = null;
        boolean var = false;
        predicate = (DiagnosticoSpecs.byTrabajoPractico(trabajoPractico));
        diagnosticos = (List<Diagnostico>) (diagnosticoService.findAll(predicate));
        if (diagnosticos.size() > 0) {
            var = true;
        }
        return var;
    }

    private boolean validar() {
        boolean varValidacion = true;

        if (validacion.nullEmpty(trabajoPractico.getNombre())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo nombre de Trabajo Práctico debe ser texto", null));
            varValidacion = false;
        }

        if (validacion.nullEmpty(trabajoPractico.getDescripcion())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingrese una descripción", null));
            varValidacion = false;
        }

        return varValidacion;
    }

    public void buscarTPs() {
        trabajosPracticos = buscarTrabajosPracticos();
    }

    private List<Materia> buscarMaterias() {
        return materiaService.findAll();
    }

    private List<TrabajoPractico> buscarTrabajosPracticos() {
        //return trabajoPracticoService.findAll();
        materia = materiaService.reload(materia, 1);
        return materia.getTrabajoPractico();
    }

    public void saveMateria() {
        materiaService.save(materia);
        init();
    }

    public void saveCatedra() {
        catedraService.save(catedra);
        init();
    }

    //GETTERS AND SETTERS
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

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Catedra getCatedra() {
        return catedra;
    }

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
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
     * @return the nuevoTPHabilitado
     */
    public boolean isNuevoTPHabilitado() {
        return nuevoTPHabilitado;
    }

    /**
     * @param nuevoTPHabilitado the nuevoTPHabilitado to set
     */
    public void setNuevoTPHabilitado(boolean nuevoTPHabilitado) {
        this.nuevoTPHabilitado = nuevoTPHabilitado;
    }

    /**
     * @return the diagnosticoService
     */
    public DiagnosticoService getDiagnosticoService() {
        return diagnosticoService;
    }

    /**
     * @param diagnosticoService the diagnosticoService to set
     */
    public void setDiagnosticoService(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    /**
     * @return the diagnosticos
     */
    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    /**
     * @param diagnosticos the diagnosticos to set
     */
    public void setDiagnosticos(List<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
}
