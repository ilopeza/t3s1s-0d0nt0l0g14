/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import com.mysema.query.types.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.Domicilio;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.DiagnosticoService;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "pacienteWizardBean")
@ViewScoped
public class PacienteWizardBean {

    //Atributos para crear.
    private Paciente paciente;
    private AsignacionPaciente asignacion;
    //Listas para los combos.
    private List<Documento.TipoDocumento> listaTipoDocumento;
    private List<Paciente.EstadoCivilTipo> listaEstadoCivilTipo;
    private List<Paciente.EstudiosTipo> listaEstudioTipo;
    private List<Paciente.SexoTipo> listaSexo;
    private List<Materia> materias;
    private List<TrabajoPractico> trabajosPracticos;
    private List<Diagnostico.EstadoDiagnostico> estadosDiagnostico;
    //Atributos para las tablas.
    private List<Paciente> pacientesEncontrados;
    private Paciente selectedPaciente;
    // Lista auxiliar que va a almacenar todos los diagnósticos nuevos que se carguen.
    // NO SE CARGAN EN LA TABLA (se cargan los de paciente.getDiagnosticos())
    private List<Diagnostico> nuevosDiagnosticos;
    private List<Diagnostico> diagnosticos;
    private Diagnostico diagnostico;
    private Diagnostico selectedDiagnostico;
    //Atributos seleccionados para los combos.
    private Materia selectedMateria;
    private TrabajoPractico selectedTrabajoPractico;
    //Atributos para las búsquedas.
    private String numDocumentoBusqueda;
    private String nombreApellidoBusqueda;
    // Atributos para filtro.
    private Diagnostico.EstadoDiagnostico estadoDiagnosticoFiltro;
    private TrabajoPractico trabajoPracticoFiltro;
    //Servicios
    @ManagedProperty(value = "#{materiaService}")
    private MateriaService materiaService;
    @ManagedProperty(value = "#{trabajoPracticoService}")
    private TrabajoPracticoService trabajoPracticoService;
    @ManagedProperty(value = "#{diagnosticoService}")
    private DiagnosticoService diagnosticoService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    
    //Atributos para deshabilitar/habilitar       
    private boolean estaDeshabilitado;
    private boolean filtrarHabilitado;
    private Date fechaNacimiento;
    //CONSTRUCTORES

    /**
     * Creates a new instance of PacienteWizardBean
     */
    public PacienteWizardBean() {
    }
    //caseros 2250
    //e1

    //MÉTODOS AUXILIARES
    @PostConstruct
    public void init() {
//        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//        paciente = (Paciente) sessionMap.get("paciente");
//        HistoriaClinica historiaClinica = (HistoriaClinica) sessionMap.get("historiaClinica");
//        if(paciente != null && historiaClinica != null) {
//            estaDeshabilitado = true;
//        }
//        if(historiaClinica != null) {
//            diagnosticos = historiaClinica.getDiagnostico();
//        } else {
            diagnosticos = new ArrayList<Diagnostico>();
//        }
        if (paciente == null) {
            paciente = new Paciente();
            paciente.setDomicilio(new Domicilio());
            paciente.setDocumento(new Documento());
            paciente.setHistoriaClinica(HistoriaClinica.createDefault());
            paciente.setSexo(Paciente.SexoTipo.MASCULIN0);
            paciente.setEmail("");
            paciente.getHistoriaClinica().setDiagnostico(new ArrayList<Diagnostico>());

        }
        if (diagnostico == null) {
            diagnostico = new Diagnostico();
        }
        filtrarHabilitado = true;
        nuevosDiagnosticos = new ArrayList<Diagnostico>();
        cargarCombos();

    }
    
    public void cargarMaterias() {
        materias = materiaService.findAll();
    }

    public void cargarTrabajosPracticos() {
        trabajosPracticos = trabajoPracticoService.findAll();
    }

    public void cargarCombos() {
        cargarMaterias();
        cargarTrabajosPracticos();
    }

    public void cargarEstadosDiagnostico() {
    }

    /**
     * Método para buscar los pacientes en el panel consultar paciente.
     */
    public void buscarPacientes() {
        pacientesEncontrados = new ArrayList<Paciente>();
        Predicate p = null;
        String docFiltro = this.getNumDocumentoBusqueda();
        String nomFiltro = this.getNombreApellidoBusqueda();

        if (docFiltro != null && docFiltro.isEmpty() == false) {
            if (this.getPacientesPorDocumento(p, docFiltro) == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontraron pacientes con documento " + docFiltro, null));
            }
        } else {
            if (nomFiltro != null && nomFiltro.isEmpty() == false) {
                if (this.getPacientesPorNombreYApellido(p, nomFiltro) == null || this.getPacientesPorNombreYApellido(p, nomFiltro).isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encotraron pacientes con nombre " + nomFiltro, null));
                }
            }
        }
    }

