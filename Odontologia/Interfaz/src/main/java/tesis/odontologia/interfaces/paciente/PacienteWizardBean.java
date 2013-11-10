/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
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
import tesis.odontologia.core.specification.AsignacionPacienteSpecs;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.interfaces.validacion.Validacion;

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
    // NO SE CARGAN EN LA TABLA (se cargan los de diagnosticos)
    private List<Diagnostico> diagnosticosNuevos;
    private List<Diagnostico> diagnosticos;
    private Diagnostico diagnostico;
    private Diagnostico selectedDiagnostico;
    private List<Diagnostico> diagnosticosEliminados; // Lista donde se guardan los diagnósticos eliminados.
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
    private boolean nuevoDiagnosticoHabilitado;
    private boolean edicionDiagnosticoHabilitado;
    private boolean habilitarBtnMod;
    private Date fechaNacimiento;
    private Validacion validacion = new Validacion();
    private String filtroBusqueda;
    private boolean mensaje = true;
    boolean unicoPacienteEncontrado;
    //Otros
    private int auxEstadoDiag; // Para manejar la baja del diagnóstico.

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
        nuevoDiagnosticoHabilitado = false;
        edicionDiagnosticoHabilitado = true;
        diagnosticosNuevos = new ArrayList<Diagnostico>();
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

