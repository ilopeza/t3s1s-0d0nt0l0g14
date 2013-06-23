/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.paciente;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlDataTable;
import tesis.odontologia.core.domain.paciente.Paciente;

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

    
    public void cargarLista(List<Paciente> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Paciente(nombres[i], apellidos[i]));
        }
    }

    @PostConstruct
    public void init() {
        pacientes = new ArrayList<Paciente>();
        cargarLista(pacientes, 5);
    }

    public String navigate() {
        return "asignarPaciente";
    }

    public BuscarTableBean() {
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
}
