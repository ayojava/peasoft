/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.results;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import static org.javasoft.peasoft.constants.PeaResource.DISPLAY_DATE_FORMAT_DAYS;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.data.NotificationFacade;
import org.javasoft.peasoft.ejb.results.ResultsFacade;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.Notification;
import org.javasoft.peasoft.excel.result.ResultsListExcelReport;
import org.javasoft.peasoft.service.ResultsService;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("resultsPageBean")
@ViewScoped
public class ResultsPageBean extends AbstractBean implements Serializable {

    @Getter
    private StudentRecord studentRecord;

    @Getter
    private List<StudentRecord> studentRecords;

    @EJB
    private ResultsFacade resultsFacade;
    
    @EJB
    private NotificationFacade notificationFacade;
    
    @EJB
    private EmailDataFacade examDataFacade;

    @Getter
    private String grade;
    
    @Getter
    private int notificationCnt;
    
    private ResultsService resultsService;
    
    @Inject
    private EmailUtilBean emailUtilBean;

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_RESULTS, pageResource)) {
            grade = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("result");
            studentRecords = resultsFacade.findStudentRecordByGrade(grade);
            //find notificationCount
            super.setPageResource(appendFolderPath(RESULTS_FOLDER, LIST_RESULTS));
        } else if (StringUtils.equals(VIEW_RESULT, pageResource)) {
            super.setPageResource(appendFolderPath(RESULTS_FOLDER, VIEW_RESULT));//
        } else if (StringUtils.equals(LIST_RESULTS_OTHER, pageResource)) {
            studentRecords = resultsFacade.findStudentRecordByGrade(grade);
            //find notificationCount
            super.setPageResource(appendFolderPath(RESULTS_FOLDER, LIST_RESULTS));
        } else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void setPageResource(String pageResource, StudentRecord studentRecordObj) {
        studentRecord = studentRecordObj;
        setPageResource(pageResource);
    }

    public void changeGrade(StudentRecord aRecord){
//        try{
//            String oldGrade = aRecord.getGrade();
//            aRecord.setGrade(StringUtils.equalsIgnoreCase(oldGrade, SELECTED) ? NOT_SELECTED : SELECTED);
//            String newGrade = aRecord.getGrade();
//            log.info("Old Grade :: {} ==== New Grade :: {} " , oldGrade , newGrade);
//            resultsFacade.edit(aRecord);
//            Messages.addGlobalInfo("Edit Operation Successful");
//            setPageResource(LIST_RESULTS_OTHER);
//        }catch(Exception ex){
//            log.error("An Error has Occurred :::", ex);
//            Messages.addGlobalError("An Error has Occured");
//        }
    }
    
    public void scheduleNotification(){
        resultsService = new ResultsService();
        List<Notification> notifications = notificationFacade.getPendingResultNotifications(grade);
        notifications.forEach((Notification aNotification)->{
            EmailData emailData = resultsService.generateNotificationEmail(emailUtilBean, grade, aNotification.getStudentRecord());
            examDataFacade.persist(emailData);
            
            aNotification.setResultNotification(true);
            notificationFacade.edit(aNotification);
        });
    
    }
    
    public void  generateResultListInExcel() {
        try{
            String fileName = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS) + "_" + RandomStringUtils.randomNumeric(5) + ".xls";
            List<StudentRecord> studentRecordsByMarks = resultsFacade.orderByMarks();
            ResultsListExcelReport resultsListExcelReport = new ResultsListExcelReport();
            resultsListExcelReport.populateExcelSheet("Results", fileName, studentRecordsByMarks);
            Messages.addGlobalInfo("Excel Sheet Sent");
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    private void cleanup() {
        studentRecords = null;
        grade = null;
    }
}