//    public String actualizarComponentes() {
//        String var="";
//        if (mensaje) {
//            var = ":msg";
//        }         
//        if(mensaje==false) {
//            var = ":secondaryForm:tablaPacientesEncontrados";
//        }
//        
//        if(unicoPacienteEncontrado){
//            var = ":formWizard:wizardPaciente,:consultarPacienteForm:pnlConsultarPaciente";
//        }
//        return var;
//    }
    /**
     * Método para buscar los pacientes en el panel consultar paciente.
     */
    public void buscarPacientes() {
        pacientesEncontrados = new ArrayList<Paciente>();
        mensaje = true;
        boolean bandera = true;
        String docFiltro = this.getNumDocumentoBusqueda();
        String nomFiltro = this.getNombreApellidoBusqueda();
        //Predicate p = PacienteSpecs.byNombreOApellido(nomFiltro);        

        if (validarCamposBusqueda()) {

            if (nomFiltro != null || docFiltro != null) {

                //CASO EN QUE DOCUMENTO Y NOMBRE/APELLIDO ESTEN CARGADOS            
                if (nomFiltro != null && docFiltro != null) {
                    //BooleanExpression predicate = PacienteSpecs.byNombreOApellido(nomFiltro);
                    //predicate.and(PacienteSpecs.byNumeroDocumento(docFiltro));
                    bandera = false;
                    if (this.getPacientesPorNombreApellidoYdocumento(nomFiltro, docFiltro) == null || this.getPacientesPorNombreApellidoYdocumento(nomFiltro, docFiltro).isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encotraron pacientes con nombre o apellido" + nomFiltro + " y documento" + docFiltro, null));
                    }
                }

                if (bandera) {
                    //CASO EN QUE NOMBRE/APELLIDO ESTEN CARGADOS            
                    if (nomFiltro != null && nomFiltro.isEmpty() == false) {
                        //BooleanExpression predicate = PacienteSpecs.byNombreOApellido(nomFiltro);
                        if (this.getPacientesPorNombreYApellido(nomFiltro) == null || this.getPacientesPorNombreYApellido(nomFiltro).isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encotraron pacientes con nombre " + nomFiltro, null));
                        }
                    }

                    //CASO EN QUE DOCUMENTO ESTE CARGADOS            
                    if (docFiltro != null && docFiltro.isEmpty() == false) {
                        //BooleanExpression predicate = PacienteSpecs.byNumeroDocumento(docFiltro);
                        if (this.getPacientesPorDocumento(docFiltro) == null) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontraron pacientes con documento " + docFiltro, null));
                        }
                    }
                }
            }
        }
    }

    private List<Paciente> getPacientesPorDocumento(String numDocFiltro) {
        pacientesEncontrados = (List<Paciente>) personaService.findAll(PacienteSpecs.byNumeroDocumento(numDocFiltro).and(PersonaSpecs.byClass(Paciente.class)));
        if (pacientesEncontrados == null || pacientesEncontrados.isEmpty()) {
            return null;
        } else {
            return pacientesEncontrados;
        }

    }

    private List<Paciente> getPacientesPorNombreYApellido(String nomFiltro) {
        pacientesEncontrados = (List<Paciente>) personaService.findAll(PacienteSpecs.byNombreOApellido(nomFiltro).and(PersonaSpecs.byClass(Paciente.class)));
        if (pacientesEncontrados == null || pacientesEncontrados.isEmpty()) {
            return null;
        } else {
            return pacientesEncontrados;
        }
    }

    private List<Paciente> getPacientesPorNombreApellidoYdocumento(String nomFiltro, String numDocFiltro) {
        pacientesEncontrados = (List<Paciente>) personaService.findAll(PacienteSpecs.byNombreOApellido(nomFiltro).and(PacienteSpecs.byNumeroDocumento(numDocFiltro).and(PersonaSpecs.byClass(Paciente.class))));
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
            //diagnosticos = paciente.getHistoriaClinica().getDiagnostico();
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

        if (validar()) {
            try {
                if (selectedPaciente != null) {
                    actualizarPaciente();
                    //resetFields();
                } else {

                    nuevoPaciente();
                    //resetFields();
                }
                diagnosticos.clear();
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El paciente " + paciente.toString() + " no fue cargado correctamente", null));
                System.out.println(ex.getMessage());
            } finally {
                diagnosticos.addAll(paciente.getHistoriaClinica().getDiagnostico());
                diagnostico = new Diagnostico();
            }
        }
    }

    public void resetFields() {
        paciente = new Paciente();
    }

    private boolean validarCamposBusqueda() {

        String docFiltro = this.getNumDocumentoBusqueda();
        String nomFiltro = this.getNombreApellidoBusqueda();
        boolean varValidacion = true;

        if (validacion.nullEmpty(docFiltro) || validacion.nullEmpty(nomFiltro)) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingrese un parámetro de búsqueda", null));

        } else {
            if (!validacion.validarNumero(docFiltro) || docFiltro.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo documento debe ser numérico", null));
                varValidacion = false;
            }

            if (!validacion.validarTexto(nomFiltro) || nomFiltro.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo nombre o apellido debe ser texto", null));
                varValidacion = false;
            }
        }


        return varValidacion;
    }

    private boolean validar() {

        boolean varValidacion = true;
        if (!validacion.validarTexto(paciente.getNombre())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo nombre debe ser solo texto", null));
            varValidacion = false;
        }

        if (!validacion.validarTexto(paciente.getApellido())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo apellido debe ser solo texto", null));
            varValidacion = false;
        }

        //agregar fecha de nacimiento

        if (!validacion.validarNumero(paciente.getDocumento().getNumero())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo numero de documento debe ser solo numérico", null));
            varValidacion = false;
        }

        if (paciente.getEmail()!=null && !paciente.getEmail().isEmpty()) {
            if (!validacion.validarMail(paciente.getEmail())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo inválido", null));
                varValidacion = false;
            }
        }

        if (paciente.getNacionalidad()!= null && !paciente.getNacionalidad().isEmpty()) {
            if (getValidacion().validarTexto(paciente.getNacionalidad()) == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo nacionalidad debe ser solo texto", null));
                varValidacion = false;
            }
        }

        if (paciente.getLugarNacimiento()!=null&&!paciente.getLugarNacimiento().isEmpty()) {
            if (getValidacion().validarTexto(paciente.getLugarNacimiento()) == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo lugar de naciomiento debe ser solo texto", null));
                varValidacion = false;
            }
        }

        if (paciente.getProvincia()!=null && !paciente.getProvincia().isEmpty()) {
            if (getValidacion().validarTexto(paciente.getProvincia()) == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo provincia debe ser solo texto", null));
                varValidacion = false;
            }
        }

        if (getValidacion().validarTexto(paciente.getDomicilio().getCalle()) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo calle debe ser solo texto", null));
            varValidacion = false;
        }

        if (!validacion.validarNumero(paciente.getDomicilio().getCalleNumero())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo numero de calle debe ser solo numérico", null));
            varValidacion = false;
        }

        if (getValidacion().validarTexto(paciente.getDomicilio().getCiudadActual()) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo ciudad actual debe ser solo texto", null));
            varValidacion = false;
        }

//        if (!paciente.getDomicilio().getPiso().isEmpty()) {
//            if (validacion.validarNumero(paciente.getDomicilio().getPiso()) == false) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo piso debe ser solo numero", null));
//                varValidacion = false;
//            }
//        }

        if (paciente.getCelular()!=null && !paciente.getCelular().isEmpty()) {
            if (getValidacion().validarNumero(paciente.getCelular()) == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo celular debe ser solo numérico", null));
                varValidacion = false;
            }
        }

        if (paciente.getTelefono()!=null && !paciente.getTelefono().isEmpty()) {
            if (getValidacion().validarNumero(paciente.getTelefono()) == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo teléfono debe ser solo numérico", null));
                varValidacion = false;
            }
        }

        if (paciente.getTelefonoMedico()!=null && !paciente.getTelefonoMedico().isEmpty()) {
            if (getValidacion().validarNumero(paciente.getTelefonoMedico()) == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo teléfono médico debe ser solo numérico", null));
                varValidacion = false;
            }
        }

        return varValidacion;
    }

    private void nuevoPaciente() {
        paciente.getHistoriaClinica().getDiagnostico().addAll(diagnosticos);
        HistoriaClinica hc = HistoriaClinica.createDefault();
        hc.setDiagnostico(paciente.getHistoriaClinica().getDiagnostico());
        paciente.setHistoriaClinica(hc);

        paciente = personaService.save(paciente);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente " + paciente.toString()
                + " guardado correctamente."));
    }

    private void actualizarPaciente() {
        if (!diagnosticosNuevos.isEmpty()) {
            this.setearIdNuevosDiagosticos(); // Se setean a null los IDS de los NUEVOS DIAGNOSTICOS para no generar conflictos en la BD.
        }
        paciente.getHistoriaClinica().getDiagnostico().addAll(diagnosticos);
        paciente = personaService.save(paciente);
        diagnosticosNuevos.clear(); // Vuelve la lista a cero para que se puedan cargar nuevos diagnósticos.
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente " + paciente.toString()
                + " actualizado correctamente."));

    }

    /**
     * Agrega un diagnostico a la lista nuevosDiagnósticos, seteándole un ID
     * temporal (para que puedan ser identificados por la tabla) y el estado
     * PEDIENTE. Se los añade también a la lista de diagnósticos del paciente
     * para que puedan ser cargados en la tabla.
     */
    public void agregarDiagnosticoEnMemoria() {
        if (this.validarDescripcion() == true) {
            if (selectedDiagnostico == null) {
                setNuevoDiagnostico();
            } else {
                this.modificarDiagnosticoEnMemoria(selectedDiagnostico);
            }
            this.nuevoDiagnosticoHabilitado = false;
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El diagnóstico debe tener una descripción para poder ser agregado.", null));
        }
    }

    private void setNuevoDiagnostico() {
        diagnostico.setEstado(Diagnostico.EstadoDiagnostico.PENDIENTE);
        long idAux = paciente.getHistoriaClinica().getDiagnostico().size();
        diagnostico.setId(Long.valueOf(idAux));
        diagnosticosNuevos.add(diagnostico);
        diagnosticos.add(diagnostico);
        paciente.getHistoriaClinica().getDiagnostico().add(diagnostico);

        diagnostico = new Diagnostico();
    }

    /**
     * Método auxiliar para devolver un diagnóstico buscado en la lista de
     * diagnósticos del paciente
     *
     * @param diag a comparar
     * @return diagnóstico encontrado
     */
    public boolean modificarDiagnosticoEnMemoria(Diagnostico diag) {
        boolean band = false;
        for (Diagnostico d : diagnosticos) {
            if (d.equals(diag)) {
                diagnostico = d;
                band = true;
                //this.reestablecerDiagnostico();
                selectedDiagnostico = null;
                break;
            }
        }
        return band;
    }

    /**
     * Método para asegurarse de que no se incluya un diagnóstico sin
     * descripción.
     *
     * @return boolean false si no hay descripción.
     */
    private boolean validarDescripcion() {
        if (diagnostico.getDescripcion() == null || diagnostico.getDescripcion().compareTo("") == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void darDeBajaDiagnostico() {
        switch (auxEstadoDiag) {
            case (1):
                cambiarEstadoDiagnostico(Diagnostico.EstadoDiagnostico.SOLUCIONADO_EN_FACULTAD);
                break;
            case (2):
                cambiarEstadoDiagnostico(Diagnostico.EstadoDiagnostico.SOLUCIONADO_FUERA);
                break;
            case (3):
                cambiarEstadoDiagnostico(Diagnostico.EstadoDiagnostico.CANCELADO);
        }
    }
    
    public void darAltaDiagnostico(){
        cambiarEstadoDiagnostico(Diagnostico.EstadoDiagnostico.PENDIENTE);
    }

    /**
     * Toma el diagnóstico seleccionado de la tabla y le cambia el estado.
     */
    private void cambiarEstadoDiagnostico(Diagnostico.EstadoDiagnostico est) {
        //Para conocer cuál es el diagnóstico seleccionado en la lista
        // de diagnósticos del paciente (que es la que se carga en la interfaz),
        // y así ponerlo en CANCELADO.
        for (Diagnostico d : diagnosticos) {
            if (d.equals(selectedDiagnostico)) {
                d.setEstado(est);
                selectedDiagnostico = null;
                break;
            }
        }
    }

    /**
     * Setea el diagnóstico seleccionado como el que se va a mostrar en la
     * intergaz para que pueda ser modificado.
     */
    public void seleccionarModificarDiagnostico() {
        diagnostico = selectedDiagnostico;
        nuevoDiagnosticoHabilitado = true;
        //selectedDiagnostico = null;
    }

    /**
     * Elimina un diagnóstico seleccionado de la lista de diagnósticos del
     * paciente.
     */
//    public void eliminarDiagnosticoEnMemoria() {
//        for (Diagnostico d : diagnosticos) {
//            if (d.equals(selectedDiagnostico)) {
//                diagnosticosEliminados.add(d);
//                diagnosticos.remove(d);
//            }
//        }
//    }

    /**
     * Método auxiliar para poner en NULL los IDS de todos los diagnósticos
     * nuevos (O SEA LOS QUE NO ESTÁN TODAVÍA EN LA BD). Se lo usa para no
     * generar conflictos cuando se los guarde en la BD.
     */
    public void setearIdNuevosDiagosticos() {
        for (Diagnostico d : diagnosticos) {
            if (diagnosticosNuevos.contains(d)) {
                d.setId(null);
            }
        }
    }

    public void eventoSeleccionarDiagnostico() {
        if (selectedDiagnostico.getEstado().equals(Diagnostico.EstadoDiagnostico.PENDIENTE)) {
            habilitarBtnMod = false;
        }
    }

    public void filtrar() {
        diagnosticos = filtrarDiagnosticos();
        estadoDiagnosticoFiltro = null;
        trabajoPracticoFiltro = null;
    }

    public void verTodos() {
        filtrarHabilitado = true;
        diagnosticos.clear();
        diagnosticos.addAll(paciente.getHistoriaClinica().getDiagnostico());
    }

    /**
     * Método que recorre los diagnósticos en mememoria cargados en el paciente
     * y los va filtrando según las opciones seleccionadas (estado y TP).
     *
     * @return listaFiltrada
     */
    private List<Diagnostico> filtrarDiagnosticos() {
        List<Diagnostico> listaAux = new ArrayList<Diagnostico>();
        boolean diagnosticoEnLista = false; // FALSE= si no está en la listaAux.
        for (Diagnostico d : diagnosticos) {
            diagnosticoEnLista = false; // Vuelvo a buscar.
            if (trabajoPracticoFiltro != null) {
                if (estadoDiagnosticoFiltro != null && d.getTrabajoPractico().equals(trabajoPracticoFiltro) && d.getEstado().equals(estadoDiagnosticoFiltro)) { // filtra por los dos.
                    listaAux.add(d);
                    diagnosticoEnLista = true;
                } else {
                    if (estadoDiagnosticoFiltro == null && d.getTrabajoPractico().equals(trabajoPracticoFiltro)) {
                        listaAux.add(d);
                        diagnosticoEnLista = true;
                    }
                }
            } else {
                if (estadoDiagnosticoFiltro != null && d.getEstado().equals(estadoDiagnosticoFiltro)) {
                    listaAux.add(d);
                }
            }
        }
        filtrarHabilitado = false;
        return listaAux;
    }

    /**
     * Metodo que carga el paciente a la session para poderlo pasar a la pagina
     * en la que se visualiza la historia clinica
     *
     * @return
     */
    public String cargarPaciente() {
        if (selectedPaciente == null) {
            return null;
        }
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.remove("paciente");
        sessionMap.remove("historiaClinica");
        sessionMap.put("paciente", selectedPaciente);
        sessionMap.put("historiaClinica", selectedPaciente.getHistoriaClinica());
        return "historiaClinica";
    }

    /**
     * Método para "vaciar" un diagnóstico. Ejecutado por el botón "Cancelar"
     * del panel para un nuevo diagnóstico.
     */
    public void reestablecerDiagnostico() {
        diagnostico.setDescripcion("");
        diagnostico.setMateria(null);
        diagnostico.setTrabajoPractico(null);
        this.nuevoDiagnosticoHabilitado = false;
    }

    public void nuevoDiagnostico() {
        this.nuevoDiagnosticoHabilitado = true;
    }

    public void habilitarEdicionDiagnostico() {
        if (selectedDiagnostico.getEstado().equals(Diagnostico.EstadoDiagnostico.CANCELADO)) {
            edicionDiagnosticoHabilitado = true;
        } else {
            edicionDiagnosticoHabilitado = false;
        }
    }
    
    public boolean deshabilitarBtnAcciones(Diagnostico d) {

        if (d.getEstado().compareTo(Diagnostico.EstadoDiagnostico.PENDIENTE) == 0) {
            return false;
        }
        return true;
    }
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

    public List<Diagnostico> getDiagnosticosNuevos() {
        return diagnosticosNuevos;
    }

    public void setDiagnosticosNuevos(List<Diagnostico> diagnosticos) {
        this.diagnosticosNuevos = diagnosticos;
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

//    public String getNumDocumentoBusqueda() {
//        return numDocumentoBusqueda;
//    }
//
//    public void setNumDocumentoBusqueda(String numDocumentoBusqueda) {
//        this.numDocumentoBusqueda = numDocumentoBusqueda;
//    }
//
//    public String getNombreApellidoBusqueda() {
//        return nombreApellidoBusqueda;
//    }
//
//    public void setNombreApellidoBusqueda(String nombreApellidoBusqueda) {
//        this.nombreApellidoBusqueda = nombreApellidoBusqueda;
//    }
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

    public boolean isNuevoDiagnosticoHabilitado() {
        return nuevoDiagnosticoHabilitado;
    }

    public void setNuevoDiagnosticoHabilitado(boolean nuevoDiagnosticoHabilitado) {
        this.nuevoDiagnosticoHabilitado = nuevoDiagnosticoHabilitado;
    }

    public List<Diagnostico> getDiagnosticosEliminados() {
        return diagnosticosEliminados;
    }

    public void setDiagnosticosEliminados(List<Diagnostico> diagnosticosEliminados) {
        this.diagnosticosEliminados = diagnosticosEliminados;
    }

    public boolean isEdicionDiagnosticoHabilitado() {
        return edicionDiagnosticoHabilitado;
    }

    public void setEdicionDiagnosticoHabilitado(boolean edicionDiagnosticoHabilitado) {
        this.edicionDiagnosticoHabilitado = edicionDiagnosticoHabilitado;
    }

    /**
     * @return the filtroBusqueda
     */
    public String getFiltroBusqueda() {
        return filtroBusqueda;
    }

    /**
     * @param filtroBusqueda the filtroBusqueda to set
     */
    public void setFiltroBusqueda(String filtroBusqueda) {
        this.filtroBusqueda = filtroBusqueda;
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
     * @return the numDocumentoBusqueda
     */
    public String getNumDocumentoBusqueda() {
        return numDocumentoBusqueda;
    }

    /**
     * @param numDocumentoBusqueda the numDocumentoBusqueda to set
     */
    public void setNumDocumentoBusqueda(String numDocumentoBusqueda) {
        this.numDocumentoBusqueda = numDocumentoBusqueda;
    }

    /**
     * @return the nombreApellidoBusqueda
     */
    public String getNombreApellidoBusqueda() {
        return nombreApellidoBusqueda;
    }

    /**
     * @param nombreApellidoBusqueda the nombreApellidoBusqueda to set
     */
    public void setNombreApellidoBusqueda(String nombreApellidoBusqueda) {
        this.nombreApellidoBusqueda = nombreApellidoBusqueda;
    }

    public boolean isHabilitarBtnMod() {
        return habilitarBtnMod;
    }

    public void setHabilitarBtnMod(boolean habilitarBtnMod) {
        this.habilitarBtnMod = habilitarBtnMod;
    }

    public int getAuxEstadoDiag() {
        return auxEstadoDiag;
    }

    public void setAuxEstadoDiag(int auxEstadoDiag) {
        this.auxEstadoDiag = auxEstadoDiag;
    }
}
