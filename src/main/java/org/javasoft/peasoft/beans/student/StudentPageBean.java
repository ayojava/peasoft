/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.student;

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
import static org.javasoft.peasoft.constants.PeaResource.NEW_SCHOOL;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.entity.brainChallenge.Parent;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.entity.brainChallenge.Student;
import org.javasoft.peasoft.entity.brainChallenge.StudentRecord;
import org.javasoft.peasoft.entity.templates.AddressTemplate;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentPageBean")
@ViewScoped
public class StudentPageBean extends AbstractBean implements Serializable{
    
    @Getter @Setter
    private StudentRecord studentRecord;
    
    @Getter @Setter
    private Parent parent;
   
    @Getter @Setter
    private Student student;
    
    @Getter @Setter
    private School school;
    
    @Getter
    private List<Student> students;
    
    @Getter
    private List<School> schools;
    
    @EJB
    private SchoolFacade schoolFacade;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }
    
    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_STUDENT, pageResource)) {
            studentRecord = new StudentRecord();
            parent = new Parent();
            AddressTemplate addressTemplate = new AddressTemplate();
            parent.setAddressTemplate(addressTemplate);
        }
    }
    
    public void saveStudent(){
        
    }
    
    public void editStudent(){
        
    }
    
    private void cleanup(){
        school = null;
    }
}
