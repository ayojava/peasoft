/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.student;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.beans.core.util.SMSUtilBean;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.data.SMSDataFacade;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.student.StudentFacade;
import org.javasoft.peasoft.entity.core.Parent;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.templates.AddressTemplate;
import org.javasoft.peasoft.service.StudentService;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentPageBean")
@ViewScoped
public class StudentPageBean extends AbstractBean implements Serializable {

    @Getter
    @Setter
    private StudentRecord studentRecord;

    @Getter
    @Setter
    private Parent parent;

    @Getter
    @Setter
    private Student student;

    @Getter
    @Setter
    private School school;

    @Getter
    private List<Student> students;

    @Getter
    private List<School> schools;

    @EJB
    private SchoolFacade schoolFacade;

    @EJB
    private StudentFacade studentFacade;

    private StudentService studentService;

    @Inject
    private EmailUtilBean emailUtilBean;

    @Inject
    private SMSUtilBean smsUtilBean;

    @EJB
    private SMSDataFacade smsDataFacade;

    @EJB
    private EmailDataFacade emailDataFacade;

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
            schools = schoolFacade.findAll("name");
            super.setPageResource(appendFolderPath(STUDENT_FOLDER, NEW_STUDENT));
        } else if (StringUtils.equals(EDIT_STUDENT, pageResource)) {
            schools = schoolFacade.findAll();
            super.setPageResource(appendFolderPath(STUDENT_FOLDER, EDIT_STUDENT));
        } else if (StringUtils.equals(VIEW_STUDENT, pageResource)) {
            super.setPageResource(appendFolderPath(STUDENT_FOLDER, VIEW_STUDENT));
        } else if (StringUtils.equals(LIST_STUDENTS, pageResource)) {
            students = studentFacade.findAll("surname");
            super.setPageResource(appendFolderPath(STUDENT_FOLDER, LIST_STUDENTS));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void setPageResource(String pageResource, Student studentObj) {
        student = studentObj;
        studentRecord = student.getStudentRecord();
        school = studentRecord.getSchool();
        setPageResource(pageResource);
    }

    public void saveStudent() {
        Student studentObj = null;
        try {
            studentObj = studentFacade.saveStudent(student, studentRecord, school);
            studentService = new StudentService();

            Messages.addGlobalInfo("Save Operation Successful");

            setPageResource(LIST_STUDENTS);

        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }

        EmailData emailData = studentService.generateWelcomeEmail(studentObj, emailUtilBean, school.getName());

        HashSet<String> phoneNos = new HashSet<>();
        phoneNos.add(studentObj.getPhoneNo());
        phoneNos.add(studentObj.getOtherPhoneNo());
        phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
        phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());

        SMSData smsData = studentService.generateWelcomeSMS(smsUtilBean, student);

        phoneNos.stream().forEach((String phoneNo) -> {
            if (StringUtils.isNotBlank(phoneNo)) {
                smsData.setId(null);
                smsData.setRecipientPhoneNo(appendCountryCode(phoneNo));
                smsDataFacade.persist(smsData);
            }
        });

        emailDataFacade.persist(emailData);
        cleanup();
    }

    public void editStudent() {
        try {
            studentFacade.editStudent(student, studentRecord, school);
            Messages.addGlobalInfo("Edit Operation Successful");
            cleanup();
            setPageResource(LIST_STUDENTS);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    private void cleanup() {
        school = null;
        studentRecord = null;

    }
}

// Downloading ...Do not turn off target
