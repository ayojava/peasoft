/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.service;

import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.beans.core.GenericBean;
import static org.javasoft.peasoft.constants.PeaResource.EXAM_BATCH_FOLDER;
import org.javasoft.peasoft.entity.data.EmailData;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class ExamBatchService {

    public EmailData generateWelcomeEmail(){
        
        GenericBean genericBean = Beans.getInstance(GenericBean.class);
        
        EmailData emailData = new EmailData();
        emailData.setAttachment(false);
        
        emailData.setRecipientType(EXAM_BATCH_FOLDER);
        return emailData;
    }
}
