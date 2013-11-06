/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.historiaClinica;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.service.AtencionService;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.AtencionGenericaSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author alespe
 */
@ManagedBean(name = "consultarAtencionesRealizadasBean")
@ViewScoped
public class ConsultarAtencionesRealizadasBean {
    
    //Lista para cargar Tablas
    private List<AtencionGenerica> atencionesGenericas;
    
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private Catedra catedraFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    private Profesor profesor;
    private Date fechaDesdeFiltro;
    private Date fechaHastaFiltro;

    
    //Listas para cargar combos.
    private List<TrabajoPractico> trabajosPracticos;
    private List<Catedra> catedras;
    private List<Materia> materias;
    
    //Servicio
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{catedraService}")
    private CatedraService catedraService;
    @ManagedProperty(value = "#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;
    @ManagedProperty(value = "#{atencionService}")
    private AtencionService atencionService;

    public ConsultarAtencionesRealizadasBean() {
        
    }
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");
        profesor = (Profesor) login.getPersona();

        //Se cargan los combos.
        cargarCombos();
        atencionesGenericas=new ArrayList<AtencionGenerica>();
    }
    
    public List<AtencionGenerica> buscarAtencionesRealizadas(){
        atencionesGenericas= new ArrayList<AtencionGenerica>();
        BooleanExpression predicate = null;
        if (materiaFiltro != null) {
            if(validarFechaDesdeHasta(fechaDesdeFiltro, fechaHastaFiltro)==true){
            materiaFiltro = materiaService.reload(materiaFiltro, 1);
            predicate = (AtencionGenericaSpecs.byMateria(materiaFiltro));
            
            if (catedraFiltro != null) {
                predicate = predicate.and(AtencionGenericaSpecs.byCatedra(catedraFiltro));
            }
            if (trabajoPracticoFiltro != null) {
                predicate = predicate.and(AtencionGenericaSpecs.byTrabajoPractico(trabajoPracticoFiltro));
            }
            if (fechaDesdeFiltro == null && fechaHastaFiltro == null) {
            } else if (fechaDesdeFiltro != null && fechaHastaFiltro == null) {
                predicate = predicate.and(AtencionGenericaSpecs.byFecha(FechaUtils.convertDateToCalendar(fechaDesdeFiltro)));
            } else {
                predicate = predicate.and(AtencionGenericaSpecs.byFechaDesdeHasta(FechaUtils.convertDateToCalendar(fechaDesdeFiltro), FechaUtils.convertDateToCalendar(fechaHastaFiltro)));
            }
            atencionesGenericas=(List<AtencionGenerica>)atencionService.findAll(predicate);

        }
        }else{
             FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar una materia", null));
        }
        return atencionesGenericas;
    }
    
    
    //MÉTODOS AUXILIARES
    
    private boolean validarFechaDesdeHasta(Date fechaDesde, Date fechaHasta) {
        if (fechaDesde != null && fechaDesde.compareTo(fechaHasta) <= 0) {
            return true;
        } else if (fechaDesde == null) {
            if (fechaHasta != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe ingresar una Fecha de Atención Desde", null));
                return false;
            } else {
                return true;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "La Fecha de Atención Desde debe ser mayor que la fecha de Atención Hasta", null));
            return false;
        }
    }
    private void cargarCombos() {
        materias = buscarMaterias();
        catedras = buscarCatedras();
        trabajosPracticos = buscarTrabajosPracticos();
    }

    public void filtrarCombosPorMateria() {
        if (materiaFiltro != null) {
            materiaFiltro = materiaService.reload(materiaFiltro, 1);
            buscarCatedras();
            buscarTrabajosPracticos();
        } else {
            catedras = new ArrayList<Catedra>();
            trabajosPracticos = new ArrayList<TrabajoPractico>();
        }
    }

    private List<Materia> buscarMaterias() {
        if (profesor == null) {
            return null;
        } else {
            profesor = personaService.reload(profesor, 1);
            return profesor.getListaMaterias();
        }

    }

    public List<Catedra> buscarCatedras() {

        if (materiaFiltro == null) {
            return null;
        } else {
            catedras = materiaFiltro.getCatedra();
            return catedras;
        }
    }

    public List<TrabajoPractico> buscarTrabajosPracticos() {
        if (materiaFiltro == null) {
            return null;
        } else {
            trabajosPracticos = materiaFiltro.getTrabajoPractico();
            return trabajosPracticos;
        }
    }
    
    //GETTER AND SETTER
    
    public AtencionService getAtencionService() {
        return atencionService;
    }

    public void setAtencionService(AtencionService atencionService) {
        this.atencionService = atencionService;
    }
    
    public List<AtencionGenerica> getAtencionesGenericas() {
        return atencionesGenericas;
    }

    public void setAtencionesGenericas(List<AtencionGenerica> atencionesGenericas) {
        this.atencionesGenericas = atencionesGenericas;
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Date getFechaDesdeFiltro() {
        return fechaDesdeFiltro;
    }

    public void setFechaDesdeFiltro(Date fechaDesdeFiltro) {
        this.fechaDesdeFiltro = fechaDesdeFiltro;
    }

    public Date getFechaHastaFiltro() {
        return fechaHastaFiltro;
    }

    public void setFechaHastaFiltro(Date fechaHastaFiltro) {
        this.fechaHastaFiltro = fechaHastaFiltro;
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
