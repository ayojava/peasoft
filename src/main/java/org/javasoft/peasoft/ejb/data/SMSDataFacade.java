/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.data;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.data.SMSData;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class SMSDataFacade extends GenericDAO<SMSData, Long>{

    public SMSDataFacade(){
        super(SMSData.class);
    }
    
    @Override
    public List<SMSData> findAll() {
        Criteria criteria = getCriteria().createAlias("student", "student");
        return criteria.addOrder(Order.asc("student.surname")).list();
    }
}
