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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.Domicilio;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;

/**
 *
 * @author Ignacio
 */
@ManagedBean(name = "formPacienteBean")
@ViewScoped
public class FormPacienteBean {

    private Paciente paciente;
    private Paciente pacienteEncontrado;
    private Paciente pacienteSeleccionado;
    private List<Documento.TipoDocumento> listaTipoDocumento;
    //Lista de EstadoCivil
    private List<Paciente.EstadoCivilTipo> listaEstadoCivilTipo;
    //Lista de EstudiosTipo
    private List<Paciente.EstudiosTipo> listaEstudioTipo;
    //Lista de pacientes.
    private List<Paciente> pacientes;
    private String docBusqueda;
    //Fecha de nacimiento del paciente
    private Date fechaNacimiento;
    // Para conocer si estudia o no.
    private int estudia;
    // Para saber si tiene servicio de emergencia.
    private int servicio;
    //Atributos búsqueda simple.
    private String nroDocumentoFiltro;
    //Atributos búsqueda avanzada.
    private String edadDesdeFiltro;
    private String edadHastaFiltro;
    private String nombreFiltro;
    //
    private boolean estaDeshabilitado;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;

    public int getEstudia() {
        if (paciente != null && paciente.getTipoEstudios() != null) {
            estudia = 1;
        } else {
            estudia = 0;
        }
        return estudia;
    }

    public void setEstudia(int estudia) {
        this.estudia = estudia;
        if (estudia == 0) {
            paciente.setTipoEstudios(null);
        }
    }

    public int getServicio() {
        if (paciente != null && paciente.getServicioEmergencia() != null) {
            servicio = 1;
        } else {
            servicio = 0;
        }
        return servicio;
    }

    public void setServicio(int servicio) {

        this.servicio = servicio;
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

    public String getDocBusqueda() {
        return docBusqueda;
    }

    public void setDocBusqueda(String docBusqueda) {
        this.docBusqueda = docBusqueda;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPaciente(Paciente p) {
        this.paciente = p;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public List<Documento.TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(Documento.TipoDocumento.values());
        return listaTipoDocumento;
    }

    public List<Paciente.EstadoCivilTipo> getListaEstadoCivilTipo() {
        listaEstadoCivilTipo = Arrays.asList(Paciente.EstadoCivilTipo.values());
        return listaEstadoCivilTipo;
    }

    public List<Paciente.EstudiosTipo> getListaEstudioTipo() {
        listaEstudioTipo = Arrays.asList(Paciente.EstudiosTipo.values());
        return listaEstudioTipo;
    }

    public Paciente getPacienteEncontrado() {
        return pacienteEncontrado;
    }

    public void setPacienteEncontrado(Paciente pacienteEncontrado) {
        this.pacienteEncontrado = pacienteEncontrado;
    }

    public boolean isEstaDeshabilitado() {
        return estaDeshabilitado;
    }

    public void setEstaDeshabilitado(boolean estaDeshabilitado) {
        this.estaDeshabilitado = estaDeshabilitado;
    }

    public String save() {
        try {
            if (pacienteSeleccionado != null) {
                getPersonaService().save(paciente);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente " + paciente.toString()
                        + " actualizado correctamente."));
            } else {
                getPersonaService().save(paciente);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente " + paciente.toString()
                        + " guardado correctamente."));

            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El paciente " + paciente.toString() + " no fue cargado correctamente", null));
            System.out.println(ex.getMessage());
        }

