/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Atencion;
import tesis.odontologia.core.domain.historiaclinica.AtencionGenerica;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.domain.profesor.Responsable;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.AsignacionPacienteService;
import tesis.odontologia.core.service.AtencionService;
import tesis.odontologia.core.service.RolService;

/**
 *
 * @author Maxi
 */
@Component
public class Initialization {

    @Autowired
    private MateriaService materiaService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private RolService rolService;
    @Autowired
    private AsignacionPacienteService asignacionPacienteService;
    private List<Atencion> atenciones = new ArrayList<Atencion>();
    private List<Materia> materias = new ArrayList<Materia>();
    private List<Paciente> pacientes = new ArrayList<Paciente>();
    private List<Diagnostico> diagnosticos = new ArrayList<Diagnostico>();
    private List<Alumno> alumnos = new ArrayList<Alumno>();
    private List<Profesor> profesores = new ArrayList<Profesor>();
    private List<Rol> roles = new ArrayList<Rol>();
    ArrayList<Materia> matprofp= new ArrayList<Materia>();
     ArrayList<Materia> matprofp1= new ArrayList<Materia>();
     private List<AsignacionPaciente> asignaciones= new ArrayList<AsignacionPaciente>();

    @PostConstruct
    public void setUp() {
        cargarRoles();
        cargarMaterias();
        cargarPacientes();
        cargarAlumnos();
        cargarResponsable();
        cargarProfesores();
        cargarAsignaciones();
        cargarAtenciones();
    }

    private void cargarRoles() {
        roles.add(rolService.save(new Rol(Rol.ALUMNO)));
        roles.add(rolService.save(new Rol(Rol.PROFESOR)));
        roles.add(rolService.save(new Rol(Rol.PACIENTE)));
        roles.add(rolService.save(new Rol(Rol.RESPONSABLE)));
        roles.add(rolService.save(new Rol(Rol.ADMINACADEMICO)));
    }

    private void cargarMaterias() {

        Materia m = new Materia("Endodoncia");
        Catedra ca = new Catedra("A");
        m.addCatedra(ca);
        Catedra cb = new Catedra("B");
        m.addCatedra(cb);
        TrabajoPractico mtp1 = new TrabajoPractico("Trabajo Práctico 1", "AAAAAAA");
        m.addTrabajoPractico(mtp1);
        TrabajoPractico mtp2 = new TrabajoPractico("Trabajo Práctico 2", "BBBBBBB");
        m.addTrabajoPractico(mtp2);
        TrabajoPractico mtp3 = new TrabajoPractico("Trabajo Práctico 3", "CCCCCCC");
        m.addTrabajoPractico(mtp3);
        materias.add(materiaService.save(m));
        matprofp.add(m);
        matprofp1.add(m);
        
        Materia m1 = new Materia("Cirugia I");
        Catedra ca1 = new Catedra("C");
        m1.addCatedra(ca1);
        Catedra cb1 = new Catedra("D");
        m1.addCatedra(cb1);
        TrabajoPractico m1tp1 = new TrabajoPractico("Trabajo Práctico 4", "AAAAAAA");
        m1.addTrabajoPractico(m1tp1);
        TrabajoPractico m1tp2 = new TrabajoPractico("Trabajo Práctico 5", "BBBBBBB");
        m1.addTrabajoPractico(m1tp2);
        TrabajoPractico m1tp3 = new TrabajoPractico("Trabajo Práctico 6", "CCCCCCC");
        m1.addTrabajoPractico(m1tp3);
        matprofp.add(m1);
        materias.add(materiaService.save(m1));
        matprofp1.add(m1);
        Materia m2 = new Materia("Periodoncia");
        Catedra ca2 = new Catedra("L");
        m2.addCatedra(ca2);
        Catedra cb2 = new Catedra("M");
        m2.addCatedra(cb2);
        TrabajoPractico m2tp1 = new TrabajoPractico("Trabajo Práctico 7", "AAAAAAA");
        m2.addTrabajoPractico(m2tp1);
        TrabajoPractico m2tp2 = new TrabajoPractico("Trabajo Práctico 8", "BBBBBBB");
        m2.addTrabajoPractico(m2tp2);
        TrabajoPractico m2tp3 = new TrabajoPractico("Trabajo Práctico 9", "CCCCCCC");
        m2.addTrabajoPractico(m2tp3);
        matprofp.add(m2);
        materias.add(materiaService.save(m2));
        
        Materia m3 = new Materia("Cirugia II");
        Catedra ca3 = new Catedra("F");
        m3.addCatedra(ca3);
        Catedra cb3 = new Catedra("K");
        m3.addCatedra(cb3);
        TrabajoPractico m3tp1 = new TrabajoPractico("Trabajo Práctico 10", "AAAAAA");
        m3.addTrabajoPractico(m3tp1);
        TrabajoPractico m3tp2 = new TrabajoPractico("Trabajo Práctico 11", "BBBBBB");
        m3.addTrabajoPractico(m3tp2);
        TrabajoPractico m3tp3 = new TrabajoPractico("Trabajo Práctico 12", "CCCCCC");
        m3.addTrabajoPractico(m3tp3);
        matprofp.add(m3);
        materias.add(materiaService.save(m3));

    }

