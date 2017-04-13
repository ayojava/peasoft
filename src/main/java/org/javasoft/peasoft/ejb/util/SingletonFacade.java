/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.util;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.student.StudentFacade;
import org.javasoft.peasoft.utils.GlobalRegistry;

/**
 *
 * @author ayojava
 */
@Slf4j
@Startup
@Singleton
@LocalBean
public class SingletonFacade {
    
    private GlobalRegistry globalRegistry;
    
    @EJB
    private SchoolFacade schoolFacade;
    
    @EJB
    private StudentFacade studentFacade;

    @PostConstruct
    public void initialize() {
        globalRegistry = GlobalRegistry.getInstance();
        initCount();
    }
    
    private void initCount(){
        int schoolCount = schoolFacade.count();
        int studentCount = studentFacade.count();
        globalRegistry.setSchoolCount(schoolCount == 0 ? 1 : schoolCount);
        globalRegistry.setStudentCount(studentCount == 0 ? 1 : studentCount);
    }
}
