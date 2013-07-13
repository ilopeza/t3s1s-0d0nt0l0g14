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
@ManagedBean(name = "formAlumnoBean")
@ViewScoped

public class AlumnoBean {

    private Alumno alumno;
    private List<Documento.TipoDocumento> listaTipoDocumento;
    private String datosAlumnoRegistrado;

    public AlumnoBean() {
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno Alumno) {
        this.alumno = Alumno;
    }

    public List<Documento.TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(Documento.TipoDocumento.values());
        return listaTipoDocumento;
    }

    public void registrarAlumno() {
        String password = "pass"; //se genera la clave
        Rol rol = new Rol("Alumno"); //todos los roles deberian estar generados.
        Usuario usuario = new Usuario(); //se genera el usuario
        usuario.setUsuario(alumno.getEmail()); //se asigna el email como nombre de usuario.
        usuario.setContraseña(password); //se setea la contraseña generada.
        usuario.setRol(rol);
        alumno.setUsuario(usuario);
        datosAlumnoRegistrado = "Se registró el alumno con los siguientes datos:" + "\nUsuario: " + alumno.getEmail() + "\nContraseña: " + alumno.getUsuario().getContraseña();
        SMTPConfig.sendMail("Registro en SAPO", "Te has registrado en el sistema SAPO, que te permite buscar pacientes para tus practicas. Tus datos de inicio de sesión son los siguientes: Usuario, Contraseña. Hace click en el siguiente enlace para completar tu registro.", alumno.getEmail());
    }
    
    public String datosAlumnoRegistrado(){
        
      return datosAlumnoRegistrado;
    }
    
   

    @PostConstruct
    public void init() {
        
        if(alumno == null){
            alumno = new Alumno();
            alumno.setDocumento(new Documento());
        }

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

    public Alumno searchAlumno() {
        Alumno alu = new Alumno();
        //AlumnoService.searchAlumno(alu);
        return alu;
    }
}
