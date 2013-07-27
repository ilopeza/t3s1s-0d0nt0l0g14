/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tesis.odontologia.core.dao.GenericDao;
import tesis.odontologia.core.service.GenericService;

/**
 *
 * @author Maxi
 */
public abstract class GenericServiceImpl<E extends Object, DAO extends GenericDao> implements GenericService<E> {

    private DAO dao;

    public void setDao(DAO dao) {
        this.dao = dao;
    }
    
    @Override
    public DAO getDao() {
        return dao;
    }
    
    @Override
    public <S extends E> S save(S entity) {
        return (S) dao.save(entity);
    }

    @Override
    public List<E> findAll() {
        return dao.findAll();
    }

    @Override
    public List<E> findAll(Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    public <S extends E> List<S> save(Iterable<S> entities) {
        return dao.save(entities);
    }

    @Override
    public void flush() {
        dao.flush();
    }

    @Override
    public E saveAndFlush(E entity) {
        return (E) dao.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(Iterable<E> entities) {
        dao.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        dao.deleteAllInBatch();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public void delete(E entity) {
        dao.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends E> entities) {
        dao.delete(entities);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public <S extends E> S findOne(Predicate predicate) {
        return (S) dao.findOne(predicate);
    }

    @Override
    public Iterable findAll(Predicate predicate) {
        return dao.findAll(predicate);
    }

    @Override
    public Iterable findAll(Predicate predicate, OrderSpecifier... orders) {
        return dao.findAll(predicate, orders);
    }

    @Override
    public Page findAll(Predicate predicate, Pageable pageable) {
        return dao.findAll(predicate, pageable);
    }

    @Override
    public long count(Predicate predicate) {
        return dao.count(predicate);
    }
    
}