    private List<Paciente> getPacientesPorDocumento(Predicate p, String numDocFiltro) {
        p = PacienteSpecs.byNumeroDocumento(numDocFiltro);
        Paciente pac = (Paciente) getPersonaService().findOne(p);

        if (pac == null) {
            return null;
        } else {
            pacientesEncontrados.add(pac);
            return pacientesEncontrados;
        }
    }

    private List<Paciente> getPacientesPorNombreYApellido(Predicate p, String nomFiltro) {
        pacientesEncontrados = (List<Paciente>) personaService.findAll(PacienteSpecs.byNombreOApellido(nomFiltro).and(PersonaSpecs.byClass(Paciente.class)));
        if (pacientesEncontrados == null || pacientesEncontrados.isEmpty()) {
            return null;
        } else {
            return pacientesEncontrados;
        }
    }

    /**
     * Para habilitar/deshabilitar los campos.
     */
    public void habilitar() {
        estaDeshabilitado = false;
    }

    /**
     * Para seleccionar un paciente de la lista mostrada en el dialog (la que
     * muestra todos los pacientes encontrados) y mostrar un mensaje.
     */
    public void mostrarPacienteSeleccionado() {
        if (seleccionarPacienteDeLista() == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No seleccionó ningún paciente", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente " + selectedPaciente.toString() + "seleccionado", null));
        }
    }

    public boolean seleccionarPacienteDeLista() {
        if (selectedPaciente == null) {
            return false;
        } else {
            selectedPaciente = personaService.reload(selectedPaciente, 2);
            paciente = selectedPaciente;
            diagnosticos.addAll(paciente.getHistoriaClinica().getDiagnostico());
            estaDeshabilitado = true;
            return true;
        }
    }

    /**
     * Método para guardar un paciente en la BD. Sete la HC y los diagnósticos
     * de la HC y después lo guarda con el save().
     */
    public void savePaciente() {
        paciente.getHistoriaClinica().getDiagnostico().clear();

        try {
            if (selectedPaciente != null) {
                actualizarPaciente();
            } else {

                nuevoPaciente();
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El paciente " + paciente.toString() + " no fue cargado correctamente", null));
            System.out.println(ex.getMessage());
        }

    }

    private void nuevoPaciente() {
        paciente.getHistoriaClinica().getDiagnostico().addAll(diagnosticos);
        HistoriaClinica hc = HistoriaClinica.createDefault();
        hc.setDiagnostico(paciente.getHistoriaClinica().getDiagnostico());
        paciente.setHistoriaClinica(hc);

        getPersonaService().save(paciente);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente " + paciente.toString()
                + " guardado correctamente."));
    }

    private void actualizarPaciente() {
        if (!nuevosDiagnosticos.isEmpty()) {
            this.setearIdNuevosDiagosticos(); // Se setean a null los IDS de los NUEVOS DIAGNOSTICOS para no generar conflictos en la BD.
        }
        paciente.getHistoriaClinica().getDiagnostico().addAll(diagnosticos);
        paciente = personaService.save(paciente);
        nuevosDiagnosticos.clear(); // Vuelve la lista a cero para que se puedan cargar nuevos diagnósticos.
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente " + paciente.toString()
                + " actualizado correctamente."));
    }

    /**
     * Agrega un diagnostico a la lista nuevosDiagnósticos, seteándole un ID
     * temporal (para que puedan ser identificados por la tabla) y el estado
     * PEDIENTE. Se los añade también a la lista de diagnósticos del paciente
     * para que puedan ser cargados en la tabla.
     */
    public void agregarDiagnostico() {
        if (selectedDiagnostico == null) {
            setNuevoDiagnostico();
        } else {
            this.modificarDiagnostico(selectedDiagnostico);
        }
    }

    private void setNuevoDiagnostico() {
        diagnostico.setEstado(Diagnostico.EstadoDiagnostico.PENDIENTE);
        long idAux = paciente.getHistoriaClinica().getDiagnostico().size();
        diagnostico.setId(Long.valueOf(idAux));
        nuevosDiagnosticos.add(diagnostico);
        diagnosticos.add(diagnostico);
        //paciente.getHistoriaClinica().getDiagnostico().add(diagnostico);

        diagnostico = new Diagnostico();
    }

    /**
     * Método auxiliar para devolver un diagnóstico buscado en la lista de
     * diagnósticos del paciente
     *
     * @param diag a comparar
     * @return diagnóstico encontrado
     */
    public boolean modificarDiagnostico(Diagnostico diag) {
        boolean band = false;
        for (Diagnostico d : diagnosticos) {
            if (d.equals(diag)) {
                diagnostico = d;
                band = true;
                selectedDiagnostico = null;
                break;
            }
        }
        return band;
    }

    /**
     * Toma el diagnóstico seleccionado de la tabla y le cambia el estado a
     * CANCELADO.
     */
    public void cancelarDiagnostico() {
        //Para conocer cuál es el diagnóstico seleccionado en la lista
        // de diagnósticos del paciente (que es la que se carga en la interfaz),
        // y así ponerlo en CANCELADO.
        for (Diagnostico d : diagnosticos) {
            if (d.equals(selectedDiagnostico)) {
                d.setEstado(Diagnostico.EstadoDiagnostico.CANCELADO);
                selectedDiagnostico = null;
                break;
            }
        }
    }

    /**
     * Setea el diagnóstico seleccionado como el que se va a mostrar en la
     * intergaz para que pueda ser modificado.
     */
    public void modificarDiagnostico() {
        diagnostico = selectedDiagnostico;
        //selectedDiagnostico = null;
    }

    /**
     * Elimina un diagnóstico seleccionado de la lista de diagnósticos del
     * paciente.
     */
    public void eliminarDiagnostico() {
        for (Diagnostico d : diagnosticos) {
            if (d.equals(selectedDiagnostico)) {
                diagnosticos.remove(d);
            }
        }
    }

    /**
     * Método auxiliar para poner en NULL los IDS de todos los diagnósticos
     * nuevos (O SEA LOS QUE NO ESTÁN TODAVÍA EN LA BD). Se lo usa para no
     * generar conflictos cuando se los guarde en la BD.
     */
    public void setearIdNuevosDiagosticos() {
        for (Diagnostico d : diagnosticos) {
            if (nuevosDiagnosticos.contains(d)) {
                d.setId(null);
            }
        }
    }
    
    public void filtrar(){
        diagnosticos = filtrarDiagnosticos();
    }
    
    public void verTodos(){
        filtrarHabilitado = true;
        diagnosticos.clear();
        diagnosticos.addAll(paciente.getHistoriaClinica().getDiagnostico());
    }
    
    /**
     * Método que recorre los diagnósticos en mememoria cargados en el paciente
     * y los va filtrando según las opciones seleccionadas (estado y TP).
     * @return listaFiltrada
     */
    private List<Diagnostico> filtrarDiagnosticos(){
        List<Diagnostico> listaAux = new ArrayList<Diagnostico>(); 
        for (Diagnostico d : diagnosticos) {
//            filtrarPorTrabajoPractico(d, listaAux);
//            filtrarPorEstado(d, listaAux);
            if (trabajoPracticoFiltro != null || estadoDiagnosticoFiltro != null) {
                if (d.getTrabajoPractico().equals(trabajoPracticoFiltro) || d.getEstado().equals(estadoDiagnosticoFiltro)) {
                    listaAux.add(d);
                }
            }
        }
        filtrarHabilitado = false;
        return listaAux;
    }
    
    /**
     * Metodo que carga el paciente a la session para poderlo pasar a la pagina en la
     * que se visualiza la historia clinica
     * @return 
     */
    public String cargarPaciente() {
        if(selectedPaciente == null) return null;
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.remove("paciente");
        sessionMap.remove("historiaClinica");
        sessionMap.put("paciente", selectedPaciente);
        sessionMap.put("historiaClinica", selectedPaciente.getHistoriaClinica());
        return "historiaClinica";
    }
    
  
    /**
     * Filtra por TP
     * @param d Diagnóstico a comparar.
     * @param listaAux lista de diagnósticos filtrados.
     */
