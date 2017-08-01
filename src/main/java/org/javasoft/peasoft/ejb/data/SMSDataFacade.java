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
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import static org.javasoft.peasoft.constants.PeaResource.SMS_BATCH_SIZE;
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
    
    public List<SMSData> findAllPendingSMS(){
        return getCriteria().add(Restrictions.eq("status", PENDING)).list();
    }
    
    public List<SMSData> findAllOrderByStudentName() {
        Criteria criteria = getCriteria().createAlias("student", "student");
        return criteria.addOrder(Order.asc("student.surname")).list();
    }
    
    public List<SMSData> findPendingSMS(){
        return getCriteria().add(Restrictions.eq("status", PENDING)).addOrder(Order.desc("id")).setMaxResults(SMS_BATCH_SIZE).list();
    }
}
