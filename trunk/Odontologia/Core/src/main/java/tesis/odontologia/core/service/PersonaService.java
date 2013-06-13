/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import tesis.odontologia.core.domain.Persona;

/**
 *
 * @author Maxi
 */
public interface PersonaService {
    
    void save(Persona p);
    
}
