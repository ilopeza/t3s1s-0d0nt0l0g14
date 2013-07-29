/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.paciente.Domicilio;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.core.specification.RolSpecs;

/**
 *
 * @author Mau
 */
@ManagedBean(name = "formAlumnoBean")
@ViewScoped
public class FormAlumnoBean {

    private Alumno alumno;
    private Usuario usuario;
    
    private List<Documento.TipoDocumento> listaTipoDocumento;   
    
    @ManagedProperty(value = "#{personaService}")
    private PersonaService personaService;
    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    @ManagedProperty(value = "#{rolService}")
    private RolService rolService;
        
    public FormAlumnoBean() {
    }
    
    @PostConstruct
    public void init() {

            alumno = new Alumno();
            alumno.setDocumento(new Documento("35018118", Documento.TipoDocumento.DNI));

    }
    
    public void save() {               
        
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
        usuario.setNombreUsuario("35018118");
        usuario.setContraseña("pass");
        usuario.setEmail("mau.g.sistemas");
        //usuario = usuarioService.findOne(UsuarioSpecs.byNombreUsuario("35018118"));
        alumno.setUsuario(usuario);
        personaService.save(alumno);        
    }
    
    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * @return the alumno
     */
    public Alumno getAlumno() {
        return alumno;
    }

    /**
     * @param alumno the alumno to set
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * @param rolService the rolService to set
     */
    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }
    
        public List<Documento.TipoDocumento> getListaTipoDocumento() {
        listaTipoDocumento = Arrays.asList(Documento.TipoDocumento.values());
        return listaTipoDocumento;
    }

}
