/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.login;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.core.specification.UsuarioSpecs;

/**
 *
 * @author Maxi
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    private String username;
    private String password;
    private Usuario usuario;
    private Persona persona;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        httpSession.invalidate();
        return "login";
    }

    public String login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        boolean loggedIn;
        String resp = "index";

        usuario = usuarioService.findOne(UsuarioSpecs.byUsuario(username).and(UsuarioSpecs.byPassword(password)));
        if (usuario == null) {
            resp = null;
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nombre de usuario o contraseña invalida.");
        } else {
            persona = personaService.findOne(PersonaSpecs.byUsuario(usuario));
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario.getNombreUsuario());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return resp;
    }
}
