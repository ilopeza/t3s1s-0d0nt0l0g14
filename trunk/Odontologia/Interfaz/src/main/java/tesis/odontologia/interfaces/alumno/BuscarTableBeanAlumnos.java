/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.Documento;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "buscarTableBeanAlumnos")
@ApplicationScoped
public class BuscarTableBeanAlumnos {

    /**
     * @param aNombres the nombres to set
     */
    public static void setNombres(String[] aNombres) {
        nombres = aNombres;
    }

    /**
     * @param aApellidos the apellidos to set
     */
    public static void setApellidos(String[] aApellidos) {
        apellidos = aApellidos;
    }

    /**
     * @return the documentos
     */
    public static String[] getDocumentos() {
        return documentos;
    }

    /**
     * @param aDocumentos the documentos to set
     */
    public static void setDocumentos(String[] aDocumentos) {
        documentos = aDocumentos;
    }

    
    
    private Alumno alumno;
    private List<Alumno> alumnos;
    private Alumno alumnoSeleccionado;
    // private String varFiltrado;
    private String nombre;
    private String apellido;
    private static String[] nombres;
    private static String[] apellidos;
    private static String[] documentos;
    private String varBusqueda;

    
    /**
     * @return the nombres
     */
    public static String[] getNombres() {
        return nombres;
    }

    /**
     * @return the apellidos
     */
    public static String[] getApellidos() {
        return apellidos;
    }

    
    
    static {

        setNombres(new String[5]);
        setApellidos(new String[5]);
        setDocumentos(new String[5]);

        getNombres()[0] = "Florencia";
        getNombres()[1] = "Maria";
        getNombres()[2] = "Alejandra";
        getNombres()[3] = "Natalia";
        getNombres()[4] = "Erica";

        getApellidos()[0] = "Guzman";
        getApellidos()[1] = "Barrancas";
        getApellidos()[2] = "Zamolo";
        getApellidos()[3] = "Sanchez";
        getApellidos()[4] = "Trovero";
        
        getDocumentos()[0] = "35018118";
        getDocumentos()[1] = "35083184";
        getDocumentos()[2] = "34688417";
        getDocumentos()[3] = "34880696";
        getDocumentos()[4] = "12345678";

    }
    
    /**
     * Creates a new instance of BuscarTableBeanAlumnos
     */
    public BuscarTableBeanAlumnos() {
    }
    
    public void buscarAlumno() {
        for (Alumno a : alumnos) {
            if (a.getDocumento().getNumero().equals(getVarBusqueda())==true) {
                alumnoSeleccionado = a;
                break;
            }
        }
    }

    /**
     * @return the alumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * @return the alumnos
     */
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    /**
     * @param alumnos the alumnos to set
     */
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    /**
     * @return the alumnoSeleccionado
     */
    public Alumno getAlumnoSeleccionado() {
        return alumnoSeleccionado;
    }

    /**
     * @param alumnoSeleccionado the alumnoSeleccionado to set
     */
    public void setAlumnoSeleccionado(Alumno alumnoSeleccionado) {
        this.alumnoSeleccionado = alumnoSeleccionado;
    }

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
    
    
    public void cargarLista(List<Alumno> list, int size) {
        for (int i = 0; i < size; i++) {
            
            list.add(new Alumno(nombres[i], apellidos[i]));            
            list.get(i).setDocumento(new Documento());
            list.get(i).getDocumento().setNumero(documentos[i]);  
        }
    }

    @PostConstruct
    public void init() {
        alumnos = new ArrayList<Alumno>();
        cargarLista(alumnos, 5);
    }

    public String navigate() {
        return "asignarPaciente";
    }

    /**
     * @return the varBusqueda
     */
    public String getVarBusqueda() {
        return varBusqueda;
    }

    /**
     * @param varBusqueda the varBusqueda to set
     */
    public void setVarBusqueda(String varBusqueda) {
        this.varBusqueda = varBusqueda;
    }
}
