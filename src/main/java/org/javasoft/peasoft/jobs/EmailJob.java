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
import static org.javasoft.peasoft.constants.PeaResource.SENT;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.settings.EnvSettingsFacade;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.settings.EmailSettings;
import org.javasoft.peasoft.entity.settings.EnvSettings;
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
@Scheduled(cronExpression = "0 0/10 * ? * * *") // Every 30 minutes
public class EmailJob implements Job {

    private List<EmailData> pendingEmailData;

    private EmailService emailService;

    @EJB
    private EmailDataFacade emailDataFacade;

    @EJB
    private EnvSettingsFacade envSettingsFacade;

    private boolean output;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EnvSettings envSettings = envSettingsFacade.findOne();
        if (envSettings == null) {
            log.error("Env Settings is pending . Check you  Configuration ");
            return;
        }
        EmailSettings emailSettings = envSettings.getEmailSettings();
        if (emailSettings == null || emailSettings.isError()) {
            log.error("Email Settings is pending . Check you Email Configuration ");
            return;
        }

        pendingEmailData = emailDataFacade.findPendingEmails();
        if (pendingEmailData.isEmpty()) {
            log.warn("====  No Pending Emails =====  ");
            return;
        }
        log.info("Pending Email Count ======= {}" , pendingEmailData.size());
        emailService = new EmailService();
        emailService.initEmailService("true", emailSettings.getServer(), String.valueOf(emailSettings.getPort()), emailSettings.getSender(),
                emailSettings.getPassword(), "BrainChallenge2017");

        if (emailService.getMailSession() == null) {
            log.error("Mail Session is null. Check your configuration  ");
            return;
        }

        pendingEmailData.forEach((EmailData data) -> {
            log.info("\n======================================");
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
