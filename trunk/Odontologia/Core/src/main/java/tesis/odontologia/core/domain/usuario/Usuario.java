/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import tesis.odontologia.core.domain.Bajeable;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.UsuarioException;

/**
 *
 * @author alespe
 */
@Entity

public class Usuario extends Bajeable{
    
    private String usuario;
    private String contraseña;
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="rol_id")
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String usuario, String contraseña, Rol rol) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public void validar() throws GenericException {
       if(usuario ==null || usuario.isEmpty()){
           throw new UsuarioException("El nombre de usuario no puede ser nulo o vacio");
       }
       
       if(contraseña ==null || contraseña.isEmpty()){
           throw new UsuarioException("La contraseña del usuario no puede ser nula o vacia");
       }
              
       if(rol ==null){
           throw new UsuarioException("El rol del usuario no puede ser nulo");
       }
       rol.validar();
    }
    
    
    
    
    
}
