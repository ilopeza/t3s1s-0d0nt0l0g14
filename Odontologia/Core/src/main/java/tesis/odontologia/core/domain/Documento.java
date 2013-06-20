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
import tesis.odontologia.core.exception.DocumentoException;

/**
 *
 * @author Maxi
 */
@Embeddable
public class Documento implements Serializable {

    @Column(nullable = false)
    private Integer numero;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    //CONSTRUCTORS
    public Documento() {
    }

    public Documento(Integer numero, TipoDocumento tipo) {
        this.numero = numero;
        this.tipoDocumento = tipo;
    }

    //GETTERS AND SETTERS
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    void validar() throws Exception {
        if (numero == null) {
            throw new DocumentoException("El numero de documento no puede ser nulo.");
        }
        if (tipoDocumento == null) {
            throw new DocumentoException("El tipo de documento no puede ser nulo.");
        }
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
