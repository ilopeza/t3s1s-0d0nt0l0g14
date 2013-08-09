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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"numero", "tipoDocumento"})})
public abstract class Persona extends Generic {

    @Column(length = 150)
    @Size(min = 1, max = 150, message = "El apellido debe tener entre 1 y 150 caracteres.")
    @NotNull(message = "El apellido no puede ser nulo.")
    private String apellido;
    
    @Column(length = 150)
    @Size(min = 1, max = 150, message = "El nombre debe tener entre 1 y 150 caracteres")
    @NotNull(message = "El nombre no puede ser nulo.")
    private String nombre;
    
    @Embedded
    private Documento documento;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message = "La fecha de nacimiento no puede ser nula.")
    private Calendar fechaNacimiento;
    
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
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
        return apellido + ", " + nombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.documento != null ? this.documento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persona other = (Persona) obj;
        if (this.documento != other.documento && (this.documento == null || !this.documento.equals(other.documento))) {
            return false;
        }
        return true;
    }
    

    //VALIDAR
    @Override
    public void validar() throws GenericException {
        if (documento == null) {
            throw new PersonaException("El documento no puede ser nulo.");
        }
        documento.validar();
    }

    /*
     * Calcula y devuelve un int con la edad de la persona.
     */
    public int getEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Calendar.getInstance().get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
    }
}