    private void cargarPacientes() {

        Paciente p = new Paciente("Maximiliano", "Barros");
        p.setDocumento(new Documento("34688417", Documento.TipoDocumento.DNI));
        p.setSexo(Paciente.SexoTipo.MASCULIN0);
        p.setEmail("enzo.biancato@gmail.com");
        HistoriaClinica hc = HistoriaClinica.createDefault();
        hc.setNumero(1);

        Diagnostico d = new Diagnostico(materias.get(0).getTrabajoPractico().get(0), "Tratar Encias", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d.setMateria(materias.get(1));
        diagnosticos.add(d);
        Diagnostico d1 = new Diagnostico(materias.get(0).getTrabajoPractico().get(1), "Descripcion TP", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d1.setMateria(materias.get(1));
        diagnosticos.add(d1);
        Diagnostico d2 = new Diagnostico(materias.get(1).getTrabajoPractico().get(0), "Descripcion TP: Se le realizará una extracción del 7mo molar premeditado pro el raavi shankar", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d2.setMateria(materias.get(1));
        diagnosticos.add(d2);

        hc.setDiagnostico(diagnosticos);
       
        p.setHistoriaClinica(hc);
        p.setFechaNacimiento(Calendar.getInstance());

        p.setDomicilio(new Domicilio("Ituzaingó", "1066", "Córdoba"));

        pacientes.add(personaService.save(p));

        Paciente p3 = new Paciente("Ponzio", "Leonardo");
        p3.setDocumento(new Documento("34686666", Documento.TipoDocumento.DNI));
        p3.setSexo(Paciente.SexoTipo.MASCULIN0);
        p3.setEmail("ponzio.leonardo@gmail.com");
        HistoriaClinica hc3 = HistoriaClinica.createDefault();
        hc3.setNumero(4);
        List<Diagnostico> diagnosticos2 = new ArrayList<Diagnostico>();
        Diagnostico d3 = new Diagnostico(materias.get(0).getTrabajoPractico().get(0), "Tratar Encias jsj", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d3.setMateria(materias.get(1));
        diagnosticos2.add(d3);
        Diagnostico d4 = new Diagnostico(materias.get(0).getTrabajoPractico().get(1), "Descripcion TP hola", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d4.setMateria(materias.get(1));
        diagnosticos2.add(d4);
        Diagnostico d5 = new Diagnostico(materias.get(1).getTrabajoPractico().get(0), "Descripcion TP: Se le realizará una extracción del 7mo molar premeditado pro el raavi shankar", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d5.setMateria(materias.get(1));
        diagnosticos2.add(d5);

        hc3.setDiagnostico(diagnosticos2);
        p3.setHistoriaClinica(hc3);
        p3.setFechaNacimiento(Calendar.getInstance());

        p3.setDomicilio(new Domicilio("Ituzaingó", "1000", "Córdoba"));

        pacientes.add(personaService.save(p3));

        Paciente p1 = new Paciente("Enzo", "Biancato");
        p1.setDocumento(new Documento("34677666", Documento.TipoDocumento.DNI));
        p1.setSexo(Paciente.SexoTipo.MASCULIN0);
        p1.setEmail("enzo.biancato@gmail.com");
        HistoriaClinica hc1 = HistoriaClinica.createDefault();
        hc1.setNumero(2);
        diagnosticos2 = new ArrayList<Diagnostico>();
        Diagnostico d6 = new Diagnostico(materias.get(2).getTrabajoPractico().get(0), "Tratar Encias jsj", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d6.setMateria(materias.get(2));
        diagnosticos2.add(d6);
        Diagnostico d7 = new Diagnostico(materias.get(2).getTrabajoPractico().get(1), "Descripcion TP hola", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d7.setMateria(materias.get(2));
        diagnosticos2.add(d7);
        Diagnostico d8 = new Diagnostico(materias.get(3).getTrabajoPractico().get(0), "Descripcion TP: Se le realizará una extracción del 7mo molar premeditado pro el raavi shankar", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d8.setMateria(materias.get(3));
        diagnosticos2.add(d8);

        hc1.setDiagnostico(diagnosticos2);
        p1.setHistoriaClinica(hc1);
        p1.setFechaNacimiento(Calendar.getInstance());
        p1.setDomicilio(new Domicilio("Obispo Salguero", "444", "Córdoba"));
        pacientes.add(personaService.save(p1));

        Paciente p2 = new Paciente("Miguel", "Romero");
        p2.setDocumento(new Documento("34234545", Documento.TipoDocumento.DNI));
        p2.setSexo(Paciente.SexoTipo.MASCULIN0);
        p2.setEmail("romeritodelvalle@gmail.com");
        HistoriaClinica hc2 = HistoriaClinica.createDefault();
        hc2.setNumero(3);
        diagnosticos2 = new ArrayList<Diagnostico>();
        Diagnostico d9 = new Diagnostico(materias.get(2).getTrabajoPractico().get(0), "Tratar Encias jsj", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d9.setMateria(materias.get(2));
        diagnosticos2.add(d9);
        Diagnostico d10 = new Diagnostico(materias.get(2).getTrabajoPractico().get(1), "Descripcion TP hola", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d10.setMateria(materias.get(2));
        diagnosticos2.add(d10);
        Diagnostico d11 = new Diagnostico(materias.get(3).getTrabajoPractico().get(0), "Descripcion TP: Se le realizará una extracción del 7mo molar premeditado pro el raavi shankar", Diagnostico.EstadoDiagnostico.PENDIENTE);
        d11.setMateria(materias.get(3));
        diagnosticos2.add(d11);
        hc2.setDiagnostico(diagnosticos2);
        p2.setHistoriaClinica(hc2);
        p2.setFechaNacimiento(Calendar.getInstance());
        p2.setDomicilio(new Domicilio("Gral. Manuel Belgrano", "745", "Córdoba"));
        pacientes.add(personaService.save(p2));

    }

    private void cargarAlumnos() {

        Alumno a = new Alumno("Lucas", "Carrario");
        a.setDocumento(new Documento("34345545", Documento.TipoDocumento.DNI));
        a.setFechaNacimiento(Calendar.getInstance());
        Usuario usuario1 = new Usuario("34345545", "34345545", roles.get(0), "Lucas@gmail.com");
        a.setUsuario(usuario1);
        alumnos.add(personaService.save(a));

        Alumno a1 = new Alumno("Roberto", "Carrario");
        a1.setDocumento(new Documento("34767767", Documento.TipoDocumento.DNI));
        a1.setFechaNacimiento(Calendar.getInstance());
        Usuario usuario2 = new Usuario("34767767", "34767767", roles.get(0), "Roberto@gmail.com");
        a1.setUsuario(usuario2);
        alumnos.add(personaService.save(a1));

        Alumno a2 = new Alumno("Emiliano", "Franzoia");
        a2.setDocumento(new Documento("34787787", Documento.TipoDocumento.DNI));
        a2.setFechaNacimiento(Calendar.getInstance());
        Usuario usuario3 = new Usuario("34787787", "34787787", roles.get(0), "Emiliano@gmail.com");
        a2.setUsuario(usuario3);
        alumnos.add(personaService.save(a2));

    }

    private void cargarResponsable() {
        Responsable p = new Responsable("Juan", "Romero");
        p.setDocumento(new Documento("30271221", Documento.TipoDocumento.DNI));
        p.setFechaNacimiento(Calendar.getInstance());
        Usuario usuario1 = new Usuario("30271221", "30271221", roles.get(3), "Juan@gmail.com");
        p.setUsuario(usuario1);
        personaService.save(p);
    }

    private void cargarProfesores() {
        
        Profesor p = new Profesor("Lucas", "Rimoldi");
        p.setDocumento(new Documento("34342245", Documento.TipoDocumento.DNI));
        p.setFechaNacimiento(Calendar.getInstance());
        p.setListaMaterias(matprofp);
        p.setEstado(Profesor.EstadoProfesor.ACTIVO);
        Usuario usuario1 = new Usuario("34342245", "34342245", roles.get(1), "Lucas@gmail.com");
        p.setUsuario(usuario1);
        profesores.add(personaService.save(p));

        Profesor p1 = new Profesor("Lorenzo", "Diaz");
        p1.setDocumento(new Documento("34442245", Documento.TipoDocumento.DNI));
        p1.setFechaNacimiento(Calendar.getInstance());
        Usuario usuario2 = new Usuario("34442245", "34442245", roles.get(1), "Lorenzo@gmail.com");
        p1.setListaMaterias(matprofp1);
        p1.setUsuario(usuario2);
        p.setEstado(Profesor.EstadoProfesor.ACTIVO);
        profesores.add(personaService.save(p1));

        Profesor p2 = new Profesor("Lucia", "Roma");
        p2.setDocumento(new Documento("34332245", Documento.TipoDocumento.DNI));
        p2.setFechaNacimiento(Calendar.getInstance());
        Usuario usuario3 = new Usuario("34332245", "34332245", roles.get(1), "Lucia@gmail.com");
        p2.setListaMaterias(matprofp1);
        p2.setUsuario(usuario3);
        p.setEstado(Profesor.EstadoProfesor.DADO_DE_BAJA);
        profesores.add(personaService.save(p2));

    }

    private void cargarAsignaciones() {
        AsignacionPaciente ap = new AsignacionPaciente();
        ap.setAlumno(alumnos.get(0));
        ap.setCatedra(materias.get(0).getCatedra().get(0));
        ap.setDiagnostico(pacientes.get(0).getHistoriaClinica().getDiagnostico().get(0));
        ap.setEstado(AsignacionPaciente.EstadoAsignacion.AUTORIZADA);
        ap.setProfesor(profesores.get(0));
        ap.setPaciente(pacientes.get(0));
        ap.setFechaCreacionAsignacion(Calendar.getInstance());
        ap.setFechaAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap));

        AsignacionPaciente ap1 = new AsignacionPaciente();
        ap1.setAlumno(alumnos.get(1));
        ap1.setCatedra(materias.get(0).getCatedra().get(0));
        ap1.setDiagnostico(pacientes.get(0).getHistoriaClinica().getDiagnostico().get(1));
        ap1.setEstado(AsignacionPaciente.EstadoAsignacion.AUTORIZADA);
        ap1.setPaciente(pacientes.get(0));
        Calendar c1 = GregorianCalendar.getInstance();
        c1.set(2013, 10, 9);
        ap1.setFechaAsignacion(c1);
        ap1.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap1));

        AsignacionPaciente ap2 = new AsignacionPaciente();
        ap2.setAlumno(alumnos.get(2));
        ap2.setCatedra(materias.get(1).getCatedra().get(0));
        ap2.setDiagnostico(pacientes.get(0).getHistoriaClinica().getDiagnostico().get(2));
        ap2.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
        ap2.setPaciente(pacientes.get(0));
        ap2.setFechaAsignacion(Calendar.getInstance());
        ap2.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap2));
        
        AsignacionPaciente ap4 = new AsignacionPaciente();
        ap4.setAlumno(alumnos.get(0));
        ap4.setCatedra(materias.get(0).getCatedra().get(0));
        ap4.setDiagnostico(pacientes.get(1).getHistoriaClinica().getDiagnostico().get(0));
        ap4.setEstado(AsignacionPaciente.EstadoAsignacion.PENDIENTE);
        ap4.setPaciente(pacientes.get(1));
        ap4.setFechaAsignacion(Calendar.getInstance());
        ap4.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap4));

        AsignacionPaciente ap5 = new AsignacionPaciente();
        ap5.setAlumno(alumnos.get(1));
        ap5.setCatedra(materias.get(0).getCatedra().get(0));
        ap5.setDiagnostico(pacientes.get(1).getHistoriaClinica().getDiagnostico().get(1));
        ap5.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
        ap5.setPaciente(pacientes.get(1));
        Calendar c2 = GregorianCalendar.getInstance();
        c2.set(2013, 11, 30);
        ap5.setFechaAsignacion(c2);
        ap5.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap5));

        AsignacionPaciente ap6 = new AsignacionPaciente();
        ap6.setAlumno(alumnos.get(2));
        ap6.setCatedra(materias.get(1).getCatedra().get(0));
        ap6.setDiagnostico(pacientes.get(1).getHistoriaClinica().getDiagnostico().get(2));
        ap6.setEstado(AsignacionPaciente.EstadoAsignacion.AUTORIZADA);
        ap6.setPaciente(pacientes.get(1));
        Calendar c3 = GregorianCalendar.getInstance();
        c3.set(2013, 06, 30);
        ap6.setFechaAsignacion(c3);
        ap6.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap6));
        
