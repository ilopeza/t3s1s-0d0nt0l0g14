/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.usuario;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.interfaces.validacion.Validacion;

/**
 *
 * @author Enzo
 */
@ManagedBean(name = "registrarUsuarioBean")
@ViewScoped
public class RegistrarUsuarioBean {

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    @ManagedProperty(value = "#{rolService}")
    private RolService rolService;
    private List<Rol> roles;
    private Usuario usuario;
    private Rol rol;
    private String nombreUsuario;
    private String email;
    private String contraseña;
    private String repetirContraseña;

    @PostConstruct
    public void init() {
        buscarRoles();
    }

    public String registrarUsuario() {
        boolean validacion = false;
        Validacion validar = new Validacion();
        if (validar.nullEmpty(nombreUsuario)) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se ingresó un nombre de usuario.");
            validacion = true;
        }
        if (validar.nullEmpty(email)) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se ingresó un correo electrónico.");
            validacion = true;
        }

        if (rol == null) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se se seleccionó un rol.");
            validacion = true;
        }
        if (validar.nullEmpty(contraseña)) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "Debe ingresar una contraseña.");
            validacion = true;
        }
        if (!validar.nullEmpty(contraseña) && validar.nullEmpty(email)) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.");
            validacion = true;
        }
        if (!validar.nullEmpty(contraseña) && !validar.nullEmpty(repetirContraseña)) {
            if (contraseña.compareTo(repetirContraseña) != 0) {
                agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.");
                validacion = true;
            }
        }


        if (validacion) {
            return null;
        }
        
        usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setEmail(email);
        usuario.setRol(rol);
        usuario.setContraseña(contraseña);

        try {
            usuario = usuarioService.save(usuario);
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_INFO, "Se registró correctamente al usuario.");

        } catch (Exception e) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se pudo registrar el usuario.");
        }

        return null;
    }

    public void agregarMensajeAlUsuario(Severity severidad, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidad, mensaje, null));
    }

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * @return the roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    private void buscarRoles() {
        roles = rolService.findAll();
    }

    /**
     * @param rolService the rolService to set
     */
    public void setRolService(RolService rolService) {
        this.rolService = rolService;
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
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
