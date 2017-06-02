/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_A;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_B;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.ejb.data.NotificationFacade;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.settings.EnvSettingsFacade;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.Marks;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.Notification;
import org.javasoft.peasoft.utils.GlobalRegistry;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class StudentFacade extends GenericDAO<Student, Long> {

    private GlobalRegistry globalRegistry;

    @EJB
    private StudentRecordFacade studentRecordFacade;

    @EJB
    private SchoolFacade schoolFacade;
    
    @EJB
    private EnvSettingsFacade envSettingsFacade;
    
    @EJB
    private NotificationFacade notificationFacade;

    public StudentFacade() {
        super(Student.class);
    }

    public void editStudent(Student studentObj, StudentRecord record, School schoolObj) {

        edit(studentObj);

        School oldSchoolEntity = record.getSchool();
        if (Objects.deepEquals(oldSchoolEntity, schoolObj)) {
            
            studentRecordFacade.edit(record);
        } else {
            
            oldSchoolEntity =schoolFacade.fetchJoinSchoolRecord(oldSchoolEntity);
            oldSchoolEntity.getStudentRecords().remove(record);
            schoolFacade.edit(oldSchoolEntity);

            record.setSchool(schoolObj);
            studentRecordFacade.edit(record);

            schoolObj = schoolFacade.fetchJoinSchoolRecord(schoolObj);
            if (schoolObj.getStudentRecords() == null) {
                List<StudentRecord> allRecords = new ArrayList<>();
                allRecords.add(record);
                schoolObj.setStudentRecords(allRecords);
            } else {
                schoolObj.getStudentRecords().add(record);
            }
            schoolFacade.edit(schoolObj);

        }
    }

    public boolean studentAlreadyExists(Student studentObj){
        List<Student> allStudents = findAll();
        return allStudents.stream().anyMatch(s-> StringUtils.equalsIgnoreCase(s.getSurname(), studentObj.getSurname())
                && StringUtils.equalsIgnoreCase(s.getOthernames(), studentObj.getOthernames()));
       
    }
    
    public Student saveStudent(Student studentObj, StudentRecord record, School schoolObj) {
        globalRegistry = GlobalRegistry.getInstance();
        
        globalRegistry.updateStudentCount();
        
        studentObj.setIdentificationNo("SD" + StringUtils.leftPad(String.valueOf(globalRegistry.getStudentCount()), 5, "0"));
       
        Marks markObj = new Marks();
        record.setMarks(markObj);
        record.setExamBatch(globalRegistry.getStudentCount() % 2 == 0 ? BATCH_A : BATCH_B);
        StudentRecord recordEntity = studentRecordFacade.persist(record);
        recordEntity.setSchool(schoolObj);
        studentRecordFacade.edit(recordEntity);

        School schoolEntity = schoolFacade.fetchJoinSchoolRecord(schoolObj);
        if (schoolEntity.getStudentRecords() == null) {
            List<StudentRecord> allRecords = new ArrayList<>();
            allRecords.add(recordEntity);
            schoolEntity.setStudentRecords(allRecords);
        } else {
            schoolEntity.getStudentRecords().add(recordEntity);
        }
        schoolFacade.edit(schoolEntity);

        Student studentEntity = persist(studentObj);
        studentEntity.setStudentRecord(record);
        edit(studentEntity);

        recordEntity.setStudent(studentObj);
        studentRecordFacade.edit(recordEntity);
        
        Notification notification = new Notification();
        notification.setStudentRecord(recordEntity);
        notificationFacade.persist(notification);

        //globalRegistry.updateStudentCount();
        
        return studentEntity;
    }
}
