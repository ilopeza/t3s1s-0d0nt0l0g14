/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.usuario;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.mail.SMTPConfig;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.core.specification.RolSpecs;
import tesis.odontologia.core.specification.UsuarioSpecs;

/**
 *
 * @author Enzo
 */
@ManagedBean(name = "recuperarPassUsuarioBean")
@ViewScoped
public class RecuperarPassUsuarioBean {
    
    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    private Usuario usuario;
    
    public RecuperarPassUsuarioBean() {
        usuario = new Usuario();
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
    
    public void recuperarPass() {
        
        if(usuarioService.count(UsuarioSpecs.byNombreOrEmail(usuario.getNombreUsuario())) == 1 || usuarioService.count(UsuarioSpecs.byNombreOrEmail(usuario.getEmail())) == 1)
        {
            if (usuario.getNombreUsuario().isEmpty()) {
                usuario = usuarioService.findOne(UsuarioSpecs.byNombreOrEmail(usuario.getEmail()));
            } else {
                usuario = usuarioService.findOne(UsuarioSpecs.byNombreOrEmail(usuario.getNombreUsuario()));
            }
            String cuerpoMail = "Has solicitado que tu contraseña de inicio de sesión en el sistema SAPO sea restaurada. Tus datos son los siguientes: \n Nombre de usuario: " + usuario.getNombreUsuario() + "\n Contraseña: " + usuario.getContraseña();
            try {
                SMTPConfig.sendMail(Boolean.TRUE, "Recuperación de contraseña", cuerpoMail, usuario.getEmail());
            } catch (MessagingException ex) {
                Logger.getLogger(RecuperarPassUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        } else
        {
            
        }
    }
    
    
}
