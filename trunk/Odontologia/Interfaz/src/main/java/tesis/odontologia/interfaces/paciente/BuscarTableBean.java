/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import com.mysema.query.types.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.PacienteSpecs;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.interfaces.util.Utiles;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "buscarTableBean")
@ApplicationScoped
public class BuscarTableBean {

    private Paciente paciente;
    private List<Paciente> pacientesFiltrados;
    private List<Paciente> pacientes;
    private Paciente pacienteSeleccionado;
    // private String varFiltrado;
    private String nombre;
    private String apellido;
    private static String[] nombres;
    private static String[] apellidos;
    //Atributos para la búsqueda simple
    private String nroDocumentoFiltro;
    // Atributos usados para la búsqueda avanzada.
    private List<Materia> materias;
    private Materia materiaFiltro;
    private List<TrabajoPractico> practicos;
    private TrabajoPractico practicoFiltro;
    private String edadDesdeFiltro;
    private String edadHastaFiltro;
    private String nombreFiltro;
    //private List<Enfermedad> enfermedades;
    //private Enfermedad enfermedadFiltro;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;

    static {

        nombres = (new String[5]);
        apellidos = (new String[5]);


        nombres[0] = "Mauro";
        nombres[1] = "Maxi";
        nombres[2] = "Alexis";
        nombres[3] = "Nacho";
        nombres[4] = "Enzo";

        apellidos[0] = "Garcia";
        apellidos[1] = "Barros";
        apellidos[2] = "Spesot";
        apellidos[3] = "Arzuaga";
        apellidos[4] = "Biancato";


    }
    
    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

//    public void cargarLista(List<Paciente> list, int size) {
//        for (int i = 0; i < size; i++) {
//            list.add(new Paciente(nombres[i], apellidos[i]));
//        }
//    }

    @PostConstruct
    public void init() {
        pacientes = new ArrayList<Paciente>();
//        //cargarLista(pacientes, 5);
        materias = new ArrayList();
        materias.addAll(cargarMaterias());

        practicos = new ArrayList();
        practicos.addAll(cargarPracticos());
    }

    public String navigate() {
        return "asignarPaciente";
    }

    public BuscarTableBean() {
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the pacientes
     */
    public List<Paciente> getPacientes() {
        Predicate p = null;
        p = PersonaSpecs.byClass(Paciente.class);
        pacientes = (List<Paciente>)personaService.findAll(p);
        return pacientes;
    }

    /**
     * @param pacientes the pacientes to set
     */
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    /**
     * @return the pacientesFiltrados
     */
    public List<Paciente> getPacientesFiltrados() {
        return pacientesFiltrados;
    }

    /**
     * @param pacientesFiltrados the pacientesFiltrados to set
     */
    public void setPacientesFiltrados(List<Paciente> pacientesFiltrados) {
        this.pacientesFiltrados = pacientesFiltrados;
    }

//    public void filtrar(String globalFilter) {
//        //pacientes = new ArrayList<Paciente>();        
//        //cargarLista(pacientes,5);
//        for (int i = 0; i < 5; i++) {
//            if (pacientes.get(i).getNombre().contains(globalFilter)) {
//                pacientesFiltrados.add(new Paciente(nombres[i], apellidos[i]));
//            }
//        }
//    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    //Métodos getter y setter para la búsqueda simple.
    public String getNroDocumentoFiltro() {
        return nroDocumentoFiltro;
    }

    public void setNroDocumentoFiltro(String nroDocumentoFiltro) {
        this.nroDocumentoFiltro = nroDocumentoFiltro;
    }

    //Métodos getter y setter para la búsqueda avanzada.
    public Materia getMateriaFiltro() {
        return materiaFiltro;
    }

    public void setMateriaFiltro(Materia materiaFiltro) {
        this.materiaFiltro = materiaFiltro;
    }

    public List<TrabajoPractico> getPracticos() {
        return practicos;
    }

    public void setPracticos(List<TrabajoPractico> practicos) {
        this.practicos = practicos;
    }

    public TrabajoPractico getPracticoFiltro() {
        return practicoFiltro;
    }

    public void setPracticoFiltro(TrabajoPractico practicoFiltro) {
        this.practicoFiltro = practicoFiltro;
    }

    public String getEdadDesdeFiltro() {
        return edadDesdeFiltro;
    }

    public void setEdadDesdeFiltro(String edadDesdeFiltro) {
        this.edadDesdeFiltro = edadDesdeFiltro;
    }

    public String getEdadHastaFiltro() {
        return edadHastaFiltro;
    }

    public void setEdadHastaFiltro(String edadHastaFiltro) {
        this.edadHastaFiltro = edadHastaFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }


    public String buscarPacientes() {
        pacientes.clear();
        if (nroDocumentoFiltro != null && nroDocumentoFiltro.length() > 0) {
            pacientes.add(busquedaSimple());
        } else {
            pacientes.addAll(busquedaAvanzada());
        }
        return "buscarPaciente";
    }

    // Métodos auxiliares.
    private Collection<? extends Paciente> busquedaAvanzada() {
        Predicate p = null;
        if (edadDesdeFiltro == null) {
            // Busca pacientes que tengan como máximo cierta edad.
            p = PacienteSpecs.byMenorA(convertirFechaHasta());
        } else {
            if (edadHastaFiltro == null) {
                //Busca pacientes que hayan nacido a partir de cierto año.
                p = PacienteSpecs.byMayorA(convertirFechaDesde());
            } else {
                p = PacienteSpecs.byEdadEntre(convertirFechaDesde(), convertirFechaHasta());
            }
        }
        if (!materiaFiltro.getNombre().equalsIgnoreCase("Ninguno")) {
            p = PacienteSpecs.byDiagnosticoMateria(materiaFiltro).and(p);
        }

        pacientesFiltrados = (List<Paciente>) personaService.findAll(p);

        return pacientesFiltrados;
    }

    private Paciente busquedaSimple() {
        return personaService.findOne(PacienteSpecs.byNumeroDocumento(nroDocumentoFiltro));
    }

    private Calendar convertirFechaDesde() {
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioDesde = anioActual - Utiles.convertStringToInt(edadDesdeFiltro).intValue();

        return Utiles.convertIntegerToCalendarYear(anioDesde);
    }

    private Calendar convertirFechaHasta() {
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioHasta = anioActual - Utiles.convertStringToInt(edadHastaFiltro).intValue();

        return Utiles.convertIntegerToCalendarYear(anioHasta);
    }

    public List<Materia> cargarMaterias() {
        List<Materia> lista = new ArrayList<Materia>();

        Materia m1 = new Materia();
        Materia m2 = new Materia();
        Materia m3 = new Materia();
        Materia m= new Materia();
        
        m.setNombre("Ninguno");
        m1.setNombre("Prostoconcia");
        m2.setNombre("Endodoncia");
        m3.setNombre("Ortodoncia");

        lista.add(m);
        lista.add(m1);
        lista.add(m2);
        lista.add(m3);

        return lista;
    }

    public List<TrabajoPractico> cargarPracticos() {

        List<TrabajoPractico> lista = new ArrayList<TrabajoPractico>();

//        TrabajoPractico tp1 = new TrabajoPractico("TP1");
//        TrabajoPractico tp2 = new TrabajoPractico("TP2");
//        TrabajoPractico tp3 = new TrabajoPractico("TP3");

//        lista.add(tp1);
//        lista.add(tp2);
//        lista.add(tp3);

        return lista;
    }
}
