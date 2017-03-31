/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.school;

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
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.entity.brainChallenge.StudentRecord;
import org.javasoft.peasoft.entity.templates.AddressTemplate;
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
    private int disqualifiedStudents , successfulStudents , failedStudents  ;
    
    @Getter
    private int scienceStudents , artStudents , commercialStudents;
    
    @Getter
    private int ss1 , ss2 ;
    
    @EJB
    private SchoolFacade schoolFacade;

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
            super.setPageResource(appendFolderPath("school", NEW_SCHOOL));
        } else if (StringUtils.equals(EDIT_SCHOOL, pageResource)) {

            super.setPageResource(appendFolderPath("school", EDIT_SCHOOL));
        } else if (StringUtils.equals(VIEW_SCHOOL, pageResource)) {
            school = schoolFacade.fetchJoinSchoolRecord(school);
            setDisplayResults(school.getStudentRecords());
            super.setPageResource(appendFolderPath("school", VIEW_SCHOOL));
        } else if (StringUtils.equals(LIST_SCHOOLS, pageResource)) {
            schools = schoolFacade.findAll();
            super.setPageResource(appendFolderPath("school", LIST_SCHOOLS));
        }else  if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }
    
    private void setDisplayResults(List<StudentRecord> allRecords){
        disqualifiedStudents = successfulStudents = failedStudents = 0;   
        scienceStudents = artStudents = commercialStudents = 0;
        ss1 = ss2 = 0;
        allRecords.forEach(record->{
            if(StringUtils.equalsIgnoreCase(DISQUALIFIED, record.getStatus())){
                disqualifiedStudents++;
            }
            if(StringUtils.equalsIgnoreCase(SUCCESS, record.getGrade())){
                successfulStudents++;
            }
            if(StringUtils.equalsIgnoreCase(FAIL, record.getGrade())){
                failedStudents++;
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
        });
        
    }
    
    public void setPageResource(String pageResource, School schoolObj) {
        school = schoolObj;
        setPageResource(pageResource);
    }
    
    public void saveSchool(){
        try{
            schoolFacade.persist(school);
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
   
    private void cleanup(){
        schools = null;
        school = null;
    }
}
