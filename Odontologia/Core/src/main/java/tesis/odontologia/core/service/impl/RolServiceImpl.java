/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.RolDao;
import tesis.odontologia.core.domain.usuario.Rol;
import tesis.odontologia.core.service.RolService;

/**
 *
 * @author Maxi
 */
@Service(value = "rolService")
@Transactional(readOnly = true)
public class RolServiceImpl extends GenericServiceImpl<Rol, RolDao> implements RolService {

    private RolDao rolDao;

    @Autowired
    public RolServiceImpl(RolDao rolDao) {
        this.rolDao = rolDao;
        super.setDao(rolDao);
    }

}
