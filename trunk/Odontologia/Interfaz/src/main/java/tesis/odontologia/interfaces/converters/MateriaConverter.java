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
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.specification.MateriaSpecs;

/**
 *
 * @author Maxi
 */
@FacesConverter("materiaConverter")
public class MateriaConverter implements Converter {

    private MateriaService materiaService;

    public MateriaConverter() {
        ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(
                FacesContext.getCurrentInstance());
        materiaService = (MateriaService) ctx.getBean("materiaService");
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        
        if(string == null || string.isEmpty()) {
            return null;
        }
        Materia m;
        try{
            m =  materiaService.findOne(MateriaSpecs.byId(Long.parseLong(string)));
        }catch(NumberFormatException e){
            return null;
        }
        
        
        return m;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o==null){
            return null;
        }
        return ((Materia) o).getId().toString();
    }
}
