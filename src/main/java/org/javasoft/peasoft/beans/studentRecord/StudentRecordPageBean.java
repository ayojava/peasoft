/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.studentRecord;

import java.io.File;
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
import static org.javasoft.peasoft.constants.PeaResource.VIEW_HOME_PAGE;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.data.SMSDataFacade;
import org.javasoft.peasoft.ejb.settings.BatchSettingsFacade;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import org.javasoft.peasoft.service.StudentRecordService;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentRecordPageBean")
@ViewScoped
public class StudentRecordPageBean extends AbstractBean implements Serializable {

//add a mark button which blocks user input and handles bulk txn in hibernate    
    @Getter
    @Setter
    private StudentRecord studentRecord;

    @Getter
    private List<StudentRecord> studentRecords;

    @EJB
    private StudentRecordFacade studentRecordFacade;

    @Inject
    private EmailUtilBean emailUtilBean;

    @Inject
    private SMSUtilBean smsUtilBean;

    @EJB
    private SMSDataFacade smsDataFacade;

    @EJB
    private EmailDataFacade emailDataFacade;

    @EJB
    private BatchSettingsFacade batchSettingsFacade;

    private StudentRecordService studentRecordService;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(EDIT_STUDENT_RECORD, pageResource)) {
            super.setPageResource(appendFolderPath(STUDENT_RECORD_FOLDER, EDIT_STUDENT_RECORD));
        } else if (StringUtils.equals(VIEW_STUDENT_RECORD, pageResource)) {
            super.setPageResource(appendFolderPath(STUDENT_RECORD_FOLDER, VIEW_STUDENT_RECORD));
        } else if (StringUtils.equals(LIST_STUDENT_RECORDS, pageResource)) {
            studentRecords = studentRecordFacade.findAll();
            super.setPageResource(appendFolderPath(STUDENT_RECORD_FOLDER, LIST_STUDENT_RECORDS));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void setPageResource(String pageResource, StudentRecord studentRecordObj) {
        studentRecord = studentRecordObj;
        setPageResource(pageResource);
    }

    public void editStudentRecord() {
        try {
            studentRecord.getMarks().computeMarks();
            studentRecordFacade.edit(studentRecord);
            Messages.addGlobalInfo("Edit Operation Successful");
            cleanup();
            setPageResource(LIST_STUDENT_RECORDS);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void sendQuizAndInterviewNotification() {

        try {
            studentRecordService = new StudentRecordService();
            BatchSettings batchSettings = batchSettingsFacade.findOne();
            String filePath = registry.getInitFilePath() + "bc2016" + File.separator + "description.pdf";
            studentRecords.stream().filter(StudentRecord::isActive).forEach((aRecord) -> {

                String fullName = aRecord.getStudent().getFullName();
                String email = aRecord.getStudent().getEmail();
                System.out.println("Full Name :: [ " + fullName + " ] - Email :: [ " + email + " ] ");
                EmailData emailData = studentRecordService.generateExamDetailsNotification(aRecord, filePath, emailUtilBean, batchSettings);
                emailDataFacade.persist(emailData);

                Student studentObj = aRecord.getStudent();
                HashSet<String> phoneNos = new HashSet<>();
                phoneNos.add(studentObj.getPhoneNo());
                phoneNos.add(studentObj.getOtherPhoneNo());
                phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
                phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());
                
                // funny behaviour 
                //SMSData smsData = studentRecordService.generateExamDetailsNotification(smsUtilBean, fullName,email);
                SMSData smsData = new SMSData();
                String message ="Dear "+ fullName+", the Screening Test and Oral Interview will come up on Friday 28th and Saturday 29th July,2017."
                        + "Details of your batch,venue have been sent to "+ email +".Incase you did not get yours,kindly come to the library(Behind "
                        + "Ojokoro Housing Estate Meiran) to confirm or call 08188687814,07063583701";
                smsData.setMessage(message);
                log.info("SMS Message :: [{}]" , message);

                phoneNos.stream().forEach((String phoneNo) -> {
                    if (StringUtils.isNotBlank(phoneNo)) {
                        smsData.setId(null);
                        smsData.setRecipientPhoneNo(appendCountryCode(phoneNo));
                        smsDataFacade.persist(smsData);
                    }
                });
            });

            Messages.addGlobalInfo("Save Operation Successful");

            setPageResource(LIST_STUDENT_RECORDS);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }

    }

    public void reSendQuizAndInterviewNotification() {
        try {
            studentRecordService = new StudentRecordService();
            BatchSettings batchSettings = batchSettingsFacade.findOne();
            String filePath = registry.getInitFilePath() + "bc2016" + File.separator + "description.pdf";
            
            EmailData emailData = studentRecordService.generateExamDetailsNotification(studentRecord, filePath, emailUtilBean, batchSettings);
                emailDataFacade.persist(emailData);

                
                /*
                Student studentObj = studentRecord.getStudent();
                HashSet<String> phoneNos = new HashSet<>();
                phoneNos.add(studentObj.getPhoneNo());
                phoneNos.add(studentObj.getOtherPhoneNo());
                phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
                phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());

                
                SMSData smsData = studentRecordService.generateExamDetailsNotification(smsUtilBean, studentRecord);

                phoneNos.stream().forEach((String phoneNo) -> {
                    if (StringUtils.isNotBlank(phoneNo)) {
                        smsData.setId(null);
                        smsData.setRecipientPhoneNo(appendCountryCode(phoneNo));
                        smsDataFacade.persist(smsData);
                    }
                });
                */
                Messages.addGlobalInfo("Information Successsfully sent");
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }

    }

//    public void markScores(){
//        try {
//            studentRecordFacade.markResults(studentRecords);
//            Messages.addGlobalInfo(" Marking Completed ");
//            setPageResource(LIST_STUDENT_RECORDS);
//        }catch (Exception ex) {
//            log.error("An Error has Occurred :::", ex);
//            Messages.addGlobalError("An Error has Occured");
//        }
//    }
    private void cleanup() {

    }
}
