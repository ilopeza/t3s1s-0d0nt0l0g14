/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.CatedraDao;
import tesis.odontologia.core.dao.TrabajoPracticoDao;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.materia.TrabajoPractico;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.TrabajoPracticoService;

/**
 *
 * @author Maxi
 */
@Service(value = "trabajoPracticoService")
@Transactional(readOnly = true)
public class TrabajoPracticoServiceImpl extends GenericServiceImpl<TrabajoPractico, TrabajoPracticoDao> implements TrabajoPracticoService {

    private TrabajoPracticoDao trabajoPracticoDao;

    @Autowired
    public TrabajoPracticoServiceImpl(TrabajoPracticoDao trabajoPracticoDao) {
        this.trabajoPracticoDao = trabajoPracticoDao;
        super.setDao(trabajoPracticoDao);
    }

}
