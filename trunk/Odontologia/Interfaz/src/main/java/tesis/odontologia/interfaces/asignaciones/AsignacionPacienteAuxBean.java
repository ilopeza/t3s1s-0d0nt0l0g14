/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces.asignaciones;

import javax.faces.bean.ManagedProperty;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.service.MateriaService;
import tesis.odontologia.core.specification.MateriaSpecs;
import tesis.odontologia.core.utils.FechaUtils;

/**
 *
 * @author alespe
 */
public class AsignacionPacienteAuxBean {

    private String paciente;
        private String alumno;
        private String fecha;
        private String descripcionDiagnostico;
        private String materia;
        private String catedra;
        private Long id;
        private String trabajoPractico;
        @ManagedProperty(value = "#{materiaService}")
        private MateriaService materiaService;
        private Materia mat;
        
        public AsignacionPacienteAuxBean(AsignacionPaciente asignacionPaciente) {
            this.id = asignacionPaciente.getId();
            this.paciente = asignacionPaciente.getPaciente().toString();
            this.alumno = asignacionPaciente.getAlumno().toString();
            this.fecha = FechaUtils.fechaMaskFormat(asignacionPaciente.getFechaAsignacion(), "dd/MM/yyyy HH:mm");
            this.descripcionDiagnostico = asignacionPaciente.getDiagnostico().getDescripcion();
            mat=materiaService.findOne(MateriaSpecs.byCatedra(asignacionPaciente.getCatedra()));
            this.materia=mat.getNombre();
            this.catedra = asignacionPaciente.getCatedra().toString();
            this.trabajoPractico = asignacionPaciente.getDiagnostico().getTrabajoPractico().getNombre();
        }

        // GETTERS Y SETTERS
                public MateriaService getMateriaService() {
                    return materiaService;
                }
        
                public void setMateriaService(MateriaService materiaService) {
                    this.materiaService = materiaService;
                }
                  public String getMateria() {
                    return materia;
                }
        
                public void setMateria(String materia) {
                    this.materia = materia;
                }
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDescripcionDiagnostico() {
            return descripcionDiagnostico;
        }

        public void setDescripcionDiagnostico(String descripcionDiagnostico) {
            this.descripcionDiagnostico = descripcionDiagnostico;
        }

        public String getTrabajoPractico() {
            return trabajoPractico;
        }

        public void setTrabajoPractico(String trabajoPractico) {
            this.trabajoPractico = trabajoPractico;
        }

        public String getCatedra() {
            return catedra;
        }

        public void setCatedra(String catedra) {
            this.catedra = catedra;
        }

        public String getPaciente() {
            return paciente;
        }

        public void setPaciente(String paciente) {
            this.paciente = paciente;
        }

        public String getAlumno() {
            return alumno;
        }

        public void setAlumno(String alumno) {
            this.alumno = alumno;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

}
