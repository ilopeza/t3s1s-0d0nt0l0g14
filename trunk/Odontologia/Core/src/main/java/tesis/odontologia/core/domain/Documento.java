/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.exception.DocumentoException;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author Maxi
 */
@Embeddable
public class Documento implements Serializable {

    @Column(name = "numero", length = 15, nullable = false)
    @Size(min = 1, max = 15, message = "El numero de documento debe tener entre 1 y 15 caracteres.")
    @NotNull(message = "El numero de documento no puede ser nulo.")
    private String numero;
    
    @Column(name = "tipoDocumento", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de documento no puede ser nulo.")
    private TipoDocumento tipoDocumento;

    //CONSTRUCTORS
    public Documento() {
    }

    public Documento(String numero, TipoDocumento tipo) {
        this.numero = numero;
        this.tipoDocumento = tipo;
    }

    //GETTERS AND SETTERS
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    void validar() throws GenericException {
        if (numero == null) {
            throw new DocumentoException("El numero de documento no puede ser nulo.");
        }
        if (tipoDocumento == null) {
            throw new DocumentoException("El tipo de documento no puede ser nulo.");
        }
    }

    @Override
    public String toString() {
        return tipoDocumento.toString() + " " + numero;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 97 * hash + (this.tipoDocumento != null ? this.tipoDocumento.hashCode() : 0);
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
        final Documento other = (Documento) obj;
        if ((this.numero == null) ? (other.numero != null) : !this.numero.equals(other.numero)) {
            return false;
        }
        if (this.tipoDocumento != other.tipoDocumento) {
            return false;
        }
        return true;
    }
    
    public enum TipoDocumento {

        DNI {
            @Override
            public String toString() {
                return "Dni";
            }
        },
        PASAPORTE {
            @Override
            public String toString() {
                return "Pasaporte";
            }
        },
        LE {
            @Override
            public String toString() {
                return "Libreta de Enrolamiento";
            }
        },
        CI {
            @Override
            public String toString() {
                return "Cedula de Identidad";
            }
        },
        LC {
            @Override
            public String toString() {
                return "Libreta Civica";
            }
        };
    }
}
