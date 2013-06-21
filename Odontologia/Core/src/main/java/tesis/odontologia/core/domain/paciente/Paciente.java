/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.paciente;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Persona {

    @Enumerated(EnumType.STRING)
    private EstadoCivilTipo estadoCivil;
    
    private String religion;
    
    private String nacionalidad;
    
    private String obraSocial;
    
    private String lugarNacimiento;
    
    private String provincia;
    
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private EstudiosTipo tipoEstudios;
    
    private String trabajo;
    
    @Embedded
    private Domicilio domicilio;
    
    private String telefono;
    
    private String celular;
    
    private String medicoCabecera;
    
    private String telefonoMedico;
    
    private String servicioEmergencia;
    
    private String privadoLibertad;
    
    private String lugar;

    //CONSTRUCTORS
    public Paciente() {
    }

    public Paciente(String nombre, String apellido) {
        super(nombre, apellido);
    }

    //GETTERS AND SETTERS
    public EstudiosTipo getTipoEstudios() {
        return tipoEstudios;
    }

    public void setTipoEstudios(EstudiosTipo tipoEstudios) {
        this.tipoEstudios = tipoEstudios;
    }

    public String getServicioEmergencia() {
        return servicioEmergencia;
    }

    public void setServicioEmergencia(String servicioEmergencia) {
        this.servicioEmergencia = servicioEmergencia;
    }

    public EstadoCivilTipo getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilTipo estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMedicoCabecera() {
        return medicoCabecera;
    }

    public void setMedicoCabecera(String medicoCabecera) {
        this.medicoCabecera = medicoCabecera;
    }

    public String getTelefonoMedico() {
        return telefonoMedico;
    }

    public void setTelefonoMedico(String telefonoMedico) {
        this.telefonoMedico = telefonoMedico;
    }

    public String getPrivadoLibertad() {
        return privadoLibertad;
    }

    public void setPrivadoLibertad(String privadoLibertad) {
        this.privadoLibertad = privadoLibertad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    
    //VALIDACIONES
    @Override
    public void validar() throws GenericException {
        super.validar();
    }
    

public enum EstudiosTipo {

    PRIMARIO {
        @Override
        public String toString() {
            return "Primario";
        }
    },
    SECUNDARIO {
        @Override
        public String toString() {
            return "Secundario";
        }
    },
    TERCIARIO {
        @Override
        public String toString() {
            return "Terciario";
        }
    },
    UNIVERSITARIO {
        @Override
        public String toString() {
            return "Universitario";
        }
    };
}

public enum EstadoCivilTipo {

    SOLTERO {
        @Override
        public String toString() {
            return "Soltero/a";
        }
    },
    CASADO {
        @Override
        public String toString() {
            return "Casado/a";
        }
    },
    DIVORCIADO {
        @Override
        public String toString() {
            return "Divorciado/a";
        }
    },
    VIUDO {
        @Override
        public String toString() {
            return "Viudo/a";
        }
    };
}
}
