/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.student;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.entity.brainChallenge.Student;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentPageBean")
@ViewScoped
public class StudentPageBean extends AbstractBean implements Serializable{
   
    @Getter @Setter
    private Student student;
    
    @Getter
    private List<Student> students;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }
    
    @Override
    public void setPageResource(String pageResource) {
        
    }
    
    public void saveStudent(){
        
    }
    
    public void editStudent(){
        
    }
    
    private void cleanup(){
        
    }
}
