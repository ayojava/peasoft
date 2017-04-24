/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.studentRecord;

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
import static org.javasoft.peasoft.constants.PeaResource.VIEW_HOME_PAGE;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.StudentRecord;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentRecordPageBean")
@ViewScoped
public class StudentRecordPageBean extends AbstractBean implements Serializable {

//add a mark button which blocks user input and handles bulk txn in hibernate    
    @Getter @Setter
    private StudentRecord studentRecord;

    @Getter
    private List<StudentRecord> studentRecords;

    @EJB
    private StudentRecordFacade studentRecordFacade;

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

    }

    public void markScores(){
    
    }
    
    public void scheduleStudent(){
    
    }
    
    private void cleanup() {

    }
}
