/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces;

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import tesis.odontologia.interfaces.login.LoginBean;

/**
 *
 * @author Maxi
 */
public class Web {
    
    /**
     * Metodo que recarga la pagina en la cual se encuentra uno segun la URL del 
     * navegador. Vuelve a ejecutar los metodos marcados con @PostConstruct como
     * por ejemplo el metodo Init().
     * @throws IOException 
     */
    public static void reloadPage() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    /**
     * Metodo que ejecuta acciones sobre un dialog desde el bean, permitiendo mostrarlo
     * u ocultarlo desde el bean. Se debe pasar por parametro el nombre del dialogo
     * junto a la accion. 
     * Ejemplo: nombreDialog.show() --- nombreDialog.hide()
     * @param dialog 
     */
    public static void callDialog(String dialog) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(dialog);
    }
    
    /**
     * Metodo que retorna el bean del login. En el mismo siempre se encuentra 
     * cargada la persona logueada.
     * @return 
     */
    public static LoginBean getLoginBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LoginBean login = (LoginBean) session.getAttribute("loginBean");
        return login;
    }
    
}
