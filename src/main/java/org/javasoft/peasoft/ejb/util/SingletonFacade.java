/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.javasoft.peasoft.batch.dto.EmailBatchDTO;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
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
        
    private HashSet<String> emailAddress ;

    @Inject
    private Scheduler<Job> jobScheduler;
    
    @Inject
    private EmailUtilBean emailUtilBean;

    @PostConstruct
    public void initialize() {
        globalRegistry = GlobalRegistry.getInstance();
        initCount();
        jobScheduler.registerNewJob(EmailJob.class);
        jobScheduler.registerNewJob(SMSJob.class);
       // handleBatchJob(); not working 
       checkEmailCount();
    }

    private void initCount() {
        int schoolCount = schoolFacade.count();
        int studentCount = studentFacade.count();
        //log.info("School Count -> [ {} ]    Student Count -> [ {} ]" , schoolCount , studentCount);
        globalRegistry.setSchoolCount(schoolCount);
        globalRegistry.setStudentCount(studentCount);
    }
    
    public void checkEmailCount(){
        emailAddress = new HashSet<>();
        
        List<EmailBatchDTO> studentEMailBatchDTO = studentFacade.findEmailBatchDTO();
                
        List<EmailBatchDTO> schoolEmailBatchDTO = schoolFacade.findEmailBatchDTO();
        
        List<EmailBatchDTO> allEmailBatchDTO = new ArrayList<>();
        
        allEmailBatchDTO.addAll(studentEMailBatchDTO);
        allEmailBatchDTO.addAll(schoolEmailBatchDTO);
        
        log.info("Students :: {} --- Schools :: {}  Total :: {} " ,studentEMailBatchDTO.size(),schoolEmailBatchDTO.size(),allEmailBatchDTO.size());
                
        allEmailBatchDTO.stream().forEach(aEmailBatchDTO->{
            emailAddress.add(aEmailBatchDTO.getEmail1());
            emailAddress.add(aEmailBatchDTO.getEmail2());
        });
        
       log.info("Size Of Email ::: {} " ,emailAddress.size());
       
    }

//    private void handleBatchJob() {
//        try {
//            JobOperator jo = BatchRuntime.getJobOperator();
//            long jid = jo.start("EmailDataJob", new Properties());
//            log.info("Job ID :::: {}",jid);
//        } catch (Exception ex) {
//            log.error("Exception :: {}",ex);
//        }
//    }
}
