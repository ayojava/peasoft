/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.student;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.brainChallenge.Marks;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.entity.brainChallenge.Student;
import org.javasoft.peasoft.entity.brainChallenge.StudentRecord;
import org.javasoft.peasoft.utils.GlobalRegistry;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class StudentFacade extends GenericDAO<Student, Long>{
    
    private GlobalRegistry globalRegistry;
    
    @EJB
    private StudentRecordFacade studentRecordFacade;
    
    @EJB
    private SchoolFacade schoolFacade;
    
    public StudentFacade(){
        super(Student.class);
    }
    
    public void saveStudent(Student studentObj, StudentRecord record,School schoolObj){
        globalRegistry = GlobalRegistry.getInstance();
        
        studentObj.setIdentificationNo("SD" +StringUtils.leftPad(String.valueOf(globalRegistry.getStudentCount()), 5, "0"));
        
        Marks markObj = new Marks();
        record.setMarks(markObj);
        StudentRecord recordEntity = studentRecordFacade.persist(record);
        recordEntity.setSchool(schoolObj);
        studentRecordFacade.edit(recordEntity);
        
        //School schoolEntity = schoolFacade.findById(schoolObj.getId());
        School schoolEntity = schoolFacade.fetchJoinSchoolRecord(schoolObj);
        if(schoolEntity.getStudentRecords() == null){
            List<StudentRecord> allRecords = new ArrayList<>();
            allRecords.add(recordEntity);
            schoolEntity.setStudentRecords(allRecords);
        }else{
            schoolEntity.getStudentRecords().add(recordEntity);
        }
        schoolFacade.edit(schoolEntity);
        
        Student studentEntity = persist(studentObj);
        studentEntity.setStudentRecord(record);
        edit(studentEntity);
        
        recordEntity.setStudent(studentObj);
        studentRecordFacade.edit(recordEntity);
        
        globalRegistry.updateStudentCount();
    }
}