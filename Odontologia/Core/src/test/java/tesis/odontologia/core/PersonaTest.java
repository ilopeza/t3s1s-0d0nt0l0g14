package tesis.odontologia.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.service.PersonaService;


/**
 * Unit test for simple App.
 */
public class PersonaTest extends AbstractTest {

    @Autowired
    private PersonaService personaService;
    
    Persona p;
    
    @Test
    public void test() {
        System.out.println("Hola mundooo!");
        p = new Persona("Maximiliano", "Barros");
        personaService.save(p);
    }
    
}
