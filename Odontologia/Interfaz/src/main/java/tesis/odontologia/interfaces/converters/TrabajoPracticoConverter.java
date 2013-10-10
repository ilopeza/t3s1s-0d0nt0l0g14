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
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.service.TrabajoPracticoService;
import tesis.odontologia.core.specification.TrabajoPracticoSpecs;

/**
 *
 * @author Maxi
 */
@FacesConverter("trabajoPracticoConverter")
public class TrabajoPracticoConverter implements Converter {

    private TrabajoPracticoService trabajoPracticoService;

    public TrabajoPracticoConverter() {
        ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(
                FacesContext.getCurrentInstance());
        trabajoPracticoService = (TrabajoPracticoService) ctx.getBean("trabajoPracticoService");
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.isEmpty()) {
            return null;
        }
        
        TrabajoPractico tp;
        try{
           tp=  trabajoPracticoService.findOne(TrabajoPracticoSpecs.byId(Long.parseLong(string))); 
        }catch(NumberFormatException e){
            return null;
        }
        
        
        return tp;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o==null){
            return null;}
        return ((TrabajoPractico) o).getId().toString();
    }
}
