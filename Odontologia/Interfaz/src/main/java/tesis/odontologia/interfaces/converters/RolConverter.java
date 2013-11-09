/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.service.RolService;
import tesis.odontologia.core.specification.RolSpecs;

/**
 *
 * @author Maxi
 */
@FacesConverter("rolConverter")
public class RolConverter implements Converter {

    private RolService rolService;

    public RolConverter() {
        ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(
                FacesContext.getCurrentInstance());
        rolService = (RolService) ctx.getBean("rolService");
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.isEmpty()) {
            return null;
        }
        Rol r;
        try{
            r=  rolService.findOne(RolSpecs.byId(Long.parseLong(string)));
        } catch(NumberFormatException e){
            return null;
        }
                
        
        return r;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o==null){
            return null;}
        return ((Rol) o).getId().toString();
    }
}
