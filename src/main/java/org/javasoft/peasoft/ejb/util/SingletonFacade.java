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
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.student.StudentFacade;
import org.javasoft.peasoft.jobs.EmailJob;
import org.javasoft.peasoft.jobs.SMSJob;
import org.javasoft.peasoft.utils.GlobalRegistry;
import org.quartz.Job;

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
    
    @Inject
    private Scheduler<Job> jobScheduler;

    @PostConstruct
    public void initialize() {
        globalRegistry = GlobalRegistry.getInstance();
        initCount();
        jobScheduler.registerNewJob(EmailJob.class);
        jobScheduler.registerNewJob(SMSJob.class);
    }
    
    private void initCount(){
        int schoolCount = schoolFacade.count();
        int studentCount = studentFacade.count();
        //log.info("School Count -> [ {} ]    Student Count -> [ {} ]" , schoolCount , studentCount);
        globalRegistry.setSchoolCount(schoolCount);
        globalRegistry.setStudentCount(studentCount);
    }
}
