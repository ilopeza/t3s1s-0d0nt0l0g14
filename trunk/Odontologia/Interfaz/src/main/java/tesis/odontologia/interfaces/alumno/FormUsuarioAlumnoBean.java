/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;


import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.mail.SMTPConfig;



/**
 *
 * @author alespe
 */
@ManagedBean(name = "formUsuarioAlumnoBean")
@ViewScoped

public class FormUsuarioAlumnoBean {

    private Usuario usuario;
    
    
    private String datosUsuarioAlumnoRegistrado;

    public FormUsuarioAlumnoBean() {
    }


    public void registrarUsuarioAlumno() {
        String password = "pass"; //se genera la clave
        Rol rol = new Rol("Alumno"); //todos los roles deberian estar generados.
        usuario.setNombreUsuario(usuario.getNombreUsuario()); //se asigna el numero de doc como nombre de usuario.
        usuario.setContraseña(password); //se setea la contraseña generada.
        usuario.setRol(rol);
        datosUsuarioAlumnoRegistrado = "Se registró el usuario del alumno con los siguientes datos:" + "\nUsuario: " + usuario.getNombreUsuario() + "\nContraseña: " + usuario.getContraseña();
        SMTPConfig.sendMail("Registro en SAPO", "Te has registrado en el sistema SAPO, que te permite buscar pacientes para tus practicas. Tus datos de inicio de sesión son los siguientes: Usuario, Contraseña. Hace click en el siguiente enlace para completar tu registro.", usuario.getEmail());
    }
    
    public String datosUsuarioRegistrado(){
        
      return datosUsuarioAlumnoRegistrado;
    }
    
   

    @PostConstruct
    public void init() {
        
            usuario = new Usuario();
    
    }
    
    
    
    public void savePaciente() {
        //AlumnoService.create(nuevoAlumno);
    }

    public void updatePaciente() {
        //AlumnoService.update(nuevoAlumno);
    }

    public void deletePaciente() {
        //AlumnoService.delete(nuevoAlumno);
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
}
