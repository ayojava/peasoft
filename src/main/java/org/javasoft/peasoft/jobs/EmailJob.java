/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.jobs;

import javax.annotation.Resource;
import javax.mail.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author ayojava
 */
@Slf4j
@Scheduled(cronExpression = "0 0/7 * * * ?")
public class EmailJob implements Job{

    @Resource(name = "java:/gmailSMTP")
    private Session session;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("======= Called EmailJob ========= {}" ,session); 
    }
    
}
