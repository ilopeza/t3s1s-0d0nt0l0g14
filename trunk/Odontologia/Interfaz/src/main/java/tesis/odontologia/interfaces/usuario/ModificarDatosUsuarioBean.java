/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.util.SerializationUtils;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.PersonaSpecs;
import tesis.odontologia.core.utils.FechaUtils;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author Enzo
 */
@ManagedBean(name = "modificarDatosUsuarioBean")
@ViewScoped
public class ModificarDatosUsuarioBean {

    private Persona persona;
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    private String contraseñaActual;
    private String nuevaContraseña;
    private String repetirContraseña;
    private LoginBean login;
    private String destination;
    private String ubicacionImagenUsuario;
    private boolean mostrarUploader;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        login = (LoginBean) session.getAttribute("loginBean");

        persona = personaService.findOne(PersonaSpecs.byId(login.getPersona().getId()));
        persona = personaService.reload(persona, 1);
        obtenerUbicacionImagenUsuario();
        mostrarUploader = false;

    }

    public void cambiarImagen() {
        mostrarUploader = true;
    }

    public void listoImagen() {
        mostrarUploader = false;
    }

    public void obtenerUbicacionImagenUsuario() {
        if (persona.getUsuario().getUbicacionImagen() == null || persona.getUsuario().getUbicacionImagen().isEmpty()) {
            ubicacionImagenUsuario = "../../resources/images/NOIMAGE.jpg";
        } else {
            ubicacionImagenUsuario = "../../" +persona.getUsuario().getUbicacionImagen().substring(persona.getUsuario().getUbicacionImagen().lastIndexOf("resources"));
        }

    }

    public String guardarCambios() {
        boolean error = false;
        if ((contraseñaActual != null && !contraseñaActual.isEmpty()) && (nuevaContraseña == null || nuevaContraseña.isEmpty())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puede asignarse una contraseña vacía.", null));
            error = true;
        }
        if ((nuevaContraseña != null && !nuevaContraseña.isEmpty()) && (contraseñaActual == null || contraseñaActual.isEmpty())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar su contraseña actual.", null));
            error = true;
        }
        if ((contraseñaActual != null && !contraseñaActual.isEmpty()) && !persona.getUsuario().getContraseña().equals(contraseñaActual)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña actual no es correcta.", null));
            error = true;
        }
        if ((nuevaContraseña != null && !nuevaContraseña.isEmpty()) && (!nuevaContraseña.equals(repetirContraseña)) || ((nuevaContraseña != null && !nuevaContraseña.isEmpty()) && (repetirContraseña == null || repetirContraseña.isEmpty()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los campos 'Nueva contraseña' y 'Confirmar contraseña' deben coincidir.", null));
            error = true;
        }
        if (error) {
            return null;
        }

        try {
            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                persona.getUsuario().setContraseña(nuevaContraseña);
            }
            persona = personaService.save(persona);
            login.setPersona(persona);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados correctamente", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha podido actualizar los datos.", null));
        }

        return "index";
    }

    public String volverAInicio() {
        return "index";
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            destination = servletContext.getRealPath("").substring(0, servletContext.getRealPath("").lastIndexOf("target"))
                    + "src" + File.separator + "main" + File.separator + "webapp" + File.separator
                    + "resources" + File.separator + "images" + File.separator + "ImagenesUsuarios" + File.separator;
            String nombre = "image_" + persona.getUsuario().getNombreUsuario() + persona.getUsuario().getVersion() + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf('.'));
            if (persona.getUsuario().getUbicacionImagen() != null && !persona.getUsuario().getUbicacionImagen().isEmpty()) {
                File file = new File(persona.getUsuario().getUbicacionImagen());
                file.delete();
            }
            if (copyFile(nombre, event.getFile().getInputstream())) {
                persona.getUsuario().setUbicacionImagen(destination + nombre);
                persona = personaService.save(persona);
                login.setPersona(persona);
                obtenerUbicacionImagenUsuario();
                mostrarUploader = false;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String formatFecha(Calendar c) {
        return FechaUtils.fechaMaskFormat(c, "dd/MM/yyyy HH:mm");
    }

    /**
     * @return the alumno
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * @return the contraseñaActual
     */
    public String getContraseñaActual() {
        return contraseñaActual;
    }

    /**
     * @param contraseñaActual the contraseñaActual to set
     */
    public void setContraseñaActual(String contraseñaActual) {
        this.contraseñaActual = contraseñaActual;
    }

    /**
     * @return the nuevaContraseña
     */
    public String getNuevaContraseña() {
        return nuevaContraseña;
    }

    /**
     * @param nuevaContraseña the nuevaContraseña to set
     */
    public void setNuevaContraseña(String nuevaContraseña) {
        this.nuevaContraseña = nuevaContraseña;
    }

    /**
     * @return the repetirContraseña
     */
    public String getRepetirContraseña() {
        return repetirContraseña;
    }

    /**
     * @param repetirContraseña the repetirContraseña to set
     */
    public void setRepetirContraseña(String repetirContraseña) {
        this.repetirContraseña = repetirContraseña;
    }

    /**
     * @param personaService the personaService to set
     */
    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the ubicacionImagenUsuario
     */
    public String getUbicacionImagenUsuario() {
        return ubicacionImagenUsuario;
    }

    /**
     * @param ubicacionImagenUsuario the ubicacionImagenUsuario to set
     */
    public void setUbicacionImagenUsuario(String ubicacionImagenUsuario) {
        this.ubicacionImagenUsuario = ubicacionImagenUsuario;
    }

    /**
     * @return the mostrarUploader
     */
    public boolean isMostrarUploader() {
        return mostrarUploader;
    }

    /**
     * @param mostrarUploader the mostrarUploader to set
     */
    public void setMostrarUploader(boolean mostrarUploader) {
        this.mostrarUploader = mostrarUploader;
    }
}
