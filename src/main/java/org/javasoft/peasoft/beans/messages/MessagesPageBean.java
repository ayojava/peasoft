/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.messages;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import static org.javasoft.peasoft.constants.PeaResource.DISPLAY_DATE_FORMAT_DAYS;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.ejb.data.SMSDataFacade;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.excel.messaging.SMSExcelReport;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("messagesPageBean")
@ViewScoped
public class MessagesPageBean extends AbstractBean implements Serializable{
    
    @Getter
    private List<SMSData> smsData;
    
    @Getter
    private List<EmailData> emailData;
    
    @EJB
    private EmailDataFacade emailDataFacade;
    
    @EJB
    private SMSDataFacade smsDataFacade;
    
    private SMSExcelReport smsExcelReport;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
    }
    
    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(LIST_EMAILS, pageResource)) {
         emailData = emailDataFacade.findAll();
         super.setPageResource(appendFolderPath(MESSAGES_FOLDER, LIST_EMAILS));
        }else if (StringUtils.equals(LIST_SMS, pageResource)) {
        smsData = smsDataFacade.findAllPendingSMS();
         super.setPageResource(appendFolderPath(MESSAGES_FOLDER, LIST_SMS));
        }else if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }
    
    public void exportPendingSMS(){
        smsExcelReport = new SMSExcelReport();
        String fileName = DateFormatUtils.format(new Date(), DISPLAY_DATE_FORMAT_DAYS)+"_"+ RandomStringUtils.randomNumeric(5)+".xls"; 
        smsExcelReport.populateExcelSheet("SMS", fileName, smsData);     
    }
    
    private void cleanup() {
        
    }
}
