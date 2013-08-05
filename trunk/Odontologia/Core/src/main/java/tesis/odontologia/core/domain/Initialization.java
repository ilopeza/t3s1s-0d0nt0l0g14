/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.service.MateriaService;

/**
 *
 * @author Maxi
 */
@Component
public class Initialization {

    @Autowired
    private MateriaService materiaService;

    @PostConstruct
    public void setUp() {
        cargarMateria();
    }
    
    private void cargarMateria() {
        Materia m = new Materia("Endodoncia", null);
        materiaService.save(m);
    }
    
}
