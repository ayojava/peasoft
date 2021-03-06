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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_SIZE;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.data.EmailData;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class EmailDataFacade extends GenericDAO<EmailData, Long>{

    public EmailDataFacade(){
        super(EmailData.class);
    }
    
    @Override
    public List<EmailData> findAll(){
         return getCriteria().addOrder(Order.asc("recipientName")).list();
    }
    
    public List<EmailData> findPendingEmails(){
        return getCriteria().add(Restrictions.eq("status", PENDING)).setMaxResults(BATCH_SIZE).list();
    }
}
