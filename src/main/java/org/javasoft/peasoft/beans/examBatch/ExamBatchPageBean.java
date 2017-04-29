/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.examBatch;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.ejb.data.NotificationFacade;
import org.javasoft.peasoft.ejb.examBatch.ExamBatchFacade;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.StudentRecord;
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

    private String batch;
    
    @Getter
    private int notificationCnt;
    
    @Getter
    private int guidelineCnt;

    @EJB
    private ExamBatchFacade examBatchFacade;
    
    @EJB
    private NotificationFacade notificationFacade;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_EXAM_BATCH, pageResource)) {
            batch = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("batch");
            log.info("Batch ::: {} ", batch);
            studentRecords = examBatchFacade.findStudentRecordByBatch(batch);
            notificationCnt = notificationFacade.pendingNotificationCount();
            guidelineCnt= notificationFacade.pendingGuidelinesCount();
            super.setPageResource(appendFolderPath(EXAM_BATCH_FOLDER, LIST_EXAM_BATCH));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void resendNotification(StudentRecord aRecord) {
        try {
            
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }

    public void changeBatch(StudentRecord aRecord) {
        try {
            aRecord.setExamBatch(StringUtils.equalsIgnoreCase(batch, BATCH_A) ? BATCH_B : BATCH_A);
            examBatchFacade.edit(aRecord);
            //send New Notification
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(LIST_EXAM_BATCH);
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }

    }

    public void generateBatchListInExcel(){
    
    }
    
    public void scheduleNotification(){
    
        //get a list of pending student records
    }
    
    private void cleanup() {
        studentRecords = null;
    }
}
