/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tesis.odontologia.core.domain.Persona;

/**
 *
 * @author Maxi
 */
public interface PersonaDao extends JpaRepository<Persona, Long>{
    
}
