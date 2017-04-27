/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.studentRecord;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.NOT_SELECTED;
import static org.javasoft.peasoft.constants.PeaResource.SELECTED;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.core.StudentRecord;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class StudentRecordFacade extends GenericDAO<StudentRecord, Long>{
    
    public StudentRecordFacade(){
        super(StudentRecord.class);
    }
    
    public List<StudentRecord> markResults(double cutOffMark){
        List<StudentRecord> allRecords = findAll();
        
        allRecords.forEach((StudentRecord record) -> {
            record.setGrade((record.getMarks().getTotalScore() >= cutOffMark)?SELECTED :NOT_SELECTED);
            edit(record);
            if(record.getRecordId() % 50 ==0){
                getHibernateSession().flush();
                getHibernateSession().clear();
            }
        }); 
       return   findAll();
    }
    
    
}
