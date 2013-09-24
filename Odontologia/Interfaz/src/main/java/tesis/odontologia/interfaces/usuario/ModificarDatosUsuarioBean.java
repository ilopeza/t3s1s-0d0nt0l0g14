/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.usuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author Enzo
 */
@ManagedBean(name = "modificarDatosUsuarioBean")
@ViewScoped
public class ModificarDatosUsuarioBean {
    
    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    private Usuario usuario;
    private Persona persona;
    private String contraseñaActual;
    private String nuevaContraseña;
    private String repetirContraseña;
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login =(LoginBean) session.getAttribute("loginBean");
        
        usuario = login.getUsuario();
        persona = login.getPersona();
        
    }
    
    
    /**
     * Método que se utiliza para cambiar la contraseña actual por otra.
     */
    public String cambiarContraseña()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login =(LoginBean) session.getAttribute("loginBean");

        if(contraseñaActual.compareTo(login.getPassword().toString())==0)
        {
            if(getNuevaContraseña().compareTo(getRepetirContraseña())==0)
            {
                usuario=login.getUsuario();
                usuario.setContraseña(getNuevaContraseña());
                getUsuarioService().save(usuario);
            }
        }
        return "login";
    }

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the alumno
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * @return the usuarioService
     */
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    /**
     * @return the contraseñaActual
     */
    public String getContraseñaActual() {
        return contraseñaActual;
    }

    /**
     * @param contraseñaActual the contraseñaActual to set
     */
    public void setContraseñaActual(String contraseñaActual) {
        this.contraseñaActual = contraseñaActual;
    }

    /**
     * @return the nuevaContraseña
     */
    public String getNuevaContraseña() {
        return nuevaContraseña;
    }

    /**
     * @param nuevaContraseña the nuevaContraseña to set
     */
    public void setNuevaContraseña(String nuevaContraseña) {
        this.nuevaContraseña = nuevaContraseña;
    }

    /**
     * @return the repetirContraseña
     */
    public String getRepetirContraseña() {
        return repetirContraseña;
    }

    /**
     * @param repetirContraseña the repetirContraseña to set
     */
    public void setRepetirContraseña(String repetirContraseña) {
        this.repetirContraseña = repetirContraseña;
    }
    
    
    
}
