/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.AsignacionPacienteDao;
import tesis.odontologia.core.domain.asignaciones.AsignacionPaciente;
import tesis.odontologia.core.service.AsignacionPacienteService;


/**
 *
 * @author Maxi
 */
@Service(value = "asignacionPacienteService")
@Transactional(readOnly = true)
public class AsignacionPacienteServiceImpl extends GenericServiceImpl<AsignacionPaciente, AsignacionPacienteDao> implements AsignacionPacienteService {

    private AsignacionPacienteDao asignacionPacienteDao;

    @Autowired
    public AsignacionPacienteServiceImpl(AsignacionPacienteDao asignacionPacienteDao) {
        this.asignacionPacienteDao = asignacionPacienteDao;
        super.setDao(asignacionPacienteDao);
    }

}