        return "formPaciente";
    }

    @PostConstruct
    public void init() {

        if (paciente == null) {
            paciente = new Paciente();
            paciente.setDomicilio(new Domicilio());
            paciente.setDocumento(new Documento());
        }

        pacientes = new ArrayList<Paciente>();

    }

    // Métodos de la interfaz.
    public void buscarPacientes() {
        pacientes.clear();
        if ((getNroDocumentoFiltro() == null || getNroDocumentoFiltro().isEmpty()) && (getNombreFiltro() == null || getNombreFiltro().isEmpty())) {
//                buscarTodosLosPacientes();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se especifico ningun parametro de busqueda.", null));
        } else {
            busquedaSimple();
        }
    }

    //Métodos auxiliares.
    private void busquedaSimple() {
        Predicate p = null;
        String docFiltro;
        docFiltro = this.getNroDocumentoFiltro();
        String nomFiltro;
        nomFiltro = this.getNombreFiltro();
        if (docFiltro != null && docFiltro.isEmpty()==false) {
            buscarPorDocumento(p, getNroDocumentoFiltro());
        } else {
            if (nomFiltro != null && nomFiltro.isEmpty()== false) {
                buscarPorNombreYApellido(p, nomFiltro);
            }
        }
    }

    private FacesMessage buscarPorDocumento(Predicate p, String numDocFiltro) {
        
        p = PacienteSpecs.byNumeroDocumento(numDocFiltro);
        Paciente pac = (Paciente) getPersonaService().findOne(p);

        if (pac == null) {
            
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontraron pacientes con documento " + numDocFiltro, null);
        } else {
            pacientes.add(pac);
        }
        return null;
    }

    private FacesMessage buscarPorNombreYApellido(Predicate p, String nomFiltro) {
        //p = PacienteSpecs.byNombreOApellido(nomFiltro);
        pacientes = (List<Paciente>) personaService.findAll(PacienteSpecs.byNombreOApellido(nomFiltro).and(PersonaSpecs.byClass(Paciente.class)));

        if (pacientes == null || pacientes.isEmpty()) {
            
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encotraron pacientes con nombre " + nombreFiltro, null);
        }
        return null;
    }

    private void busquedaAvanzada() {
        pacientes.clear();

//        if (getEdadDesdeFiltro() != null && getEdadDesdeFiltro().length() > 0) {
//            // Busca pacientes que tengan como máximo cierta edad.
//            p = PacienteSpecs.byMayorA(convertirFechaDesde());
//        }
//        if (getEdadHastaFiltro() != null && getEdadHastaFiltro().length() > 0) {
//            p = PacienteSpecs.byMenorA(convertirFechaHasta()).and(p);
//        }

        pacientes = (List<Paciente>) personaService.findAll(PacienteSpecs.byNombreOApellido(nombreFiltro).and(PersonaSpecs.byClass(Paciente.class)));
        if (pacientes == null || pacientes.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontraron pacientes.", null));
        }
//
//        if (getNombreFiltro() != null && getNombreFiltro().length() > 0) {
//            p = PacienteSpecs.byNombre(nombreFiltro);
//        }
//
//        pacientes.addAll((Collection<? extends Paciente>) getPersonaService().findAll(p));
    }

//    private void buscarTodosLosPacientes() {
//        Predicate p = PersonaSpecs.byClass(Paciente.class);
//        pacientes = (List<Paciente>) getPersonaService().findAll(p);
//    }
//    private Calendar convertirFechaDesde() {
//        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
//        int anioDesde = anioActual - Utiles.convertStringToInt(getEdadDesdeFiltro()).intValue();
//
//        return Utiles.convertIntegerToCalendarYear(anioDesde);
//    }
//
//    private Calendar convertirFechaHasta() {
//        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
//        int anioHasta = anioActual - Utiles.convertStringToInt(getEdadHastaFiltro()).intValue();
//
//        return Utiles.convertIntegerToCalendarYear(anioHasta);
//    }
    /**
     * Creates a new instance of FormPacienteBean
     */
    public FormPacienteBean() {
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * @return the nroDocumentoFiltro
     */
    public String getNroDocumentoFiltro() {
        return nroDocumentoFiltro;
    }

    /**
     * @param nroDocumentoFiltro the nroDocumentoFiltro to set
     */
    public void setNroDocumentoFiltro(String nroDocumentoFiltro) {
        this.nroDocumentoFiltro = nroDocumentoFiltro;
    }

    /**
     * @return the edadDesdeFiltro
     */
    public String getEdadDesdeFiltro() {
        return edadDesdeFiltro;
    }

    /**
     * @param edadDesdeFiltro the edadDesdeFiltro to set
     */
    public void setEdadDesdeFiltro(String edadDesdeFiltro) {
        this.edadDesdeFiltro = edadDesdeFiltro;
    }

    /**
     * @return the edadHastaFiltro
     */
    public String getEdadHastaFiltro() {
        return edadHastaFiltro;
    }

    /**
     * @param edadHastaFiltro the edadHastaFiltro to set
     */
    public void setEdadHastaFiltro(String edadHastaFiltro) {
        this.edadHastaFiltro = edadHastaFiltro;
    }

    /**
     * @return the personaService
     */
    public PersonaService getPersonaService() {
        return personaService;
    }

    /**
     * @return the nombreFiltro
     */
    public String getNombreFiltro() {
        return nombreFiltro;
    }

    /**
     * @param nombreFiltro the nombreFiltro to set
     */
    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    /**
     * @return the pacienteSeleccionado
     */
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    /**
     * @param pacienteSeleccionado the pacienteSeleccionado to set
     */
    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    /**
     * Cambia la propiedad disabled a false para que los input queden
     * habilitados. En el btnModificar, la propiedad rendered queda en false y
     * no se va a mostrar.
     */
    public void habilitar() {
        estaDeshabilitado = false;
    }

    public void seleccionarPaciente() {
        if (pacienteSeleccionado == null) {
            return;
        }
        paciente = pacienteSeleccionado;
        estaDeshabilitado = true;
    }
}
