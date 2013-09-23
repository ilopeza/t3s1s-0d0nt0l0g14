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
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login =(LoginBean) session.getAttribute("loginBean");
        
        usuario = login.getUsuario();
        persona = login.getPersona();
        
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
    
}
