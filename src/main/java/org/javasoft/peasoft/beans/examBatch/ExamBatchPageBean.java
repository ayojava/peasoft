/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.examBatch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.beans.core.util.SMSUtilBean;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.data.NotificationFacade;
import org.javasoft.peasoft.ejb.data.SMSDataFacade;
import org.javasoft.peasoft.ejb.examBatch.ExamBatchFacade;
import org.javasoft.peasoft.ejb.settings.BatchSettingsFacade;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.excel.batch.BatchListExcelReport;
import org.javasoft.peasoft.service.ExamBatchService;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("examBatchPageBean")
@ViewScoped
public class ExamBatchPageBean extends AbstractBean implements Serializable {

    @Getter
    private List<StudentRecord> studentRecords;

    @Getter
    private List<School> schools;

    @Getter
    private String batch;

    @Getter
    private Long notificationCnt;

    @Getter
    private Long guidelineCnt;

    @EJB
    private ExamBatchFacade examBatchFacade;

    @EJB
    private NotificationFacade notificationFacade;

    @EJB
    private BatchSettingsFacade batchSettingsFacade;

    @Inject
    private EmailUtilBean emailUtilBean;

    @Inject
    private SMSUtilBean smsUtilBean;

    private ExamBatchService examBatchService;

    @EJB
    private SMSDataFacade smsDataFacade;

    @EJB
    private EmailDataFacade emailDataFacade;

