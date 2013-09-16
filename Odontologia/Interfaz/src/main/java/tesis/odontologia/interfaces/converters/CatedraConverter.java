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
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.specification.CatedraSpecs;

/**
 *
 * @author Maxi
 */
@FacesConverter("catedraConverter")
public class CatedraConverter implements Converter {

    private CatedraService catedraService;

    public CatedraConverter() {
        ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(
                FacesContext.getCurrentInstance());
        catedraService = (CatedraService) ctx.getBean("catedraService");
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.isEmpty()) {
            return null;
        }
        Catedra c =  catedraService.findOne(CatedraSpecs.byId(Long.parseLong(string)));
        
        return c;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((Catedra) o).getId().toString();
    }
}
