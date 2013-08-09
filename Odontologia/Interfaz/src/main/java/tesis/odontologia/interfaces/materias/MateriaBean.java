/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.materias;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.MateriaService;

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
    private List<Materia> materias;
    private List<Catedra> catedras;
    private Materia materia;
    private Catedra catedra;
    private Materia selectedMateria;
    private Catedra selectedCatedra;
    
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{catedraService}")
    private CatedraService catedraService;

    @PostConstruct
    public void init() {
        materias = materiaService.findAll();
        catedras = catedraService.findAll();
        resetFields();
    }
    
    public void resetFields() {
        materia = new Materia();
        catedra = new Catedra();
    }

    public MateriaBean() {
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

    public Materia getSelectedMateria() {
        return selectedMateria;
    }

    public void setSelectedMateria(Materia selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    public Catedra getSelectedCatedra() {
        return selectedCatedra;
    }

    public void setSelectedCatedra(Catedra selectedCatedra) {
        this.selectedCatedra = selectedCatedra;
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
}
