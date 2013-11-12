/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.alumno;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.interfaces.Web;
import tesis.odontologia.interfaces.login.LoginBean;

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
    private boolean firstLogin;

    public FormAlumnoBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");

        firstLogin = true;
        alumno = new Alumno();
        alumno.setId(new Long(1));
        alumno.setFechaNacimiento(Calendar.getInstance());
        alumno.setDocumento(new Documento());
        alumno.setUsuario(login.getUsuario());
        
        if(login.getPersona() != null ) {
            alumno = (Alumno) login.getPersona();
            firstLogin = false;
        }

    }

    public String save() {

        try {
            alumno = personaService.save(alumno);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            LoginBean login = (LoginBean) session.getAttribute("loginBean");
            login.setPersona(alumno);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alumno " + alumno.toString()
                    + " guardado correctamente."));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El alumno no fue cargado correctamente", null));
            System.out.println(ex.getMessage());

        }
        if(firstLogin) {
            Web.callDialog("dlgFirstLogin.hide()");
            Web.callDialog("dlg.show()");
            return null;
        }
        return "formAlumno";
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
