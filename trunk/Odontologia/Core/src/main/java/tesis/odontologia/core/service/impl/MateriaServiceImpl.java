/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.MateriaDao;
import tesis.odontologia.core.domain.materia.Materia;
import tesis.odontologia.core.service.MateriaService;

/**
 *
 * @author Maxi
 */
@Service(value = "materiaService")
@Transactional(readOnly = true)
public class MateriaServiceImpl extends GenericServiceImpl<Materia, MateriaDao> implements MateriaService {

    private MateriaDao materiaDao;

    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
        super.setDao(materiaDao);
    }

}
