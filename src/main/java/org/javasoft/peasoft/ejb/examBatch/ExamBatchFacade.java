/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.examBatch;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.core.StudentRecord;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class ExamBatchFacade extends GenericDAO<StudentRecord, Long> {

    public ExamBatchFacade() {
        super(StudentRecord.class);
    }
    
    public List<StudentRecord> findStudentRecordByBatch(String batch){
        Criteria recordCriteria =getCriteria().add(Restrictions.eq("examBatch", batch));
        recordCriteria.createAlias("student", "student");
        return recordCriteria.addOrder(Order.asc("student.surname")).list();
    }
}
