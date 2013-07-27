package tesis.odontologia.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import tesis.odontologia.core.dao.PersonaDao;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.paciente.Paciente;
import static org.testng.Assert.*;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.paciente.Domicilio;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.specification.PacienteSpecs;

/**
 * Unit test for simple App.
 */
public class PersonaTest extends AbstractTest {

    @Autowired
    private PersonaService personaService;
    Paciente p;

    @Test
    public void crearPaciente() {
        p = new Paciente("Maximiliano", "Barros");
        p.setDocumento(new Documento("34688417", Documento.TipoDocumento.DNI));
        p.setDomicilio(new Domicilio("Macaon 4123", "Cordoba"));
        personaService.save(p);
    }

    @Test(dependsOnMethods = "crearPaciente")
    public void buscarPaciente() {
        Persona per = (Persona) personaService.findOne(PacienteSpecs.byNombre("Maximiliano"));
        assertNotNull(per);
        System.out.println("Nombre: " + per.getNombre());
        System.out.println("Apellido: " + per.getApellido());
    }
    
    @Test(dependsOnMethods = "buscarPaciente")
    public void guardarConService() {
        Paciente pa = new Paciente("Roberto", "Rodriguez");
        pa.setDocumento(new Documento("17267218", Documento.TipoDocumento.DNI));
        pa.setDomicilio(new Domicilio("Ruelle 4123", "Cordoba"));
        personaService.save(pa);
    }
    
    @Test(dependsOnMethods = "guardarConService")
    public void testCount() {
        Long count = personaService.count();
        assertTrue(count > 0);
        assertNotNull(count);
        System.out.println("Count: " + count);
    }
    
    @Test(dependsOnMethods = "guardarConService")
    public void testFindAll() {
        List<Persona> findAll = personaService.findAll();
        assertNotNull(findAll);
        assertFalse(findAll.isEmpty());
        System.out.println("FindAll: " + findAll);
    }
    
    @Test(dependsOnMethods = "guardarConService")
    public void testSpecification() {
        Persona findOne = personaService.findOne(PacienteSpecs.byNombre("robert"));
        assertNotNull(findOne);
        System.out.println("FinOne Predicate: " + findOne);
    }
    
    @Test(dependsOnMethods = "testSpecification")
    public void testAlumno() {
        Alumno a = new Alumno("Juan", "Barrionuevo");
        a.setDocumento(new Documento("28188271", Documento.TipoDocumento.DNI));
        a = personaService.save(a);
        assertNotNull(a);
        assertFalse(a.isNew());
    }
}
