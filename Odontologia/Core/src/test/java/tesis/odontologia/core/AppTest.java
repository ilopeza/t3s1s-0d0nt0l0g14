package tesis.odontologia.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import tesis.odontologia.core.dao.PersonaDao;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.service.PersonaService;


/**
 * Unit test for simple App.
 */
@ContextConfiguration(value = "classpath:context.xml")
public class AppTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PersonaService personaService;
    
    @Test
    public void test() {
        System.out.println("Hola mundooo!");
        Persona p = new Persona("Maximiliano", "Barros");
        personaService.save(p);
    }
    
}
