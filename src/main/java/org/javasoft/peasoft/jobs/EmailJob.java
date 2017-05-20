/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.jobs;

import java.util.List;
import javax.ejb.EJB;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.javasoft.peasoft.ejb.settings.EnvSettingsFacade;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.settings.EmailSettings;
import org.javasoft.peasoft.utils.EmailService;
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

    private List<EmailData> emailData;
    
    private EmailService emailService;
    
    @EJB
    private EnvSettingsFacade envSettingsFacade;
        
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EmailSettings emailSettings  = envSettingsFacade.findOne().getEmailSettings();
        if(emailSettings.isError()){
            log.error("Email Settings is pending . Check you Email Configuration ");
            return;
        }
        emailService  = new EmailService();
        emailService.initEmailService("true", emailSettings.getServer(), String.valueOf(emailSettings.getPort()), emailSettings.getSender(),
                emailSettings.getPassword(), "BrainChallenge2017");
    }
    
}
