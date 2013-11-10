/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.usuario;

import com.mysema.query.types.expr.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.service.UsuarioService;
import tesis.odontologia.core.specification.UsuarioSpecs;
import tesis.odontologia.interfaces.validacion.Validacion;

/**
 *
 * @author Enzo
 */
@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean {

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    @ManagedProperty(value = "#{rolService}")
    private RolService rolService;
    private List<Rol> roles;
    private Usuario usuario;
    private String repetirContraseña;
    private String filtroBusqueda;
    private Rol rolBusqueda;
    private List<Usuario> usuariosEncontrados;
    private Usuario selectedUsuario;
    private boolean habilitarNuevoUsuario;
    private boolean habilitarBotonNuevo;
    private boolean habilitarContraseña;

    @PostConstruct
    public void init() {
        roles = new ArrayList<Rol>();
        buscarRoles();
        if (usuario == null) {
            usuario = new Usuario();
            usuario.setRol(new Rol());
        }


        habilitarNuevoUsuario = false;
        habilitarBotonNuevo = true;
        usuariosEncontrados = new ArrayList<Usuario>();
        habilitarContraseña = true;

    }

    public String registrarUsuario() {
        boolean validacion = false;
        Validacion validar = new Validacion();
        if (validar.nullEmpty(usuario.getNombreUsuario())) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se ingresó un nombre de usuario.");
            validacion = true;
        }
        if (validar.nullEmpty(usuario.getEmail())) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se ingresó un correo electrónico.");
            validacion = true;
        } else if (!validar.validarMail(usuario.getEmail())) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "El correo electrónico no tiene el formato correcto.");
        }

        if (usuario.getRol() == null) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "No se se seleccionó un rol.");
            validacion = true;
        }
        if (habilitarContraseña) {

            if (validar.nullEmpty(usuario.getContraseña())) {
                agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "Debe ingresar una contraseña.");
                validacion = true;
            }
            if (!validar.nullEmpty(usuario.getContraseña()) && validar.nullEmpty(repetirContraseña)) {
                agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.");
                validacion = true;
            }
            if (!validar.nullEmpty(usuario.getContraseña()) && !validar.nullEmpty(repetirContraseña)) {
                if (usuario.getContraseña().compareTo(repetirContraseña) != 0) {
                    agregarMensajeAlUsuario(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.");
                    validacion = true;
                }
            }
        }
        if (validacion) {
            return null;
        }
        try {
            if (selectedUsuario != null) {
                actualizarUsuario();
            } else {
                nuevoUsuario();
            }
            habilitarNuevoUsuario = false;

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario no fue cargado correctamente", null));
            System.out.println(ex.getMessage());
        } finally {
            usuario = new Usuario();
            usuario.setRol(new Rol());
        }


        return null;
    }

    private void nuevoUsuario() {

        usuario = usuarioService.save(usuario);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario " + usuario.getNombreUsuario()
                + " guardado correctamente."));
    }

    private void actualizarUsuario() {
        usuario = usuarioService.save(usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profesor " + usuario.getNombreUsuario()
                + " actualizado correctamente."));
    }

    public void buscarUsuarios() {
        boolean aux = false;
        BooleanExpression predicate = UsuarioSpecs.byClaseUsuario();
        Validacion validar = new Validacion();
        if (!validar.nullEmpty(filtroBusqueda)) {
            predicate = predicate.and(UsuarioSpecs.byNombreOrEmail(filtroBusqueda));
            aux = true;
        }
        if (rolBusqueda != null) {
            predicate = predicate.and(UsuarioSpecs.byRol(rolBusqueda));
            aux = true;
        }

        if (aux) {
            try {
                usuariosEncontrados = (List<Usuario>) usuarioService.findAll(predicate);
                if (usuariosEncontrados.isEmpty()) {
                    agregarMensajeAlUsuario(FacesMessage.SEVERITY_INFO, "No se encontraron usuarios.");
                } else {
                    agregarMensajeAlUsuario(FacesMessage.SEVERITY_INFO, "Se encontraron " + usuariosEncontrados.size() + " usuarios.");
                }
            } catch (Exception e) {
            }

        } else {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_WARN, "Debe ingresar un parámetro como mínimo para realizar la búsqueda.");
        }

    }

    public void registrarNuevoUsuario() {
        setHabilitarNuevoUsuario(true);
        setHabilitarBotonNuevo(false);
        habilitarContraseña = true;
    }

    public void seleccionarUsuario() {
        if (selectedUsuario == null) {
            agregarMensajeAlUsuario(FacesMessage.SEVERITY_WARN, "Seleccione un usuario de la lista.");
        } else {
            usuario = selectedUsuario;
            habilitarNuevoUsuario = true;
            habilitarBotonNuevo = true;
            habilitarContraseña = false;
        }
    }

    public void agregarMensajeAlUsuario(Severity severidad, String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidad, mensaje, null));
    }

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * @return the roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    private void buscarRoles() {
        roles = rolService.findAll();
    }

    /**
     * @param rolService the rolService to set
     */
    public void setRolService(RolService rolService) {
        this.rolService = rolService;
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
     * @return the rolBusqueda
     */
    public Rol getRolBusqueda() {
        return rolBusqueda;
    }

    /**
     * @param rolBusqueda the rolBusqueda to set
     */
    public void setRolBusqueda(Rol rolBusqueda) {
        this.rolBusqueda = rolBusqueda;
    }

    /**
     * @return the usuariosEncontrados
     */
    public List<Usuario> getUsuariosEncontrados() {
        return usuariosEncontrados;
    }

    /**
     * @param usuariosEncontrados the usuariosEncontrados to set
     */
    public void setUsuariosEncontrados(List<Usuario> usuariosEncontrados) {
        this.usuariosEncontrados = usuariosEncontrados;
    }

    /**
     * @return the selectedUsuario
     */
    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    /**
     * @param selectedUsuario the selectedUsuario to set
     */
    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    /**
     * @return the habilitarBotonNuevo
     */
    public boolean isHabilitarBotonNuevo() {
        return habilitarBotonNuevo;
    }

    /**
     * @param habilitarBotonNuevo the habilitarBotonNuevo to set
     */
    public void setHabilitarBotonNuevo(boolean habilitarBotonNuevo) {
        this.habilitarBotonNuevo = habilitarBotonNuevo;
    }

    /**
     * @return the habilitarNuevoUsuario
     */
    public boolean isHabilitarNuevoUsuario() {
        return habilitarNuevoUsuario;
    }

    /**
     * @param habilitarNuevoUsuario the habilitarNuevoUsuario to set
     */
    public void setHabilitarNuevoUsuario(boolean habilitarNuevoUsuario) {
        this.habilitarNuevoUsuario = habilitarNuevoUsuario;
    }

    /**
     * @return the filtroBusqueda
     */
    public String getFiltroBusqueda() {
        return filtroBusqueda;
    }

    /**
     * @param filtroBusqueda the filtroBusqueda to set
     */
    public void setFiltroBusqueda(String filtroBusqueda) {
        this.filtroBusqueda = filtroBusqueda;
    }

    /**
     * @return the habilitarContraseña
     */
    public boolean isHabilitarContraseña() {
        return habilitarContraseña;
    }

    /**
     * @param habilitarContraseña the habilitarContraseña to set
     */
    public void setHabilitarContraseña(boolean habilitarContraseña) {
        this.habilitarContraseña = habilitarContraseña;
    }
}
