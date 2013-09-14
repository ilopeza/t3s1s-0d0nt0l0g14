/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.DiagnosticoDao;
import tesis.odontologia.core.domain.historiaclinica.Diagnostico;
import tesis.odontologia.core.service.DiagnosticoService;

/**
 *
 * @author Maxi
 */
@Service(value = "diagnosticoService")
@Transactional(readOnly = true)
public class DiagnosticoServiceImpl extends GenericServiceImpl<Diagnostico, DiagnosticoDao> implements DiagnosticoService {

    private DiagnosticoDao diagnosticoDao;

    @Autowired
    public DiagnosticoServiceImpl(DiagnosticoDao diagnosticoDao) {
        this.diagnosticoDao = diagnosticoDao;
        super.setDao(diagnosticoDao);
    }

}
