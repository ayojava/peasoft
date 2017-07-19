/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.school;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
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
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.templates.AddressTemplate;
import org.javasoft.peasoft.service.SchoolService;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("schoolPageBean")
@ViewScoped
public class SchoolPageBean extends AbstractBean implements Serializable {

    @Getter @Setter
    private School school;

    @Getter
    private List<School> schools;
    
    @Getter
    private List<StudentRecord> allRecords;
   
    @Getter
    private int disqualifiedStudents , selectedStudents , notSelectedStudents  ;
    
    @Getter
    private int scienceStudents , artStudents , commercialStudents;
    
    @Getter
    private int ss1 , ss2 ,totalStudents;
    
    @EJB
    private SchoolFacade schoolFacade;
    
    @EJB
    private StudentRecordFacade studentRecordFacade;
    
    @Inject
    private EmailUtilBean emailUtilBean;
    
    @EJB
    private EmailDataFacade emailDataFacade;
    
    private SchoolService schoolService;
    
    

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_SCHOOL, pageResource)) {
            school = new School();
            AddressTemplate addressTemplate = new AddressTemplate();
            school.setAddressTemplate(addressTemplate);
            super.setPageResource(appendFolderPath(SCHOOL_FOLDER, NEW_SCHOOL));
        } else if (StringUtils.equals(EDIT_SCHOOL, pageResource)) {
            super.setPageResource(appendFolderPath(SCHOOL_FOLDER, EDIT_SCHOOL));
        } else if (StringUtils.equals(VIEW_SCHOOL, pageResource)) {
            //school = schoolFacade.fetchJoinSchoolRecord(school);
            allRecords = studentRecordFacade.orderByMarks(school);
            setDisplayResults();
            super.setPageResource(appendFolderPath(SCHOOL_FOLDER, VIEW_SCHOOL));
        } else if (StringUtils.equals(LIST_SCHOOLS, pageResource)) {
            schools = schoolFacade.findAll("name");
            super.setPageResource(appendFolderPath(SCHOOL_FOLDER, LIST_SCHOOLS));
        }else  if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }
    
    public void scheduleInterviewAndBatchTimings(){
        try{
            studentRecordFacade.editStudentTimings(school.getAssignedBatch(), school.getInterviewSlot(), allRecords);
            Messages.addGlobalInfo("Schedule Operation Successful");
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    
    private void setDisplayResults(){
        totalStudents = allRecords.size();
        disqualifiedStudents = selectedStudents = notSelectedStudents = 0;   
        scienceStudents = artStudents = commercialStudents = 0;
        ss1 = ss2 = 0;
        allRecords.forEach(record->{
            if(StringUtils.equalsIgnoreCase(DISQUALIFIED, record.getStatus())){
                disqualifiedStudents++;
            }
            if(StringUtils.equalsIgnoreCase(SELECTED, record.getGrade())){
                selectedStudents++;
            }
            if(StringUtils.equalsIgnoreCase(NOT_SELECTED, record.getGrade())){
                notSelectedStudents++;
            }
            if(StringUtils.equalsIgnoreCase(ARTS, record.getDepartment())){
                artStudents++;
            }
            if(StringUtils.equalsIgnoreCase(SCIENCE, record.getDepartment())){
                scienceStudents++;
            }
            if(StringUtils.equalsIgnoreCase(COMMERCIAL, record.getDepartment())){
                commercialStudents++;
            }
            if(StringUtils.equalsIgnoreCase(SSS1, record.getSss())){
                ss1++;
            }
            if(StringUtils.equalsIgnoreCase(SSS2, record.getSss())){
                ss2++;
            }
        });
        
    }
    
    public void setPageResource(String pageResource, School schoolObj) {
        school = schoolObj;
        setPageResource(pageResource);
    }
    
    public void saveSchool(){
        try{
            if(schoolFacade.schoolAlreadyExists(school)){
                Messages.addGlobalError("School Details Already Exists");
                return;
            }
            
            schoolFacade.saveSchool(school);
            Messages.addGlobalInfo("Save Operation Successful");
            setPageResource(LIST_SCHOOLS);
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    public void editSchool(){
        try{
            schoolFacade.edit(school);
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(LIST_SCHOOLS);
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
    
    public void generateAllSchoolsList(){
        if(schools == null || schools.isEmpty()){
            Messages.addGlobalWarn("No School Available");
            return;
        }
        String fileName = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS)+"_"+ RandomStringUtils.randomNumeric(5)+".xls"; 
        schoolFacade.asyncSchoolListExcelDocument(fileName);
        
        Messages.addGlobalInfo("Excel Sheet In Progress");
    }
    
    public void generateResultBySchool(){
        String fileName = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS)+"_"+ RandomStringUtils.randomNumeric(5)+".xls"; 
        schoolFacade.asyncSchoolAndStudentsRecordsExcelDocument(fileName, school,allRecords);
        schoolService = new SchoolService();
        
        String filePath = registry.getInitFilePath()+ File.separator + SCHOOL_FOLDER+ File.separator + fileName;
        EmailData emailData = schoolService.generateResultBySchoolEmail(emailUtilBean, school,totalStudents,selectedStudents, notSelectedStudents, 
                artStudents, scienceStudents, commercialStudents, ss1, ss2, filePath);
        emailDataFacade.persist(emailData);
        Messages.addGlobalInfo("Excel Sheet In Progress");
    }
    
    public void generateBatchBySchool(){
    
    }
    
    
   
    private void cleanup(){
        schools = null;
        school = null;
    }
}
