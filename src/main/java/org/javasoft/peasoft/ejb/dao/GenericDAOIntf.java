/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ayojava
 * @param <T>
 * @param <PK>
 */
public interface GenericDAOIntf<T , PK extends Serializable> {
    
    public  T saveOrUpdate(T entity);
    
    public  T persist(T entity);
    
    public T refresh(T entity) ;
    
    public T findById(PK id);
    
    public List <T> findAll();
    
    public List <T> findAll(String orderBy);
    
    public void delete (T entity);
    
    public int deleteAll (List<T> entity);
    
    public T edit(T entity);
    
    public int count();
    
    public T findOne();
        
}