        AsignacionPaciente ap7 = new AsignacionPaciente();
        ap7.setAlumno(alumnos.get(0));
        ap7.setCatedra(materias.get(2).getCatedra().get(0));
        ap7.setDiagnostico(pacientes.get(2).getHistoriaClinica().getDiagnostico().get(0));
        ap7.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
        ap7.setPaciente(pacientes.get(2));
        ap7.setFechaAsignacion(Calendar.getInstance());
        ap7.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap7));

        AsignacionPaciente ap8 = new AsignacionPaciente();
        ap8.setAlumno(alumnos.get(1));
        ap8.setCatedra(materias.get(2).getCatedra().get(0));
        ap8.setDiagnostico(pacientes.get(2).getHistoriaClinica().getDiagnostico().get(1));
        ap8.setEstado(AsignacionPaciente.EstadoAsignacion.AUTORIZADA);
        ap8.setPaciente(pacientes.get(2));
        Calendar c4 = GregorianCalendar.getInstance();
        c4.set(2013, 10, 9);
        ap8.setFechaAsignacion(c4);
        ap8.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap8));

        AsignacionPaciente ap9 = new AsignacionPaciente();
        ap9.setAlumno(alumnos.get(2));
        ap9.setCatedra(materias.get(3).getCatedra().get(0));
        ap9.setDiagnostico(pacientes.get(2).getHistoriaClinica().getDiagnostico().get(2));
        ap9.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
        ap9.setPaciente(pacientes.get(2));
        ap9.setFechaAsignacion(Calendar.getInstance());
        ap9.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap9));
        
        AsignacionPaciente ap10 = new AsignacionPaciente();
        ap10.setAlumno(alumnos.get(0));
        ap10.setCatedra(materias.get(2).getCatedra().get(0));
        ap10.setDiagnostico(pacientes.get(3).getHistoriaClinica().getDiagnostico().get(0));
        ap10.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
        ap10.setPaciente(pacientes.get(3));
        ap10.setFechaAsignacion(Calendar.getInstance());
        ap10.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap10));

        AsignacionPaciente ap11 = new AsignacionPaciente();
        ap11.setAlumno(alumnos.get(1));
        ap11.setCatedra(materias.get(2).getCatedra().get(0));
        ap11.setDiagnostico(pacientes.get(3).getHistoriaClinica().getDiagnostico().get(1));
        ap11.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
        ap11.setPaciente(pacientes.get(3));
        Calendar c5 = GregorianCalendar.getInstance();
        c5.set(2013, 11, 30);
        ap11.setFechaAsignacion(c5);
        ap11.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap11));

        AsignacionPaciente ap12 = new AsignacionPaciente();
        ap12.setAlumno(alumnos.get(2));
        ap12.setCatedra(materias.get(3).getCatedra().get(0));
        ap12.setDiagnostico(pacientes.get(3).getHistoriaClinica().getDiagnostico().get(2));
        ap12.setEstado(AsignacionPaciente.EstadoAsignacion.AUTORIZADA);
        ap12.setPaciente(pacientes.get(3));
        Calendar c6 = GregorianCalendar.getInstance();
        c6.set(2013, 06, 30);
        ap12.setFechaAsignacion(c6);
        ap12.setFechaCreacionAsignacion(Calendar.getInstance());
        asignaciones.add(asignacionPacienteService.save(ap12));
    }
    
    private void cargarAtenciones(){
        AtencionGenerica at = new AtencionGenerica();
        at.setFechaAtencion(Calendar.getInstance());
        at.setMotivoConsultaOdontologica("dolor dolor mas dolor");
        at.setAsignacionPaciente(asignaciones.get(0));
        at.setDescripcionProcedimiento("seguramente le saco algo que se yo que? hdasdfhsdfhaiosdhfpoashdfashdfpasidfjasijdfo  asdfhoas  asofdh oiasd hf  ioasdfhoas hfoias ihasd fohasiofdh asodaoisdhfoash foiha sdofh sadfhoahsdfo");
        atenciones.add(at);
        pacientes.get(0).getHistoriaClinica().setAtencion(atenciones);
        personaService.save(pacientes.get(0));
    }
}
