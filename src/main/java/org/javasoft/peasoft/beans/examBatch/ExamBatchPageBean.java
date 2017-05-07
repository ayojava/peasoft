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
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.Notification;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.settings.BatchSettings;
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
            notificationCnt = notificationFacade.pendingNotificationCount();
            guidelineCnt = notificationFacade.pendingGuidelinesCount();
            super.setPageResource(appendFolderPath(EXAM_BATCH_FOLDER, LIST_EXAM_BATCH));
        } else if (StringUtils.equals(LIST_EXAM_BATCH_OTHER, pageResource)) {
            studentRecords = examBatchFacade.findStudentRecordByBatch(batch);
            notificationCnt = notificationFacade.pendingNotificationCount();
            guidelineCnt = notificationFacade.pendingGuidelinesCount();
            super.setPageResource(appendFolderPath(EXAM_BATCH_FOLDER, LIST_EXAM_BATCH));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void resendNotification(StudentRecord aRecord) {
        try {
            BatchSettings batchSetting = batchSettingsFacade.findOne();
            EmailData emailData = examBatchService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting);
            emailDataFacade.persist(emailData);
            Messages.addGlobalInfo("Notification Scheduled Successfully");
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void changeBatch(StudentRecord aRecord) {
        try {
            String oldBatch = aRecord.getExamBatch();
            aRecord.setExamBatch(StringUtils.equalsIgnoreCase(batch, BATCH_A) ? BATCH_B : BATCH_A);
            String newBatch = aRecord.getExamBatch();
            examBatchFacade.edit(aRecord);
            //send New Notification
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(LIST_EXAM_BATCH);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void generateBatchListInExcel() {
        if (studentRecords == null || studentRecords.isEmpty()) {
            Messages.addGlobalWarn("No Student Record Available");
            return;
        }
        try {
            batchListExcelReport = new BatchListExcelReport();
            String fileName
                    = DateFormatUtils.format(new Date(),DISPLAY_DATE_FORMAT_DAYS) + "_" + RandomStringUtils.randomNumeric(3) + "_" + batch + ".xls";
           if( !batchListExcelReport.populateExcelSheet(studentRecords, "Batch :: " + batch, fileName)){
               throw new Exception();
           }
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void scheduleNotification() {
        try {
            BatchSettings batchSetting = batchSettingsFacade.findOne();
            List<Notification> pendingNotifications = notificationFacade.getPendingNotification();
            pendingNotifications.forEach(
                    aNotification -> {
                        StudentRecord aRecord = aNotification.getStudentRecord();
                        EmailData emailData = examBatchService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting);
                        SMSData smsData = examBatchService.generateNotificationSMS(smsUtilBean, aRecord, batchSetting);

                        emailDataFacade.persist(emailData);
                        smsDataFacade.persist(smsData);

                        aNotification.setBatchNotification(true);
                        notificationFacade.edit(aNotification);
                    }
            );
            Messages.addGlobalInfo("Notifications Scheduled Successfully");
            setPageResource(LIST_EXAM_BATCH_OTHER);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void scheduleGuidelines() {
        try {
            BatchSettings batchSetting = batchSettingsFacade.findOne();
            List<Notification> pendingNotifications = notificationFacade.getPendingNotification();
            pendingNotifications.forEach(
                    aNotification -> {
                        StudentRecord aRecord = aNotification.getStudentRecord();
                        EmailData emailData = examBatchService.generateNotificationEmail(emailUtilBean, aRecord, batchSetting);
                        SMSData smsData = examBatchService.generateNotificationSMS(smsUtilBean, aRecord, batchSetting);

                        emailDataFacade.persist(emailData);
                        smsDataFacade.persist(smsData);

                        aNotification.setGuidelineNotification(true);
                        notificationFacade.edit(aNotification);
                    }
            );
            Messages.addGlobalInfo("Guidelines Scheduled Successfully");
            setPageResource(LIST_EXAM_BATCH_OTHER);
        } catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    private void cleanup() {
        studentRecords = null;
    }
}
