/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.studentRecord;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.beans.core.GenericBean;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.beans.core.util.SMSUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.DISPLAY_DATE_FORMAT_DAYS;
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
import org.javasoft.peasoft.excel.studentRecord.StudentRecordsListExcelReport;
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

    @Inject
    private GenericBean genericBean;

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

    public void exportStudentRecords() {
        try {
            String fileName = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS) + "_" + RandomStringUtils.randomNumeric(5) + ".xls";
            StudentRecordsListExcelReport studentRecordsListExcelReport = new StudentRecordsListExcelReport();
            studentRecordsListExcelReport.populateExcelSheet("StudentRecords", fileName, studentRecords);
            Messages.addGlobalInfo("Excel Sheet Sent");
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void sendAcademyDetailsNotification() {
        try {
            studentRecordService = new StudentRecordService();
            String filePath = registry.getInitFilePath() + "bc2016" + File.separator + "timetable.pdf";
            studentRecords.stream().forEach((aRecord) -> {
                EmailData emailData = studentRecordService.generateAcademyDetailsNotificationForStudents(studentRecord, filePath, emailUtilBean);
                emailDataFacade.persist(emailData);
                
                EmailData parentEmailData = studentRecordService.generateAcademyInteractiveSessionNotificationForParents(studentRecord, emailUtilBean);
                emailDataFacade.persist(parentEmailData);
                Student studentObj = aRecord.getStudent();
                
                aRecord.getMarks().computeMarks();
                studentRecordFacade.edit(aRecord);
                
                HashSet<String> phoneNos = new HashSet<>();
                phoneNos.add(studentObj.getPhoneNo());
                phoneNos.add(studentObj.getOtherPhoneNo());
                phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
                phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());

                SMSData academySMSData = new SMSData();
                SMSData parentSMSData = new SMSData();

                String academySMS = "Dear " + studentObj.getAbbreviatedName() + "-BrainChallenge Academy-Thur and Fri Aug 10 and 11 2017-Alinco "
                        + "Event Centre,1 Asuko Layout,Ijaiye(Behind Ojokoro LCDA)-9am daily";

                String parentSMS = "Interactive Day with parents of BrainChallenge Applicants will hold on Fri Aug 11,2017 by" +
                " 2pm at Alinco Event Centre,1 Asuku Layout,Ijaiye(Behind Ojokoro LCDA)";

                phoneNos.stream().forEach((String phoneNo) -> {
                    if (StringUtils.isNotBlank(phoneNo)) {
                        academySMSData.setId(null);
                        academySMSData.setMessage(academySMS);
                        academySMSData.setRecipientPhoneNo(appendCountryCode(phoneNo));
                        smsDataFacade.persist(academySMSData);
                    }
                });

                String parentPhoneNo1 = studentObj.getParent().getAddressTemplate().getContactPhoneNo1();
                if (StringUtils.isNotBlank(parentPhoneNo1)) {
                    parentSMSData.setId(null);
                    parentSMSData.setMessage(parentSMS);
                    parentSMSData.setRecipientPhoneNo(appendCountryCode(parentPhoneNo1));
                    smsDataFacade.persist(parentSMSData);
                }
                
                String parentPhoneNo2 = studentObj.getParent().getAddressTemplate().getContactPhoneNo2();
                if (StringUtils.isNotBlank(parentPhoneNo2) && !StringUtils.equalsIgnoreCase(parentPhoneNo1, parentPhoneNo2)) {
                    parentSMSData.setId(null);
                    parentSMSData.setMessage(parentSMS);
                    parentSMSData.setRecipientPhoneNo(appendCountryCode(parentPhoneNo2));
                    smsDataFacade.persist(parentSMSData);
                }

            });

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
                //System.out.println("Full Name :: [ " + fullName + " ] - Email :: [ " + email + " ] ");
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
                int exceedMsgLength = 0;
                SMSData quizSMSData = new SMSData();
                SMSData interviewSMSData = new SMSData();
//                
                String quizMsg = "QUIZ DETAILS FOR " + studentObj.getAbbreviatedName() + " -FRI JULY 28,2017-"
                        + genericBean.batch(aRecord.getExamBatch()) + "-"
                        + ((StringUtils.equalsIgnoreCase(BATCH_A, aRecord.getExamBatch()) ? batchSettings.getBatchA_Start() : batchSettings.getBatchB_Start()) + " a.m")
                        + "-ST ANTHONY SCHOOL,AJALA BSTOP,IJAIYE,"
                        + "Come with an HB Pencil for shading your answer sheet."; //
                quizSMSData.setMessage(quizMsg);
                log.info("Quiz Message :: [{}] - Length :: [{}]", quizMsg, quizMsg.length());

                String interviewMsg = "INTERVIEW DETAILS FOR " + studentObj.getAbbreviatedName() + "-SAT JULY 29,2017 from "
                        + aRecord.getInterviewSlot()
                        + "at UTOPIA COLLEGE,IJAIYE.Come formally dressed for the interview.";

                interviewSMSData.setMessage(interviewMsg);
                log.info("Interview Message :: [{}] - Length :: [{}]", interviewMsg, interviewMsg.length());
                if ((quizMsg.length() > 160) || (interviewMsg.length() > 160)) {
                    exceedMsgLength++;
                    log.info("Exceed Message Length ::: [{}]", exceedMsgLength);
                }

                phoneNos.stream().forEach((String phoneNo) -> {
                    if (StringUtils.isNotBlank(phoneNo)) {
                        quizSMSData.setId(null);
                        quizSMSData.setRecipientPhoneNo(appendCountryCode(phoneNo));
                        smsDataFacade.persist(quizSMSData);

                        interviewSMSData.setId(null);
                        interviewSMSData.setRecipientPhoneNo(appendCountryCode(phoneNo));
                        smsDataFacade.persist(interviewSMSData);
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
