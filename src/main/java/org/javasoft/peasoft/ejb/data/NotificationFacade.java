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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.ACTIVE;
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
    
    public Long pendingBatchNotificationCount(String examBatch){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("batchNotification", false));//
        criteria.createAlias("studentRecord", "studentRecord");
        criteria.add(Restrictions.eq("studentRecord.examBatch", examBatch));
        criteria.setProjection(Projections.rowCount());
        return (criteria.list().isEmpty() ? 0 : (Long) criteria.list().get(0));
    }
    
    public Long pendingResultNotificationCount(String grade){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("resultNotification", false));
        criteria.createAlias("studentRecord", "studentRecord");
        criteria.add(Restrictions.eq("studentRecord.grade", grade));
        criteria.setProjection(Projections.rowCount());
        return (criteria.list().isEmpty() ? 0 : (Long) criteria.list().get(0));
    }
    
    public Long pendingGuidelinesCount(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("guidelineNotification", false));
        criteria.setProjection(Projections.rowCount());
        return (criteria.list().isEmpty() ? 0 : (Long) criteria.list().get(0));
    }
    
    public List<Notification> getPendingBatchNotification(String examBatch){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("batchNotification", false));
        criteria.createAlias("studentRecord", "studentRecord");
        criteria.add(Restrictions.eq("studentRecord.examBatch", examBatch));
        criteria.setMaxResults(BATCH_SIZE);
        return  criteria.list();
    }
    
    public List<Notification> getPendingResultNotifications(String grade){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("resultNotification", false));
        criteria.createAlias("studentRecord", "studentRecord");
        criteria.add(Restrictions.eq("studentRecord.grade", grade));
        return criteria.list();
    }
    
    public List<Notification> getPendingGuidelines(){
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("guidelineNotification", false));
        criteria.setMaxResults(BATCH_SIZE);
        return  criteria.list();
    }
    
    public List<Notification> getPendingAcademyNotificationOrderByMarksAndStatus(){
        
        Criteria criteria = getCriteria();
        //criteria.add(Restrictions.eq("resultNotification", false));
        criteria.createAlias("studentRecord", "studentRecord");
        criteria.createAlias("studentRecord.marks", "marks");
        criteria.add(Restrictions.eq("studentRecord.status", ACTIVE));
        criteria.addOrder(Order.desc("marks.totalScore"));
        return criteria.list();
    }
}
