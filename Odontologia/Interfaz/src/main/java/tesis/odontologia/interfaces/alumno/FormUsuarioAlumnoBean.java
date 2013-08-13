/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.mail.SMTPConfig;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.core.specification.RolSpecs;

/**
 *
 * @author alespe
 */
@ManagedBean(name = "formUsuarioAlumnoBean")
@ViewScoped
public class FormUsuarioAlumnoBean {

    private Usuario usuario;
    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    private String datosUsuarioAlumnoRegistrado;
    @ManagedProperty(value = "#{rolService}")
    private RolService rolService;

    public FormUsuarioAlumnoBean() {
    }

//    public void registrarUsuarioAlumno() {
//        String password = "pass"; //se genera la clave
//        Rol rol = new Rol("Alumno"); //todos los roles deberian estar generados.
//        usuario.setNombreUsuario(usuario.getNombreUsuario()); //se asigna el numero de doc como nombre de usuario.
//        usuario.setContraseña(password); //se setea la contraseña generada.
//        usuario.setRol(rol);
//        datosUsuarioAlumnoRegistrado = "Se registró el usuario del alumno con los siguientes datos:" + "\nUsuario: " + usuario.getNombreUsuario() + "\nContraseña: " + usuario.getContraseña();
//        SMTPConfig.sendMail("Registro en SAPO", "Te has registrado en el sistema SAPO, que te permite buscar pacientes para tus practicas. Tus datos de inicio de sesión son los siguientes: Usuario, Contraseña. Hace click en el siguiente enlace para completar tu registro.", usuario.getEmail());
//    }

    public String datosUsuarioRegistrado() {

        return datosUsuarioAlumnoRegistrado;
    }

    @PostConstruct
    public void init() {

       createUsuario();
    }

    public void saveUsuarioAlumno() {
        try{
            String randomPass = "PASSWORD";
            usuario.setContraseña(randomPass);
            SMTPConfig.sendMail(true, "Registro en SAPO", "Te has registrado en el sistema SAPO, que te permite buscar pacientes para tus practicas. Tus datos de inicio de sesión son los siguientes: Usuario: "+ usuario.getNombreUsuario()+" , Contraseña "+ usuario.getContraseña()+" .", usuario.getEmail());
            usuarioService.save(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario" + usuario.getNombreUsuario() +" fue cargado correctamente", null));
        }catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El alumno no fue cargado correctamente", null));
            System.out.println(ex.getMessage());
        }
    }

    public void updateUsuarioAlumno() {
        //AlumnoService.update(nuevoAlumno);
    }

    public void deleteUsuarioAlumno() {
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

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }

    private void createUsuario() {
         usuario = new Usuario();
        Rol rolUsuario = new Rol("Alumno");
        if(rolService.count(RolSpecs.byNombre(rolUsuario.getNombre())) == 0)
        {
            rolUsuario = rolService.save(rolUsuario);
        } else
        {
            rolUsuario = rolService.findOne(RolSpecs.byNombre(rolUsuario.getNombre()));
        }
        usuario.setRol(rolUsuario);
    }
    
}
