/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.oralInterview;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import static org.javasoft.peasoft.constants.PeaResource.DISPLAY_DATE_FORMAT_DAYS;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.excel.interviewSlot.InterviewSlotExcelReport;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("oralInterviewPageBean")
@ViewScoped
public class OralInterviewPageBean extends AbstractBean implements Serializable {

    @Getter
    private String slot;

    @EJB
    private StudentRecordFacade studentRecordFacade;

    @Getter
    private List<StudentRecord> studentRecords;
    
    @Getter @Setter
    private StudentRecord studentRecord;
    
    private InterviewSlotExcelReport interviewSlotExcelReport;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_ORAL_INTERVIEW, pageResource)) {
            slot = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("slot");
            studentRecords = studentRecordFacade.findAllBySlot(slot);
            super.setPageResource(appendFolderPath(ORAL_INTERVIEW_FOLDER, LIST_ORAL_INTERVIEW));
        } else if (StringUtils.equals(LIST_ORAL_INTERVIEW_OTHER, pageResource)) {
            log.info("Slot ::: {}" , slot);
            studentRecords = studentRecordFacade.findAllBySlot(slot);
            super.setPageResource(appendFolderPath(ORAL_INTERVIEW_FOLDER, LIST_ORAL_INTERVIEW));
        }else if(StringUtils.equals(EDIT_ORAL_INTERVIEW, pageResource)){
            super.setPageResource(appendFolderPath(ORAL_INTERVIEW_FOLDER, EDIT_ORAL_INTERVIEW));
        }else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }

    public void setPageResource(String pageResource, StudentRecord studentRecord) {
        this.studentRecord = studentRecord;
        setPageResource(pageResource);
    }

    public void generateInterviewListInExcel() {
        if (studentRecords == null || studentRecords.isEmpty()) {
            Messages.addGlobalWarn("No Student Record Available");
            return;
        }
        
        try {
            interviewSlotExcelReport = new InterviewSlotExcelReport();
            String fileName
                    = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS) + "_" + RandomStringUtils.randomNumeric(3) + ".xls";
            if (!interviewSlotExcelReport.populateExcelSheet(studentRecords,  slot, fileName)) {
                throw new Exception();
            }
            Messages.addGlobalInfo("Interview List Successfully Generated");
        }catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    public void editInterviewSlot(){
        try{
            studentRecordFacade.edit(studentRecord);
            //send New Notification
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(LIST_ORAL_INTERVIEW_OTHER);
        }catch (Exception ex) {
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    private void cleanup() {

    }
}
