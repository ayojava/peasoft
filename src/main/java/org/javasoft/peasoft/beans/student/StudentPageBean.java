/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.student;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.student.StudentFacade;
import org.javasoft.peasoft.entity.brainChallenge.Parent;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.entity.brainChallenge.Student;
import org.javasoft.peasoft.entity.brainChallenge.StudentRecord;
import org.javasoft.peasoft.entity.templates.AddressTemplate;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentPageBean")
@ViewScoped
public class StudentPageBean extends AbstractBean implements Serializable {

    @Getter @Setter
    private StudentRecord studentRecord;

    @Getter @Setter
    private Parent parent;

    @Getter @Setter
    private Student student;

    @Getter @Setter
    private School school;

    @Getter
    private List<Student> students;

    @Getter
    private List<School> schools;

    @EJB
    private SchoolFacade schoolFacade;

    @EJB
    private StudentFacade studentFacade;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_STUDENT, pageResource)) {
            studentRecord = new StudentRecord();
            parent = new Parent();
            AddressTemplate addressTemplate = new AddressTemplate();
            parent.setAddressTemplate(addressTemplate);
            student = new Student();
            student.setParent(parent);
            //schools = schoolFacade.fetchJoinSchools();
            schools = schoolFacade.findAll();
            super.setPageResource(appendFolderPath("student", NEW_STUDENT));
        } else if (StringUtils.equals(EDIT_STUDENT, pageResource)) {
             schools = schoolFacade.findAll();
            super.setPageResource(appendFolderPath("student", EDIT_STUDENT));
        } else if (StringUtils.equals(VIEW_STUDENT, pageResource)) {
            super.setPageResource(appendFolderPath("student", VIEW_STUDENT));
        } else if (StringUtils.equals(LIST_STUDENTS, pageResource)) {
            students = studentFacade.findAll();
            super.setPageResource(appendFolderPath("student", LIST_STUDENTS));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void setPageResource(String pageResource, Student studentObj) {
        student = studentObj;
        studentRecord =student.getStudentRecord();
        school = studentRecord.getSchool();
        setPageResource(pageResource);
    }

    public void saveStudent() {
        try {
            studentFacade.saveStudent(student, studentRecord, school);
            Messages.addGlobalInfo("Save Operation Successful");
            cleanup();
            setPageResource(LIST_STUDENTS);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void editStudent() {
        try{
            studentFacade.editStudent(student, studentRecord, school);
            Messages.addGlobalInfo("Edit Operation Successful");
            cleanup();
            setPageResource(LIST_STUDENTS);
        }catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    private void cleanup() {
        school = null;
        studentRecord = null;
        school = null;
    }
}

// Downloading ...Do not turn off target
