/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.results;

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
public class ResultsFacade extends GenericDAO<StudentRecord, Long>{
    
    public ResultsFacade() {
        super(StudentRecord.class);
    }
    
    public List<StudentRecord> findStudentRecordByGrade(String grade){
        Criteria recordCriteria =getCriteria().add(Restrictions.eq("grade", grade));
        recordCriteria.createAlias("marks", "marks");
        return recordCriteria.addOrder(Order.desc("marks.totalScore")).list();
    }
    
    public List<StudentRecord> orderByMarks() {
        Criteria recordCriteria = getCriteria();
        recordCriteria.createAlias("marks", "marks");
        return recordCriteria.addOrder(Order.desc("marks.totalScore")).list();
    }
}
