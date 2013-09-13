/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.paciente;

import tesis.odontologia.core.domain.Domicilio;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
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
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "La religion debe tener entre 1 y 50 caracteres.")
    private String religion;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "La nacionalidad debe tener entre 1 y 50 caracteres.")
    private String nacionalidad;
    
    @Column(length = 75)
    @Size(min = 1, max = 75, message = "La obra social debe tener entre 1 y 75 caracteres.")
    private String obraSocial;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El lugar de nacimiento debe tener entre 1 y 50 caracteres.")
    private String lugarNacimiento;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "La provincia debe tener entre 1 y 50 caracteres.")
    private String provincia;
    
    @Column
    @Enumerated(EnumType.STRING)
    private EstudiosTipo tipoEstudios;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El trabajo debe tener entre 1 y 50 caracteres.")
    private String trabajo;
    
    @Embedded
    private Domicilio domicilio;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El telefono debe tener entre 1 y 50 caracteres.")
    private String telefono;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El celular debe tener entre 1 y 50 caracteres.")
    private String celular;
    
    @Column(length = 100)
    @Size(min = 1, max = 100, message = "El medico de cabecera debe tener entre 1 y 100 caracteres.")
    private String medicoCabecera;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El telefono del medico debe tener entre 1 y 50 caracteres.")
    private String telefonoMedico;
    
    @Column(length = 50)
    @Size(min = 1, max = 50, message = "El servicio de emergencia debe tener entre 1 y 50 caracteres.")
    private String servicioEmergencia;
    
    @Column(name = "privadoLibertad")
    private boolean privadoLibertad;
    
    @Column(length = 75)
    @Size(min = 1, max = 75, message = "El lugar de privacion de libertad debe tener entre 1 y 75 caracteres.")
    private String lugar;
    
    @NotNull
    private boolean sexoFemenino;
    
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    @JoinColumn(name="historiaclinica_id")
    @NotNull(message = "La historia clinica del paciente no puede ser nula.")
    private HistoriaClinica historiaClinica;

    //CONSTRUCTORS
    public Paciente() {
    }

    public Paciente(String nombre, String apellido) {
        super(nombre, apellido);
    }

    //GETTERS AND SETTERS
    public boolean isSexoFemenino() {
        return sexoFemenino;
    }

    public void setSexoFemenino(boolean sexoFemenino) {
        this.sexoFemenino = sexoFemenino;
    }
    
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

    public boolean isPrivadoLibertad() {
        return privadoLibertad;
    }

    public void setPrivadoLibertad(boolean privadoLibertad) {
        this.privadoLibertad = privadoLibertad;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }
    
    public boolean hasEstudio(){
        return tipoEstudios != null;
    }
    

    
    //VALIDACIONES
    @Override
    public void validar() throws GenericException {
        super.validar();
    }
    

public enum EstudiosTipo {

    NINGUNO {
        @Override
        public String toString() {
            return "Ninguno";
        }
    },
    
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
