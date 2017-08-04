/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.util;

import java.util.HashSet;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.student.StudentFacade;
import org.javasoft.peasoft.ejb.studentRecord.StudentRecordFacade;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.jobs.EmailJob;
import org.javasoft.peasoft.jobs.SMSJob;
import org.javasoft.peasoft.service.StudentService;
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
    private StudentRecordFacade studentRecordFacade;

    @EJB
    private StudentFacade studentFacade;

    private HashSet<String> phoneNos;

    @Inject
    private Scheduler<Job> jobScheduler;

    @Inject
    private EmailUtilBean emailUtilBean;

    private StudentService studentService;

    @PostConstruct
    public void initialize() {
        globalRegistry = GlobalRegistry.getInstance();
        initCount();
        jobScheduler.registerNewJob(EmailJob.class);
        jobScheduler.registerNewJob(SMSJob.class);
        // handleBatchJob(); not working 
        // checkSMSCount();
        
    }

    private void initCount() {
        int schoolCount = schoolFacade.count();
        int studentCount = studentFacade.count();
        //log.info("School Count -> [ {} ]    Student Count -> [ {} ]" , schoolCount , studentCount);
        globalRegistry.setSchoolCount(schoolCount);
        globalRegistry.setStudentCount(studentCount);

        
    }

    private void checkSMSCount() {
        phoneNos = new HashSet<>();
        studentRecordFacade.findAll().stream().forEach((aRecord)->{
            Student studentObj = aRecord.getStudent();
            phoneNos.add(studentObj.getPhoneNo());
            phoneNos.add(studentObj.getOtherPhoneNo());
            phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo1());
            phoneNos.add(studentObj.getParent().getAddressTemplate().getContactPhoneNo2());
        });
        log.info("Total Phone Nos ::: {}" ,phoneNos.size());
        
        log.info("MTN Phone Nos ::: {}" , phoneNos.stream().filter(p->(
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0703")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0706")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0803")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0806")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0810")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0813")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0814")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0816")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0903")||
                StringUtils.substring(p, 0,4).equalsIgnoreCase("0906")
                )).count());
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
