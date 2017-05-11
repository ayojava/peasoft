/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.beans.core.util.SMSUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_A;
import static org.javasoft.peasoft.constants.PeaResource.EXAM_BATCH_FOLDER;
import static org.javasoft.peasoft.constants.PeaResource.FULL_DATE_FORMAT;
import static org.javasoft.peasoft.constants.PeaResource.FULL_DATE_FORMAT_SMS;
import static org.javasoft.peasoft.constants.PeaResource.SEPARATOR;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import static org.javasoft.peasoft.utils.template.EmailTemplate.EXAMINATION_CENTER_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.EXAMINATION_CENTER_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.HEADER_CLOSE_DIV_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.HEADER_OPEN_DIV_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_BOTTOM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_BRAINCHALLENGE_EMAIL_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_BRAINCHALLENGE_TELEPHONE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_BRAINCHALLENGE_WEBSITE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_ENQUIRY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_FACEBOOK_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_GUIDELINE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_INSTAGRAM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_TWITTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_WEBSITE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_EVEN_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_ODD_TEMPLATE;
import static org.javasoft.peasoft.utils.template.SMSTemplate.BATCH_DETAILS_SMS_TEMPLATE;

/**
 *
 * @author ayojava
 */
@Slf4j
public class ExamBatchService {
    
    public SMSData generateNotificationSMS(SMSUtilBean smsUtilBean, StudentRecord studentRecord , BatchSettings batchSetting){
        
        Student studentObj = studentRecord.getStudent();
        String schoolDesc = studentRecord.getSchool().getName() + " " + studentRecord.getSchool().getAddressTemplate().getNearestBusStop();
        
        String startTime  = (StringUtils.equalsIgnoreCase(studentRecord.getExamBatch(), BATCH_A))? 
                batchSetting.getBatchA_Start() +" a.m" : batchSetting.getBatchB_Start() + " p.m ";
        
          String smsMsg = smsUtilBean.showMessageFromTemplate(BATCH_DETAILS_SMS_TEMPLATE, 
                studentObj.getFullName(),DateFormatUtils.format(batchSetting.getExamDate(), FULL_DATE_FORMAT_SMS),
                schoolDesc,startTime,studentRecord.getExamBatch());
        
        log.info("smsMsg :: {} " , smsMsg);
        
        SMSData smsData = new SMSData();
        smsData.setMessage(smsMsg);
        //smsData.setStatus(PENDING);
        
        smsData.setStudent(studentObj);
        return smsData;
        //  Good Day %s,The Quiz and Interview will come up on %s  at %s ,Your Exam slot starts at %s - %s. 
    }

    public EmailData generateNotificationEmail(EmailUtilBean emailUtilBean, StudentRecord studentRecord, BatchSettings batchSetting,
            String attachmentFilePath) {

        Student studentObj = studentRecord.getStudent();

        String mailSubject = emailUtilBean.showMessageFromTemplate(EXAMINATION_CENTER_SUBJECT_TEMPLATE, studentObj.getFullName());

        StringBuilder msgBody = new StringBuilder();

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(EXAMINATION_CENTER_TOP_TEMPLATE, studentObj.getFullName()));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "Identification No : ", studentObj.getIdentificationNo()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, "Full Name : ", studentObj.getFullName()));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Batch : ", studentRecord.getExamBatch()));

        String timeAlloted = "";

        if (StringUtils.equalsIgnoreCase(studentRecord.getExamBatch(), BATCH_A)) {
            timeAlloted = batchSetting.getBatchA_Start() + " a.m   - " + batchSetting.getBatchA_Stop() + " noon";
        } else {
            timeAlloted = batchSetting.getBatchB_Start() + " p.m   - " + batchSetting.getBatchB_Stop() + " p.m";
        }
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Time Alloted : ", timeAlloted));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Date  : ", DateFormatUtils.format(batchSetting.getExamDate(), FULL_DATE_FORMAT)));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Venue : ", batchSetting.getExamCentre().getName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Address  : ", batchSetting.getExamCentre().getAddressTemplate().getFullAddress()));
        //
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));
        
        //
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_GUIDELINE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_ENQUIRY_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_BRAINCHALLENGE_EMAIL_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_BRAINCHALLENGE_TELEPHONE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_BRAINCHALLENGE_WEBSITE_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_FACEBOOK_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_INSTAGRAM_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_TWITTER_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_WEBSITE_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_FOOTER_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_CLOSE_DIV_TEMPLATE));
        
        StringBuilder recipientEmails = new StringBuilder();
        recipientEmails= recipientEmails.append(studentObj.getEmail());
      
        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();
       // log.info("Contact Email 1 :: {} " , contactEmail1);
                
        if(StringUtils.isNotBlank(contactEmail1) && !StringUtils.equalsIgnoreCase(studentObj.getEmail(), contactEmail1)){
            recipientEmails= recipientEmails.append(SEPARATOR).append(contactEmail1);
        }
        //GUIDELINE_FOLDER
        EmailData emailData = new EmailData();
        emailData.setAttachment(true);
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getFullName());
        emailData.setAttachmentFile(attachmentFilePath);
        emailData.setMailSubject(mailSubject);
        emailData.setMailMessage(msgBody.toString());
        emailData.setRecipientType(EXAM_BATCH_FOLDER);
        return emailData;
    }
}
