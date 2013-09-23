/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.usuario;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.mail.SMTPConfig;
import tesis.odontologia.core.service.UsuarioService;
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
    private String inputText;
    
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
        
        if (inputText.matches("[0-9]*")) {
            usuario = usuarioService.findOne(UsuarioSpecs.byUsuario(inputText));
        } else {
            usuario = usuarioService.findOne(UsuarioSpecs.byEmail(inputText));
        }
        
        if (usuario != null) {
            String cuerpoMail = "Has solicitado que tu contraseña de inicio de sesión en el sistema SAPO sea restaurada. Tus datos son los siguientes: \n Nombre de usuario: " + usuario.getNombreUsuario() + "\n Contraseña: " + usuario.getContraseña();
            try {
                SMTPConfig.sendMail(Boolean.TRUE, "Recuperación de contraseña", cuerpoMail, usuario.getEmail());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, usuario.getNombreUsuario() + ", se han enviado tus datos de inicio de sesión a " + usuario.getEmail(), null));
            } catch (MessagingException ex) {
                Logger.getLogger(RecuperarPassUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha encontrado el usuario", null));
        }
    }

    /**
     * @return the inputText
     */
    public String getInputText() {
        return inputText;
    }

    /**
     * @param inputText the inputText to set
     */
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    
    
}
