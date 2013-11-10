/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.estadisticas;

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
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.AtencionService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.AtencionGenericaSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "consultarEstadisticasBean")
@ViewScoped
public class ConsultarEstadisticasBean {
    //Lista para cargar Tablas

    private CartesianChartModel linearModel;
    private CartesianChartModel categoryModel;
    private PieChartModel pieModel;
    private List<AsignacionPaciente> asignaciones;
    private List<AsignacionPaciente> asignacionesEstado;
    private List<AtencionGenerica> atencionesGenericas;
    //Atributos búsqueda avanzada.
    private Materia materiaFiltro;
    private Profesor profesor;
    private Date fechaDesdeFiltro;
    private Date fechaHastaFiltro;
    
    //Lista para cargar combos
    private List<Materia> materias;
    //Servicio
    @ManagedProperty(value = "#{asignacionPacienteService}")
    private AsignacionPacienteService asignacionPacienteService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{atencionService}")
    private AtencionService atencionService;

    public ConsultarEstadisticasBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");
        profesor = (Profesor) login.getPersona();

        //Se cargan los combos.
        cargarCombos();
        atencionesGenericas = new ArrayList<AtencionGenerica>();
        asignaciones = new ArrayList<AsignacionPaciente>();

        //Se cargan los gráficos
        createCategoryModel();
        createPieModel();

    }
    //MÉTODOS DE CALCULO DE ESTADISTICA

    public void calcularEstadisticas() {
        asignaciones = new ArrayList<AsignacionPaciente>();
        atencionesGenericas = new ArrayList<AtencionGenerica>();
       

        BooleanExpression predicateAsignacionesAutorizadas = AsignacionPacienteSpecs.byEstadoAsignacion(AsignacionPaciente.EstadoAsignacion.AUTORIZADA);
        BooleanExpression predicateAtenciones = null;
        BooleanExpression predicateAsignaciones = null;
        if (materiaFiltro != null) {
            if (validarFechaDesdeHasta(fechaDesdeFiltro, fechaHastaFiltro) == true) {
                materiaFiltro = materiaService.reload(materiaFiltro, 1);
                predicateAsignacionesAutorizadas = predicateAsignacionesAutorizadas.and((AsignacionPacienteSpecs.byMateria(materiaFiltro)));
                predicateAtenciones = (AtencionGenericaSpecs.byMateria(materiaFiltro));
                predicateAsignaciones = (AsignacionPacienteSpecs.byMateria(materiaFiltro));

                if (fechaDesdeFiltro == null && fechaHastaFiltro == null) {
                } else if (fechaDesdeFiltro != null && fechaHastaFiltro == null) {
                    predicateAsignacionesAutorizadas = predicateAsignacionesAutorizadas.and(AsignacionPacienteSpecs.byFecha(FechaUtils.convertDateToCalendar(fechaDesdeFiltro)));
                    predicateAtenciones = predicateAtenciones.and(AtencionGenericaSpecs.byFecha(FechaUtils.convertDateToCalendar(fechaDesdeFiltro)));
                    predicateAsignaciones = predicateAsignaciones.and(AsignacionPacienteSpecs.byFecha(FechaUtils.convertDateToCalendar(fechaDesdeFiltro)));

                } else {
                    predicateAsignacionesAutorizadas = predicateAsignacionesAutorizadas.and(AsignacionPacienteSpecs.byFechaDesdeHasta(FechaUtils.convertDateToCalendar(fechaDesdeFiltro), FechaUtils.convertDateToCalendar(fechaHastaFiltro)));
                    predicateAtenciones = predicateAtenciones.and(AtencionGenericaSpecs.byFechaDesdeHasta(FechaUtils.convertDateToCalendar(fechaDesdeFiltro), FechaUtils.convertDateToCalendar(fechaHastaFiltro)));
                    predicateAsignaciones = predicateAsignaciones.and(AsignacionPacienteSpecs.byFechaDesdeHasta(FechaUtils.convertDateToCalendar(fechaDesdeFiltro), FechaUtils.convertDateToCalendar(fechaHastaFiltro)));

                }
                atencionesGenericas = (List<AtencionGenerica>) atencionService.findAll(predicateAtenciones);
                asignaciones = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicateAsignacionesAutorizadas);
                asignacionesEstado = (List<AsignacionPaciente>) asignacionPacienteService.findAll(predicateAsignaciones);
               
                createCategoryModel();
                createPieModel();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar una materia", null));
        }
    }
    
    //MÉTODOS GRÁFICOS
    
    private void createPieModel() {  
        Integer cancelada =0;
        Integer pendiente =0;
        Integer anulada =0;
        Integer confirmada =0;
        Integer autorizada =0;
        Integer registrada =0;
        Integer noRegistrada =0;
        
        pieModel = new PieChartModel();
        if(asignacionesEstado!=null){
        for (AsignacionPaciente a : asignacionesEstado) {
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.CANCELADA)){
                        cancelada=cancelada +1;
                    }
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.ANULADA)){
                        anulada=anulada +1;
                    }
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.PENDIENTE)){
                        pendiente=pendiente +1;
                    }
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.CONFIRMADA)){
                        confirmada=confirmada +1;
                    }
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.AUTORIZADA)){
                        autorizada=autorizada +1;
                    }
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.REGISTRADA)){
                        registrada=registrada +1;
                    }
                    if(a.getEstado().equals(AsignacionPaciente.EstadoAsignacion.NOREGISTRADA)){
                        noRegistrada=noRegistrada +1;
                    }
                }
        }
        pieModel.set("CANCELADA", cancelada);  
        pieModel.set("PENDIENTE", pendiente);  
        pieModel.set("ANULADA", anulada);  
        pieModel.set("CONFIRMADA", confirmada);
        pieModel.set("AUTORIZADA", autorizada);
        pieModel.set("NO REGISTRADA", autorizada);
        pieModel.set("REGISTRADA", autorizada);
    }  

    private void createCategoryModel() {
        Integer atenciones = 0;
    Integer asignacionesAutorizadas = 0;
        categoryModel = new CartesianChartModel();
                for (AsignacionPaciente a : asignaciones) {
                    asignacionesAutorizadas = asignacionesAutorizadas + 1;
                }
                for (AtencionGenerica a : atencionesGenericas) {
                    atenciones = atenciones + 1;
                }
        ChartSeries asigAut = new ChartSeries();
        asigAut.setLabel("Asignaciones Autorizadas");

        asigAut.set("2008", asignacionesAutorizadas);

        ChartSeries aten = new ChartSeries();
        aten.setLabel("Atenciones");
        aten.set("2008", atenciones);

        categoryModel.addSeries(asigAut);
        categoryModel.addSeries(aten);
    }
    //SE CARGAN LOS COMBOS

    private void cargarCombos() {
        materias = buscarMaterias();
    }

    private List<Materia> buscarMaterias() {
        if (profesor == null) {
            return null;
        } else {
            profesor = personaService.reload(profesor, 1);
            return profesor.getListaMaterias();
        }
    }
    //Validar Fecha

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

    //GETTER AND SETTER
    
    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
    
    public List<AsignacionPaciente> getAsignacionesEstado() {
        return asignacionesEstado;
    }

    public void setAsignacionesEstado(List<AsignacionPaciente> asignacionesEstado) {
        this.asignacionesEstado = asignacionesEstado;
    }

    public List<AsignacionPaciente> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionPaciente> asignaciones) {
        this.asignaciones = asignaciones;
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

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
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

    public AtencionService getAtencionService() {
        return atencionService;
    }

    public void setAtencionService(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    public CartesianChartModel getLinearModel() {
        return linearModel;
    }

    public void setLinearModel(CartesianChartModel linearModel) {
        this.linearModel = linearModel;
    }

    public CartesianChartModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CartesianChartModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
