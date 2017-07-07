/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.jobs;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.mail.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.scheduler.api.Scheduled;
import static org.javasoft.peasoft.constants.PeaResource.SENT;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.utils.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author ayojava
 */
@Slf4j
//  0 0 8/2 ? * * * Every 2 hours starting from 8am
//@Scheduled(cronExpression = "0 0 8/2 ? * * *") // Every 2 hours starting from 8am
@Scheduled(cronExpression = "0 0 0/1 ? * * *") // Every hour
//@Scheduled(cronExpression = " 0 0/10 * ? * * *") // Every 20 minutes
public class EmailJob implements Job {

    private List<EmailData> pendingEmailData;

    private EmailService emailService;

    @EJB
    private EmailDataFacade emailDataFacade;

//    @EJB
//    private EnvSettingsFacade envSettingsFacade;
    
    @Resource(name = "java:/brainChallengeSMTP")
    private Session session;

    private boolean output;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        EnvSettings envSettings = envSettingsFacade.findOne();
//        if (envSettings == null) {
//            log.error("Env Settings is pending . Check you  Configuration ");
//            return;
//        }
//        EmailSettings emailSettings = envSettings.getEmailSettings();
//        if (emailSettings == null || emailSettings.isError()) {
//            log.error("Email Settings is pending . Check you Email Configuration ");
//            return;
//        }
        log.info(" ==========  [ Loading Email JOB ] ========== ");
        if (session == null) {
            log.error("Mail Session is null. Check your configuration  ");
            return;
        }
        
        pendingEmailData = emailDataFacade.findPendingEmails();
        if (pendingEmailData.isEmpty()) {
            log.warn("====  No Pending Emails =====  ");
            return;
        }
        //
        emailService = new EmailService();
//        emailService.initEmailService("true", emailSettings.getServer(), String.valueOf(emailSettings.getPort()), emailSettings.getSender(),
//                emailSettings.getPassword(), "BrainChallenge2017");
        emailService.setMailSession(session);
        
        log.info("======= [ About To Send Email ] =======");
        pendingEmailData.forEach((EmailData data) -> {
            //log.info("\n======================================");
            if (data.isAttachment()) {
                String attachment[] = {data.getAttachmentFile()};
                output = emailService.sendHtmlMessageWithAttachment(
                        data.getMailSubject(), data.getMailMessage(), data.getRecipientName(), data.getRecipientEmail(), attachment);
            } else {
                output = emailService.sendHtmlMessage(data.getMailSubject(), data.getMailMessage(), data.getRecipientName(), data.getRecipientEmail());
            }
            if (output) {
                data.setStatus(SENT);
                emailDataFacade.edit(data);
            }
        });

    }

}
