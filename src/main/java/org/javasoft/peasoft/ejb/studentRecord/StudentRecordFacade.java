/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.studentRecord;

import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_SIZE;
import static org.javasoft.peasoft.constants.PeaResource.NOT_SELECTED;
import static org.javasoft.peasoft.constants.PeaResource.SELECTED;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.StudentRecord;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class StudentRecordFacade extends GenericDAO<StudentRecord, Long> {

    public StudentRecordFacade() {
        super(StudentRecord.class);
    }

    //will not be necessary, just do a limit query
    public List<StudentRecord> markResults(double cutOffMark) {
        List<StudentRecord> allRecords = findAll();

        allRecords.forEach((StudentRecord record) -> {
            record.setGrade((record.getMarks().getTotalScore() >= cutOffMark) ? SELECTED : NOT_SELECTED);
            edit(record);
            if (record.getRecordId() % 50 == 0) {
                getHibernateSession().flush();
                getHibernateSession().clear();
            }
        });
        return findAll();
    }

    public void markResults(List<StudentRecord> allRecords) {
        List<StudentRecord> selectedStudents = allRecords.stream().filter(StudentRecord::isActive).limit(24).collect(toList());

        List<StudentRecord> notSelectedStudents = allRecords.stream().filter(StudentRecord::isActive).skip(24).collect(toList());

        selectedStudents.forEach(
                (StudentRecord record) -> {
                    record.setGrade(SELECTED);
                    edit(record);
                }
        );

        notSelectedStudents.forEach(
                (StudentRecord record) -> {
                    record.setGrade(NOT_SELECTED);
                    edit(record);
                    if (record.getRecordId() % BATCH_SIZE == 0) {
                        getHibernateSession().flush();
                        getHibernateSession().clear();
                    }
                }
        );

    }

    @Override
    public List<StudentRecord> findAll() {
        Criteria criteria = getCriteria().createAlias("student", "student");
        return criteria.addOrder(Order.asc("student.surname")).list();
    }

    public List<StudentRecord> orderByMarks() {
        Criteria recordCriteria = getCriteria();
        recordCriteria.createAlias("marks", "marks");
        return recordCriteria.addOrder(Order.desc("marks.totalScore")).list();
    }

    public List<StudentRecord> orderByMarks(School school) {
        Criteria recordCriteria = getCriteria();
        recordCriteria.createAlias("marks", "marks").createAlias("school", "school").add(Restrictions.eq("school.id", school.getId()));
        return recordCriteria.addOrder(Order.desc("marks.totalScore")).list();
    }

    /*
        public School fetchJoinSchoolRecord(School school){
       return (School) getCriteria().setFetchMode("studentRecords", FetchMode.JOIN)
               .add(Restrictions.eq("id", school.getId())).uniqueResult();
   }
     */
}
