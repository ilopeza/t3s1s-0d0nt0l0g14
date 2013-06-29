/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Maxi
 */
public interface GenericService<E extends Object> {

    <S extends E> S save(S entity);
    
    List<E> findAll();

    List<E> findAll(Sort sort);

    <S extends E> List<S> save(Iterable<S> entities);

    void flush();
    
    E saveAndFlush(E entity);

    void deleteInBatch(Iterable<E> entities);

    void deleteAllInBatch();

    Page<E> findAll(Pageable pageable);

    long count();

    void delete(E entity);

    void delete(Iterable<? extends E> entities);

    void deleteAll();

    <S extends E> S findOne(Predicate predicate);

    Iterable findAll(Predicate predicate);

    Iterable findAll(Predicate predicate, OrderSpecifier... orders);

    Page findAll(Predicate predicate, Pageable pageable);

    long count(Predicate predicate);
    
}
