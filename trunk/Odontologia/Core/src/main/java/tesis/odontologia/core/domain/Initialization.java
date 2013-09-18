/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tesis.odontologia.core.domain.alumno.Alumno;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.domain.paciente.Paciente;
import tesis.odontologia.core.domain.profesor.Profesor;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.service.PersonaService;
import tesis.odontologia.core.service.AsignacionPacienteService;
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
    private AsignacionPacienteService asignacionPacienteService;
    
    private List<Materia> materias = new ArrayList<Materia>();
    
    private List<Paciente> pacientes= new ArrayList<Paciente>();
    
    private List<Diagnostico> diagnosticos = new ArrayList<Diagnostico>();
    
    private List<Alumno> alumnos = new ArrayList<Alumno>();
    
    @PostConstruct
    public void setUp() {
        cargarMaterias();
        cargarPacientes();
        cargarAlumnos();
        cargarProfesores();
        cargarAsignaciones();
    }
    
    private void cargarMaterias() {
        
        Materia m = new Materia("Endodoncia");
        Catedra ca = new Catedra("A");
        m.addCatedra(ca);
        Catedra cb = new Catedra("B");
        m.addCatedra(cb);
        TrabajoPractico mtp1 = new TrabajoPractico("Trabajo Práctico 1");
        m.addTrabajoPractico(mtp1);
        TrabajoPractico mtp2 = new TrabajoPractico("Trabajo Práctico 2");
        m.addTrabajoPractico(mtp2);
        TrabajoPractico mtp3 = new TrabajoPractico("Trabajo Práctico 3");
        m.addTrabajoPractico(mtp3);
        
        materias.add(materiaService.save(m));
        
        Materia m1 = new Materia("Cirugia");
        Catedra ca1 = new Catedra("C");
        m1.addCatedra(ca1);
        Catedra cb1 = new Catedra("D");
        m1.addCatedra(cb1);
        TrabajoPractico m1tp1 = new TrabajoPractico("Trabajo Práctico 4");
        m1.addTrabajoPractico(m1tp1);
        TrabajoPractico m1tp2 = new TrabajoPractico("Trabajo Práctico 5");
        m1.addTrabajoPractico(m1tp2);
        TrabajoPractico m1tp3 = new TrabajoPractico("Trabajo Práctico 6");
        m1.addTrabajoPractico(m1tp3);
        
        materias.add(materiaService.save(m1));
       
    }
    
      private void cargarPacientes() {
          
        Paciente p = new Paciente("Maximiliano", "Barros");
        p.setDocumento(new Documento("34688417", Documento.TipoDocumento.DNI));
        HistoriaClinica hc= HistoriaClinica.createDefault();
        hc.setNumero(1);
        
        Diagnostico d = new Diagnostico(materias.get(0).getTrabajoPractico().get(0),"Tratar Encias",Diagnostico.EstadoDiagnostico.PENDIENTE);
        d.setMateria(materias.get(1));
        diagnosticos.add(d);
        Diagnostico d1 = new Diagnostico(materias.get(0).getTrabajoPractico().get(1),"Descripcion TP",Diagnostico.EstadoDiagnostico.PENDIENTE);
        d1.setMateria(materias.get(1));
        diagnosticos.add(d1);
        Diagnostico d2 = new Diagnostico(materias.get(1).getTrabajoPractico().get(0),"Descripcion TP",Diagnostico.EstadoDiagnostico.PENDIENTE);
        d2.setMateria(materias.get(1));
        diagnosticos.add(d2);
        
        hc.setDiagnostico(diagnosticos);
        p.setHistoriaClinica(hc);
        p.setFechaNacimiento(Calendar.getInstance());
        
        pacientes.add(personaService.save(p));
        
        
        Paciente p1 = new Paciente("Enzo", "Biancato");
        p1.setDocumento(new Documento("34677666", Documento.TipoDocumento.DNI));
        HistoriaClinica hc1= HistoriaClinica.createDefault();
        hc1.setNumero(1);
        p1.setHistoriaClinica(hc1);
        p1.setFechaNacimiento(Calendar.getInstance());
        pacientes.add(personaService.save(p1));
        
        Paciente p2 = new Paciente("Miguel", "Romero");
        p2.setDocumento(new Documento("34234545", Documento.TipoDocumento.DNI));
        HistoriaClinica hc2= HistoriaClinica.createDefault();
        hc.setNumero(1);
        p2.setHistoriaClinica(hc2);
        p2.setFechaNacimiento(Calendar.getInstance());
        pacientes.add(personaService.save(p2));
        
    }
      
      private void cargarAlumnos() {
          
        Alumno a = new Alumno("Lucas", "Carrario");
        a.setDocumento(new Documento("34345545", Documento.TipoDocumento.DNI));
        a.setFechaNacimiento(Calendar.getInstance());
        alumnos.add(personaService.save(a));
        
        Alumno a1 = new Alumno("Roberto", "Carrario");
        a1.setDocumento(new Documento("34767767", Documento.TipoDocumento.DNI));
        a1.setFechaNacimiento(Calendar.getInstance());
        alumnos.add(personaService.save(a1));
        
        Alumno a2 = new Alumno("Emiliano", "Franzoia");
        a2.setDocumento(new Documento("34787787", Documento.TipoDocumento.DNI));
        a2.setFechaNacimiento(Calendar.getInstance());
        alumnos.add(personaService.save(a2));
        
    }
      
      private void cargarProfesores() {
          
        Profesor p = new Profesor("Lucas", "Rimoldi");
        p.setDocumento(new Documento("34342245", Documento.TipoDocumento.DNI));
        p.setFechaNacimiento(Calendar.getInstance());
        personaService.save(p);
        
        Profesor p1 = new Profesor("Lorenzo", "Diaz");
        p1.setDocumento(new Documento("34442245", Documento.TipoDocumento.DNI));
        p1.setFechaNacimiento(Calendar.getInstance());
        personaService.save(p1);
        
        Profesor p2 = new Profesor("Lucia", "Roma");
        p2.setDocumento(new Documento("34332245", Documento.TipoDocumento.DNI));
        p2.setFechaNacimiento(Calendar.getInstance());
        personaService.save(p2);
        
    }
      private void cargarAsignaciones(){
          AsignacionPaciente ap = new AsignacionPaciente();
          ap.setAlumno(alumnos.get(0));
          ap.setCatedra(materias.get(0).getCatedra().get(0));
          ap.setDiagnostico(pacientes.get(0).getHistoriaClinica().getDiagnostico().get(0));
          ap.setEstado(AsignacionPaciente.EstadoAsignacion.PENDIENTE);
          ap.setPaciente(pacientes.get(0));
          ap.setFechaAsignacion(Calendar.getInstance());
          asignacionPacienteService.save(ap);
          
          AsignacionPaciente ap1 = new AsignacionPaciente();
          ap1.setAlumno(alumnos.get(1));
          ap1.setCatedra(materias.get(0).getCatedra().get(0));
          ap1.setDiagnostico(pacientes.get(0).getHistoriaClinica().getDiagnostico().get(1));
          ap1.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
          ap1.setPaciente(pacientes.get(0));
          ap1.setFechaAsignacion(Calendar.getInstance());
          asignacionPacienteService.save(ap1);
          
          AsignacionPaciente ap2 = new AsignacionPaciente();
          ap2.setAlumno(alumnos.get(2));
          ap2.setCatedra(materias.get(1).getCatedra().get(0));
          ap2.setDiagnostico(pacientes.get(0).getHistoriaClinica().getDiagnostico().get(2));
          ap2.setEstado(AsignacionPaciente.EstadoAsignacion.CONFIRMADA);
          ap2.setPaciente(pacientes.get(0));
          ap2.setFechaAsignacion(Calendar.getInstance());
          asignacionPacienteService.save(ap2);
      }

    
      
      
}
