/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author Maxi
 */
@ManagedBean
@SessionScoped
public class MenuBean {

    /**
     * Creates a new instance of MenuBean
     */
    private MenuModel menu = new DefaultMenuModel();
    private Rol rol;

    public MenuBean() {
        buildMenu();
    }

    public void buildMenu() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");

        rol = login.getUsuario().getRol();
        if (rol.is(Rol.ALUMNO) || rol.is(Rol.PACIENTE) || rol.is(Rol.PROFESOR) || rol.is(Rol.RESPONSABLE) || rol.is(Rol.ADMINACADEMICO)) {
            if (rol == null) {
                return;
            }
        }
        Submenu submenu = null;
        MenuItem menuItem = null;
        //Menu
        if (rol.is(Rol.ALUMNO) || rol.is(Rol.RESPONSABLE)) {
            submenu = subMenu("Gestión de Pacientes");
        }
        //Opciones
        if (rol.is(Rol.ALUMNO) || rol.is(Rol.RESPONSABLE)) {
            menuItem = menuItem("Registrar Paciente", "/pages/wizardPacientes/wizardPaciente.xhtml");
            submenu.getChildren().add(menuItem);
            menu.addSubmenu(submenu);
        }

        //Menu
        if (rol.is(Rol.ADMINACADEMICO)) {

            submenu = subMenu("Gestión de Materias");
            //Opciones
            menuItem = menuItem("Registrar Materia", "/pages/materias/formMaterias.xhtml");
            submenu.getChildren().add(menuItem);

            menu.addSubmenu(submenu);


        }

//        if (rol.is(Rol.ALUMNO)) {
//        //Menu
//        submenu = subMenu("Gestión de Alumnos");
//        //Opciones
//        menuItem = menuItem("Registrar Alumno", "/pages/alumnos/formAlumno.xhtml");
//        submenu.getChildren().add(menuItem);
//        menu.addSubmenu(submenu);
//        }

        if (rol.is(Rol.RESPONSABLE)) {
            //Menu
            submenu = subMenu("Gestión de Usuarios");
            //Opciones
            menuItem = menuItem("Registrar Usuario de Alumno", "/pages/alumnos/formUsuarioAlumno.xhtml");
            submenu.getChildren().add(menuItem);

            menuItem = menuItem("Registrar Usuario", "/pages/usuario/registrarUsuario.xhtml");
            submenu.getChildren().add(menuItem);
            
            menu.addSubmenu(submenu);

            submenu = subMenu("Gestión de Profesores");
            //Opciones
            menuItem = menuItem("Consultar profesores", "/pages/profesores/CABMProfesor.xhtml");
            submenu.getChildren().add(menuItem);

            menu.addSubmenu(submenu);
        }


        if (rol.is(Rol.ALUMNO) || rol.is(Rol.PROFESOR) || rol.is(Rol.RESPONSABLE)) {
            //Menu
            submenu = subMenu("Asignaciones de Pacientes");
            //Opciones
            menuItem = menuItem("Asignacion Paciente", "/pages/asignaciones/registrarAsignacionPaciente.xhtml");
            submenu.getChildren().add(menuItem);
            menuItem = menuItem("Consultar Asignacion Paciente", "/pages/asignaciones/consultarAsignacionesPaciente.xhtml");
            submenu.getChildren().add(menuItem);


            if (rol.is(Rol.PROFESOR)) {
                menuItem = menuItem("Consultar Asignaciones Confirmadas", "/pages/asignaciones/consultarAsignacionesConfirmadas.xhtml");
                submenu.getChildren().add(menuItem);
                menuItem = menuItem("Consultar Atenciones Realizadas", "/pages/historiaClinica/consultarAtencionesRealizadas.xhtml");
                submenu.getChildren().add(menuItem);
                menuItem = menuItem("Consultar Estadisticas", "/pages/estadisticas/consultarEstadisticas.xhtml");
                submenu.getChildren().add(menuItem);
            }
            menu.addSubmenu(submenu);
        }

        if (rol.is(Rol.PROFESOR)) {
            //Menu
            submenu = subMenu("Trabajos Prácticos");
            menuItem = menuItem("Nuevo Trabajo Práctico", "/pages/trabajosPracticos/registrarTrabajoPractico.xhtml");
            submenu.getChildren().add(menuItem);

            menu.addSubmenu(submenu);
        }

        if (rol.is(Rol.ALUMNO) || rol.is(Rol.PROFESOR) || rol.is(Rol.RESPONSABLE)) {
            //Menu
            submenu = subMenu("Cuenta");
            //Opciones
            menuItem = menuItem("Datos Alumno", "/pages/alumnos/formAlumno.xhtml");
            submenu.getChildren().add(menuItem);
            menuItem = menuItem("Recuperar contraseña", "/pages/usuario/recuperarPassword.xhtml");
            submenu.getChildren().add(menuItem);
            menuItem = menuItem("Modificar mis datos", "/pages/usuario/modificarDatosUsuario.xhtml");
            submenu.getChildren().add(menuItem);
//        menuItem = menuItem("Caducar sesión", "/pages/usuario/caducarSesion.xhtml");
//        submenu.getChildren().add(menuItem);
//        menuItem = menuItem("Iniciar sesión", "/pages/usuario/iniciarSesion.xhtml");
//        submenu.getChildren().add(menuItem);
            menu.addSubmenu(submenu);
        }
    }

    private Submenu subMenu(String label) {
        Submenu submenu = new Submenu();
        submenu.setLabel(label);
        return submenu;
    }

    private MenuItem menuItem(String value, String url) {
        MenuItem menuItem = new MenuItem();
        menuItem.setValue(value);
        menuItem.setUrl(url);
        return menuItem;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public MenuModel getMenu() {
        return menu;
    }

    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }
}
