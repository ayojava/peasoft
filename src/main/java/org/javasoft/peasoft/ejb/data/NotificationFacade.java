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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_SIZE;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.data.Notification;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class NotificationFacade extends GenericDAO<Notification, Long>{

    public NotificationFacade(){
        super(Notification.class);
    }
    
    public int pendingNotificationCount(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("batchNotification", false));
        criteria.setProjection(Projections.rowCount());
        return (criteria.list().isEmpty() ? 0 : (Integer) criteria.list().get(0));
    }
    
    public int pendingGuidelinesCount(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("guidelineNotification", false));
        criteria.setProjection(Projections.rowCount());
        return (criteria.list().isEmpty() ? 0 : (Integer) criteria.list().get(0));
    }
    
    public List<Notification> getPendingNotification(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("batchNotification", false));
        criteria.setMaxResults(BATCH_SIZE);
        return  criteria.list();
    }
}
