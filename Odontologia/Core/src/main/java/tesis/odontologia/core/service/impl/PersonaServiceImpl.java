/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.PersonaDao;
import tesis.odontologia.core.domain.Persona;
import tesis.odontologia.core.service.PersonaService;

/**
 *
 * @author Maxi
 */
@Service(value = "personaService")
@Transactional(readOnly = true)
public class PersonaServiceImpl implements PersonaService {
    
    private PersonaDao personaDao;
    
    @Autowired
    public PersonaServiceImpl(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    @Override
    @Transactional
    public void save(Persona p) {
        personaDao.save(p);
    }
    
}
