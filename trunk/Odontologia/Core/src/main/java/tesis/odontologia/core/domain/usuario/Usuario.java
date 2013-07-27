/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.usuario;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Bajeable;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.UsuarioException;

/**
 *
 * @author alespe
 */
@Entity

public class Usuario extends Bajeable{
    @Column (length = 50, nullable = false)
    @Size(min = 1, max = 50)
    @NotNull
    private String nombreUsuario;
    
    @Column (length = 50, nullable = false)
    @Size(min = 1, max = 50)
    @NotNull
    private String contraseña;
    
    @JoinColumn(name="rol_id")
    @ManyToOne
    @NotNull
    private Rol rol;
    
    @Column(length = 75, nullable = false)
    @Size(min = 1, max = 75)
    @NotNull
    private String email;

    public Usuario() {
    }

    public Usuario(String usuario, String contraseña, Rol rol, String email) {
        this.nombreUsuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String usuario) {
        this.nombreUsuario = usuario;
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
    

    @Override
    public void validar() throws GenericException {
       if(nombreUsuario ==null || nombreUsuario.isEmpty()){
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
