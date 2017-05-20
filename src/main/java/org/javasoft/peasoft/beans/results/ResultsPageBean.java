/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.results;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.ejb.results.ResultsFacade;
import org.javasoft.peasoft.entity.core.StudentRecord;
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

    @Getter
    private String grade;

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_RESULTS, pageResource)) {
            grade = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("result");
            studentRecords = resultsFacade.findStudentRecordByGrade(grade);
            super.setPageResource(appendFolderPath(RESULTS_FOLDER, LIST_RESULTS));
        } else if (StringUtils.equals(VIEW_RESULT, pageResource)) {
            super.setPageResource(appendFolderPath(RESULTS_FOLDER, VIEW_RESULT));//
        } else if (StringUtils.equals(LIST_RESULTS_OTHER, pageResource)) {
            studentRecords = resultsFacade.findStudentRecordByGrade(grade);
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
        try{
            String oldGrade = aRecord.getGrade();
            aRecord.setGrade(StringUtils.equalsIgnoreCase(oldGrade, SELECTED) ? NOT_SELECTED : SELECTED);
            String newGrade = aRecord.getGrade();
            log.info("Old Grade :: {} ==== New Grade :: {} " , oldGrade , newGrade);
            resultsFacade.edit(aRecord);
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(LIST_RESULTS_OTHER);
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    
    private void cleanup() {
        studentRecords = null;
    }
}
