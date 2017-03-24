/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author ayojava
 * @param <T>
 * @param <PK>
 */
public abstract class GenericDAO<T, PK extends Serializable> implements GenericDAOIntf<T, PK> {

    @PersistenceContext
    private Session session;
    
    protected final Class<T> entityClass;
    
    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected Session getHibernateSession() {
        return session;
    }

    //returns the new id of the object saved 
    @Override
    public T saveOrUpdate(T entity) {
        getHibernateSession().saveOrUpdate(entity);
        return entity;
    }

    // doesnot update the id of the new object in the 
    @Override
    public T persist(T entity) {
        getHibernateSession().persist(entity);
        return entity;
    }

    @Override
    public T refresh(T entity) {
        getHibernateSession().refresh(entity);
        return entity;
    }

    @Override
    public T findById(PK id) {
        return getHibernateSession().get(entityClass, id);
    }

    protected Criteria getCriteria() {
        return getHibernateSession().createCriteria(entityClass);
    }
    
    @Override
    public List<T> findAll() {
        return getCriteria().list();
    }

    @Override
    public List<T> findAll(String orderBy) {
        Criteria criteria = getCriteria();
        criteria.addOrder(Order.asc(orderBy));
        return criteria.list();
    }

    @Override
    public void delete(T entity) {
        getHibernateSession().delete(getHibernateSession().merge(entity));
    }

    @Override
    public T edit(T entity) {
        entity = (T) getHibernateSession().merge(entity);
        return entity;
    }

    @Override
    public int deleteAll(List<T> entity) {
        int count = 0;
        for (T anEntity : entity) {
            getHibernateSession().delete(getHibernateSession().merge(anEntity));
            ++count;
        }
        return count;
    }

}
