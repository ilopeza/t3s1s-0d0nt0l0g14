/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.CatedraDao;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.service.CatedraService;

/**
 *
 * @author Maxi
 */
@Service(value = "catedraService")
@Transactional(readOnly = true)
public class CatedraServiceImpl extends GenericServiceImpl<Catedra, CatedraDao> implements CatedraService {

    private CatedraDao catedraDao;

    @Autowired
    public CatedraServiceImpl(CatedraDao catedraDao) {
        this.catedraDao = catedraDao;
        super.setDao(catedraDao);
    }

}