    private BatchListExcelReport batchListExcelReport;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        examBatchService = new ExamBatchService();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_EXAM_BATCH, pageResource)) {
            batch = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("batch");
            log.info("Batch ::: {} ", batch);
            studentRecords = examBatchFacade.findStudentRecordByBatch(batch);
            notificationCnt = notificationFacade.pendingBatchNotificationCount(batch);
            //guidelineCnt = notificationFacade.pendingGuidelinesCount();
            super.setPageResource(appendFolderPath(EXAM_BATCH_FOLDER, LIST_EXAM_BATCH));
        } else if (StringUtils.equals(LIST_EXAM_BATCH_OTHER, pageResource)) {
            studentRecords = examBatchFacade.findStudentRecordByBatch(batch);
            notificationCnt = notificationFacade.pendingBatchNotificationCount(batch);
            //guidelineCnt = notificationFacade.pendingGuidelinesCount();
            super.setPageResource(appendFolderPath(EXAM_BATCH_FOLDER, LIST_EXAM_BATCH));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void setPageResource(String pageResource, StudentRecord studentRecord) {

    }

    public void resendNotification(StudentRecord aRecord) {
//        try {
//            BatchSettings batchSetting = batchSettingsFacade.findOne();
//            EmailData emailData = examBatchService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting,
//                    registry.getInitFilePath() + GUIDELINES_FOLDER + File.separator + "guideline.pdf");
//            emailDataFacade.persist(emailData);
//            Messages.addGlobalInfo("Notification Scheduled Successfully");
//        } catch (Exception ex) {
//            log.error("An Error has Occurred :::", ex);
//            Messages.addGlobalError("An Error has Occured");
//        }
    }

    public void scheduleBatchClass() {
        try {
            int k = 0;
            for (StudentRecord aRecord : studentRecords) {
                k++;
                if ((k == 1) || (k <= 30)) {
                    aRecord.setExamClass(SS3A);
                } else if ((k == 31) || (k <= 60)) {
                    aRecord.setExamClass(SS3B);
                } else if ((k == 61) || (k <= 80)) {
                    aRecord.setExamClass(SS1B);
                } else if ((k == 81) || (k <= 110)) {
                    aRecord.setExamClass(SS3C);
                } else if ((k == 111) || (k <= 140)) {
                    aRecord.setExamClass(SS3D);
                } else if ((k == 141) || (k <= 170)) {
                    aRecord.setExamClass(SS2A);
                } else if ((k == 171) || (k <= 200)) {
                    aRecord.setExamClass(SS2B);
                } else if ((k == 201) || (k <= 230)) {
                    aRecord.setExamClass(SS2C);
                } else if ((k == 231) || (k <= 360)) {
                    aRecord.setExamClass(HALL);
                }
                examBatchFacade.edit(aRecord);
            }
            Messages.addGlobalInfo("Schedule successful");
            setPageResource(LIST_EXAM_BATCH_OTHER);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void changeBatch(StudentRecord aRecord) {
        try {
            String oldBatch = aRecord.getExamBatch();
            aRecord.setExamBatch(StringUtils.equalsIgnoreCase(oldBatch, BATCH_A) ? BATCH_B : BATCH_A);
            String newBatch = aRecord.getExamBatch();
            log.info("Old Batch :: {} ==== New Batch :: {} ", oldBatch, newBatch);
            examBatchFacade.edit(aRecord);
            //send New Notification
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(LIST_EXAM_BATCH_OTHER);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void generateBatchListInExcel() {
        //run a groupBy class , surname
        if (studentRecords == null || studentRecords.isEmpty()) {
            Messages.addGlobalWarn("No Student Record Available");
            return;
        }
        try {
            batchListExcelReport = new BatchListExcelReport();
            String fileName
                    = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS) + "_" + RandomStringUtils.randomNumeric(3) + "_" + batch + ".xls";
            if (!batchListExcelReport.populateExcelSheet(studentRecords, "Batch " + batch, fileName)) {
                throw new Exception();
            }
            Messages.addGlobalInfo("Batch List Successfully Generated");
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    //deprecated of no use again 
    public void scheduleNotification() {
        try {
//            BatchSettings batchSetting = batchSettingsFacade.findOne();
//            List<Notification> pendingNotifications = notificationFacade.getPendingBatchNotification(batch);
//
//            pendingNotifications.forEach(
//                    aNotification -> {
//                        StudentRecord aRecord = aNotification.getStudentRecord();
//                        EmailData emailData = examBatchService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting,
//                                registry.getInitFilePath() + GUIDELINES_FOLDER + File.separator + "guideline.pdf");
//                        emailDataFacade.persist(emailData);
//
//                        Student studentObj = aRecord.getStudent();
//
//                        HashSet<String> phoneNos = new HashSet<>();
//                        phoneNos.add(studentObj.getPhoneNo());
//                        phoneNos.add(studentObj.getOtherPhoneNo());
//                        phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
//                        phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());
//
//                        SMSData smsData = examBatchService.generateNotificationSMS(smsUtilBean, aRecord.getExamBatch(), batchSetting, studentObj);
//
//                        phoneNos.stream().forEach((String phoneNo) -> {
//                            if (StringUtils.isNotBlank(phoneNo)) {
//                                smsData.setId(null);
//                                smsData.setRecipientPhoneNo(appendCountryCode(phoneNo));
//                                smsDataFacade.persist(smsData);
//                            }
//                        });
//
//                        aNotification.setBatchNotification(true);
//                        notificationFacade.edit(aNotification);
//                    }
//            );
//            Messages.addGlobalInfo("Notifications Scheduled Successfully");
//            setPageResource(LIST_EXAM_BATCH_OTHER);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void scheduleGuidelines() {
        try {
//            BatchSettings batchSetting = batchSettingsFacade.findOne();
//            List<Notification> pendingGuidelines = notificationFacade.getPendingGuidelines();
//            pendingGuidelines.forEach(
//                    aNotification -> {
//                        StudentRecord aRecord = aNotification.getStudentRecord();
//                        EmailData emailData = examBatchService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting);
//                        SMSData smsData = examBatchService.generateNotificationSMS(smsUtilBean, aRecord, batchSetting);
//
//                        emailDataFacade.persist(emailData);
//                        smsDataFacade.persist(smsData);
//
//                        aNotification.setGuidelineNotification(true);
//                        notificationFacade.edit(aNotification);
//                    }
//            );
//            Messages.addGlobalInfo("Guidelines Scheduled Successfully");
//            setPageResource(LIST_EXAM_BATCH_OTHER);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    private void cleanup() {
        studentRecords = null;
    }
}

//Adeola Adeku...saka junction beside keystone bank, first turning on my right , second building to the left
/*

 <c:if test="#{examBatchPageBean.notificationCnt gt 0}">
                        <p:spacer width="10px"/>
                        <p:commandButton styleClass="pnx-round-button" id="sendNotificationBtn" value="Notifications [#{examBatchPageBean.notificationCnt}]" 
                                         ajax="false" update="@form" action="#{examBatchPageBean.scheduleNotification()}" 
                                         icon="fa fa-fw fa-bell-o" title="Schedule Notification"/> 
                    </c:if>
                    
                    <c:if test="#{false}">
                        <p:spacer width="10px"/>
                        <p:commandButton styleClass="pnx-round-button" id="sendGuidelineBtn" value="Guidelines [#{examBatchPageBean.guidelineCnt}]" 
                                         ajax="false" update="@form" action="#{examBatchPageBean.scheduleGuidelines()}" 
                                         icon="fa fa-fw fa-bell-o" title="Schedule Guideline"/> 
                    </c:if>

*/
