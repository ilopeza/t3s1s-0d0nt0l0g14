/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.dao;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 *
 * @author Maxi
 */
public interface GenericDao<T extends Object, PK extends Serializable> 
    extends JpaRepository<T, PK>, QueryDslPredicateExecutor {
    
}
