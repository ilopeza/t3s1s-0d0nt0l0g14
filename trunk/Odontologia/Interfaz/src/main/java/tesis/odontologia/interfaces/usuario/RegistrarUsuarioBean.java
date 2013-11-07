/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.usuario;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.service.UsuarioService;

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
    
    @PostConstruct
    public void init() {
        buscarRoles();
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
    
}
