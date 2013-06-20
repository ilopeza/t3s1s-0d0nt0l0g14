package tesis.odontologia.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import tesis.odontologia.core.dao.PersonaDao;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.domain.paciente.Paciente;
import static org.testng.Assert.*;
import tesis.odontologia.core.domain.Documento;
import tesis.odontologia.core.domain.paciente.Domicilio;
import tesis.odontologia.core.specification.PacienteSpecs;

/**
 * Unit test for simple App.
 */
public class PersonaTest extends AbstractTest {

    @Autowired
    private PersonaDao personaDao;
    Paciente p;

    @Test
    public void crearPaciente() {
        p = new Paciente("Maximiliano", "Barros");
        p.setDocumento(new Documento(34688417, Documento.TipoDocumento.DNI));
        p.setDomicilio(new Domicilio("Macaon 4123", "Cordoba"));
        personaDao.save(p);
    }

    @Test(dependsOnMethods = "crearPaciente")
    public void buscarPaciente() {
        Persona per = (Persona) personaDao.findOne(PacienteSpecs.byNombre("Maximiliano"));
        assertNotNull(per);
        System.out.println("Nombre: " + per.getNombre());
        System.out.println("Apellido: " + per.getApellido());
    }
}
