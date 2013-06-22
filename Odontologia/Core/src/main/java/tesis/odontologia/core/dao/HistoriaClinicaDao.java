/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import tesis.odontologia.core.domain.historiaclinica.HistoriaClinica;


/**
 *
 * @author Maxi
 */
public interface HistoriaClinicaDao extends JpaRepository<HistoriaClinica, Long>, QueryDslPredicateExecutor {
}