//    private void filtrarPorTrabajoPractico(Diagnostico d, List<Diagnostico> listaAux) {
//        if(trabajoPracticoFiltro != null){
//            if(d.getTrabajoPractico().equals(trabajoPracticoFiltro)){
//                listaAux.add(d);
//            }
//        }
//    }
//
//    /**
//     * Idem anterior pero con estadoDiagnostico
//     * @param d
//     * @param listaAux 
//     */
//    private void filtrarPorEstado(Diagnostico d, List<Diagnostico> listaAux) {
//        if (estadoDiagnosticoFiltro != null) {
//            if (d.getEstado().equals(estadoDiagnosticoFiltro)) {
//                listaAux.add(d);
//            }
//        }
//    }

    //GETTERS Y SETTERS
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Documento.TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(Documento.TipoDocumento.values());
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<Documento.TipoDocumento> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public List<Paciente.EstadoCivilTipo> getListaEstadoCivilTipo() {
        listaEstadoCivilTipo = Arrays.asList(Paciente.EstadoCivilTipo.values());
        return listaEstadoCivilTipo;
    }

    public void setListaEstadoCivilTipo(List<Paciente.EstadoCivilTipo> listaEstadoCivilTipo) {
        this.listaEstadoCivilTipo = listaEstadoCivilTipo;
    }

    public List<Paciente.EstudiosTipo> getListaEstudioTipo() {
        listaEstudioTipo = Arrays.asList(Paciente.EstudiosTipo.values());
        return listaEstudioTipo;
    }

    public void setListaEstudioTipo(List<Paciente.EstudiosTipo> listaEstudioTipo) {
        this.listaEstudioTipo = listaEstudioTipo;
    }

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

    public List<Paciente> getPacientesEncontrados() {
        return pacientesEncontrados;
    }

    public void setPacientesEncontrados(List<Paciente> pacientesEncontrados) {
        this.pacientesEncontrados = pacientesEncontrados;
    }

    public Paciente getSelectedPaciente() {
        return selectedPaciente;
    }

    public void setSelectedPaciente(Paciente selectedPaciente) {
        this.selectedPaciente = selectedPaciente;
    }

    public List<Diagnostico> getNuevosDiagnosticos() {
        return nuevosDiagnosticos;
    }

    public void setNuevosDiagnosticos(List<Diagnostico> diagnosticos) {
        this.nuevosDiagnosticos = diagnosticos;
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

    public String getNumDocumentoBusqueda() {
        return numDocumentoBusqueda;
    }

    public void setNumDocumentoBusqueda(String numDocumentoBusqueda) {
        this.numDocumentoBusqueda = numDocumentoBusqueda;
    }

    public String getNombreApellidoBusqueda() {
        return nombreApellidoBusqueda;
    }

    public void setNombreApellidoBusqueda(String nombreApellidoBusqueda) {
        this.nombreApellidoBusqueda = nombreApellidoBusqueda;
    }

    public MateriaService getMateriaService() {
        return materiaService;
    }

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    public TrabajoPracticoService getTrabajoPracticoService() {
        return trabajoPracticoService;
    }

    public void setTrabajoPracticoService(TrabajoPracticoService trabajoPracticoService) {
        this.trabajoPracticoService = trabajoPracticoService;
    }

    public DiagnosticoService getDiagnosticoService() {
        return diagnosticoService;
    }

    public void setDiagnosticoService(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public boolean isEstaDeshabilitado() {
        return estaDeshabilitado;
    }

    public void setEstaDeshabilitado(boolean estaDeshabilitado) {
        this.estaDeshabilitado = estaDeshabilitado;
    }

    public AsignacionPaciente getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionPaciente asignacion) {
        this.asignacion = asignacion;
    }

    public Date getFechaNacimiento() {
        return paciente.getFechaNacimiento() == null ? null : paciente.getFechaNacimiento().getTime();
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        if (fechaNacimiento == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha de nacimiento no puede ser nula.", null));
            return;
        }
        this.fechaNacimiento = fechaNacimiento;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        paciente.setFechaNacimiento(cal);
    }

    /**
     * @return the listaSexo
     */
    public List<Paciente.SexoTipo> getListaSexo() {
        listaSexo = Arrays.asList(Paciente.SexoTipo.values());
        return listaSexo;
    }

    /**
     * @param listaSexo the listaSexo to set
     */
    public void setListaSexo(List<Paciente.SexoTipo> listaSexo) {
        this.listaSexo = listaSexo;
    }

    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(List<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public List<Diagnostico.EstadoDiagnostico> getEstadosDiagnostico() {
        estadosDiagnostico = Arrays.asList(Diagnostico.EstadoDiagnostico.values());
        return estadosDiagnostico;
    }

    public void setEstadosDiagnostico(List<Diagnostico.EstadoDiagnostico> estadosDiagnostico) {
        this.estadosDiagnostico = estadosDiagnostico;
    }

    public Diagnostico.EstadoDiagnostico getEstadoDiagnosticoFiltro() {
        return estadoDiagnosticoFiltro;
    }

    public void setEstadoDiagnosticoFiltro(Diagnostico.EstadoDiagnostico estadoDiagnosticoFiltro) {
        this.estadoDiagnosticoFiltro = estadoDiagnosticoFiltro;
    }

    public TrabajoPractico getTrabajoPracticoFiltro() {
        return trabajoPracticoFiltro;
    }

    public void setTrabajoPracticoFiltro(TrabajoPractico trabajoPracticoFiltro) {
        this.trabajoPracticoFiltro = trabajoPracticoFiltro;
    }
    
     public boolean isFiltrarHabilitado() {
        return filtrarHabilitado;
    }

    public void setFiltrarHabilitado(boolean filtrarHabilitado) {
        this.filtrarHabilitado = filtrarHabilitado;
    }
}
