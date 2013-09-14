/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.CatedraDao;
import tesis.odontologia.core.dao.HistoriaClinicaDao;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.HistoriaClinicaService;

/**
 *
 * @author Maxi
 */
@Service(value = "historiaClinicaService")
@Transactional(readOnly = true)
public class HistoriaClinicaServiceImpl extends GenericServiceImpl<HistoriaClinica, HistoriaClinicaDao> implements HistoriaClinicaService {

    private HistoriaClinicaDao historiaClinicaDao;

    @Autowired
    public HistoriaClinicaServiceImpl(HistoriaClinicaDao historiaClinicaDao) {
        this.historiaClinicaDao = historiaClinicaDao;
        super.setDao(historiaClinicaDao);
    }

}
