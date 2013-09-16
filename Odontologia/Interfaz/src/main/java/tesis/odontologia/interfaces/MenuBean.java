/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;


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
    private MenuModel menu = new DefaultMenuModel();;
    
    public MenuBean() {
        Submenu submenu;
        MenuItem menuItem;
        //Menu
        submenu = subMenu("Gestión de Pacientes");
        //Opciones
        menuItem = menuItem("Registrar Paciente","/pages/pacientes/formPaciente.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
        
        //Menu
        submenu = subMenu("Gestión de Materias");
        //Opciones
        menuItem = menuItem("Registrar Materia","/pages/materias/formMaterias.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
        
        //Menu
        submenu = subMenu("Gestión de Alumnos");
        //Opciones
        menuItem = menuItem("Registrar Alumno","/pages/alumnos/formAlumno.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
        
        //Menu
        submenu = subMenu("Gestión de Usuarios");
        //Opciones
        menuItem = menuItem("Registrar Usuario de Alumno","/pages/alumnos/formUsuarioAlumno.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
        
        //Menu
        submenu = subMenu("Asignaciones de Pacientes");
        //Opciones
        menuItem = menuItem("Asignacion Paciente","/pages/asignaciones/asignacionPaciente.xhtml");
        submenu.getChildren().add(menuItem);
        menuItem = menuItem("Consultar Asignacion Paciente","/pages/asignaciones/consultarAsignacionesPaciente.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
        menuItem = menuItem("Consultar Asignaciones Confirmadas","/pages/asignaciones/consultarAsignacionesConfirmadas.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
        //Menu
        submenu = subMenu("Cuenta");
        //Opciones
        menuItem = menuItem("Recuperar contraseña","/pages/usuario/recuperarPassword.xhtml");
        submenu.getChildren().add(menuItem);
        menuItem = menuItem("Caducar sesión","/pages/usuario/caducarSesion.xhtml");
        submenu.getChildren().add(menuItem);
        menuItem = menuItem("Iniciar sesión","/pages/usuario/iniciarSesion.xhtml");
        submenu.getChildren().add(menuItem);
        menu.addSubmenu(submenu);
    }

    public MenuModel getMenu() {
        return menu;
    }

    public void setMenu(MenuModel menu) {
        this.menu = menu;
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
    
}
