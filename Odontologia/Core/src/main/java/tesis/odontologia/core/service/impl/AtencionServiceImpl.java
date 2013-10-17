/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.AtencionDao;
import tesis.odontologia.core.domain.historiaclinica.Atencion;
import tesis.odontologia.core.service.AtencionService;


/**
 *
 * @author Maxi
 */
@Service(value = "atencionService")
@Transactional(readOnly = true)
public class AtencionServiceImpl extends GenericServiceImpl<Atencion, AtencionDao> implements AtencionService {

    private AtencionDao atencionDao;

    @Autowired
    public AtencionServiceImpl(AtencionDao atencionDao) {
        this.atencionDao = atencionDao;
        super.setDao(atencionDao);
    }

}
