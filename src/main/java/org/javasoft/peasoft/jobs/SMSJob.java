/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.jobs;

import java.util.List;
import javax.ejb.EJB;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.scheduler.api.Scheduled;
import static org.javasoft.peasoft.constants.PeaResource.COLON;
import static org.javasoft.peasoft.constants.PeaResource.SENT;
import static org.javasoft.peasoft.constants.PeaResource.SEPARATOR;
import org.javasoft.peasoft.ejb.data.SMSDataFacade;
import org.javasoft.peasoft.ejb.settings.EnvSettingsFacade;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.settings.EnvSettings;
import org.javasoft.peasoft.entity.settings.SMSSettings;
import org.javasoft.peasoft.utils.SMSService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author ayojava
 */
@Slf4j
//@Scheduled(cronExpression = "0 0 9/2 ? * * *")
@Scheduled(cronExpression = " 0 0/20 * ? * * *") // Every 20 minutes
public class SMSJob implements Job {

    @EJB
    private EnvSettingsFacade envSettingsFacade;

    private SMSService smsService;

    @EJB
    private SMSDataFacade smsDataFacade;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(" ==========  [ Loading SMS JOB ] ========== ");
        EnvSettings envSettings = envSettingsFacade.findOne();
        if (envSettings == null) {
            log.error("Env Settings is pending . Check your  Configuration ");
            return;
        }

        SMSSettings smsSettings = envSettings.getSmsSettings();
        if (smsSettings == null || smsSettings.isError()) {
            log.error("SMS Settings is pending . Check your SMS Configuration ");
            return;
        }

        List<SMSData> pendingSMS = smsDataFacade.findPendingSMS();
        if (pendingSMS.isEmpty()) {
            log.warn("====  No Pending SMS =====  ");
            return;
        }

        smsService = new SMSService(smsSettings);
        int creditBalance = -1;//pessimistic
        try {
            String creditBalanceMsg = smsService.checkCreditBalance();
            log.info("========= Credit Balance Message :: {}", creditBalanceMsg);
            String[] split = StringUtils.split(creditBalanceMsg, COLON);
            creditBalance = Integer.parseInt(StringUtils.remove(split[1],','));
            log.info("========= Credit Balance :: {}", creditBalance);
        } catch (Exception ex) {
            log.error("An Error has Occurred while checking balance :::", ex);
        }

        if (creditBalance <= 1) {
            log.warn("====  Insufficient Credit Balance =====  ");
            return;
        }

        pendingSMS.forEach((SMSData smsDATA) -> {
            try {
                String output = smsService.sendSMSMessage(smsDATA);
                log.info(" :: Msg [ {} ] :: Msg Length [ {} ] :: Response [ {} ]", smsDATA.getMessage() , smsDATA.getMessage().length() , output);
                String responseMsg[] = StringUtils.split(output, SEPARATOR);
                smsDATA.setResponseCode(responseMsg[0]);
                smsDATA.updateResponseMessage();
                if (smsDATA.isSuccessStatus()) {
                    smsDATA.setStatus(SENT);
                    
                }
                smsDataFacade.edit(smsDATA);

            } catch (Exception ex) {
                log.error("An Error has Occurred while Sending message :::", ex);
            }
        });
    }

}
