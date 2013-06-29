/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.exception.GenericException;
import tesis.odontologia.core.exception.PersonaException;

/**
 *
 * @author Maxi
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Persona extends Generic {

    @Column(length = 50, nullable = false)
    private String apellido;
    
    @Column(length = 50, nullable = false)
    private String nombre;
    
    @Embedded
    private Documento documento;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar fechaNacimiento;
    
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name= "usuario_id")
    private Usuario usuario;

    //CONSTRUCTORS
    public Persona() {
    }

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    //GETTERS AND SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return apellido + ", "+ nombre;
    }

    //VALIDAR
    @Override
    public void validar() throws GenericException {
        if (documento == null) {
            throw new PersonaException("El documento no puede ser nulo.");
        }
        documento.validar();
    }
}
