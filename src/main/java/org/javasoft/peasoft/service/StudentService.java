/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.beans.core.util.SMSUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import static org.javasoft.peasoft.constants.PeaResource.SCHOOL_FOLDER;
import static org.javasoft.peasoft.constants.PeaResource.SEPARATOR;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import static org.javasoft.peasoft.utils.template.EmailTemplate.FOOTER_ENQUIRY_MAIL_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.FOOTER_SOCIAL_MEDIA_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_BOTTOM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.REGISTRATION_DETAILS_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.REGISTRATION_DETAILS_TOP__TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_HEADER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_EVEN_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_ODD_TEMPLATE;
import static org.javasoft.peasoft.utils.template.SMSTemplate.REGISTRATION_DETAILS_SMS_TEMPLATE;

/**
 *
 * @author ayojava
 */
@Slf4j
public class StudentService {
    
    public SMSData generateWelcomeSMS(SMSUtilBean smsUtilBean, Student studentObj){
        
        String smsMsg = smsUtilBean.showMessageFromTemplate(REGISTRATION_DETAILS_SMS_TEMPLATE,studentObj.getFullName(), studentObj.getEmail());
        log.info("smsMsg :: {} " , smsMsg);
        
        SMSData smsData = new SMSData();
        smsData.setMessage(smsMsg);
        smsData.setStatus(PENDING);
        smsData.setStudent(studentObj);
        return smsData;
    }
    
    public EmailData generateWelcomeEmail(Student studentObj,EmailUtilBean emailUtilBean, String schoolName){
        
        String mailSubject = emailUtilBean.showMessageFromTemplate(REGISTRATION_DETAILS_SUBJECT_TEMPLATE,studentObj.getFullName() );
        
        StringBuilder msgBody = new StringBuilder();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_HEADER_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(REGISTRATION_DETAILS_TOP__TEMPLATE,studentObj.getSurname()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE,"Identification No : ",studentObj.getIdentificationNo()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE,"Full Name : ",studentObj.getFullName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Gender : ",studentObj.getGender()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Phone No : ",studentObj.getPhoneNo()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," School Name : ",schoolName));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Class : ",studentObj.getStudentRecord().getSss()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Department : ",studentObj.getStudentRecord().getDepartment()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Parent Name : ",studentObj.getParent().getName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Occupation : ",studentObj.getParent().getOccupation()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Parent Phone No : ",studentObj.getParent().getAddressTemplate().getContactPhoneNo1()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Parent Email : ",studentObj.getParent().getAddressTemplate().getContactEmail1()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Parent Address  : ",studentObj.getParent().getAddressTemplate().getFullAddress()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(FOOTER_SOCIAL_MEDIA_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(FOOTER_ENQUIRY_MAIL_TEMPLATE));
        
        log.info("\nMail Subject :: {}\n\n " , mailSubject);
        log.info("Mail Body :: {}\n\n " , msgBody.toString());
        
        StringBuilder recipientEmails = new StringBuilder();
        recipientEmails= recipientEmails.append(studentObj.getEmail());
      
        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();
        log.info("Contact Email 1 :: {} " , contactEmail1);
        
        String contactEmail2 = studentObj.getParent().getAddressTemplate().getContactEmail2();
        log.info("Contact Email 2 :: {} " , contactEmail2);
        
        if(StringUtils.isNotBlank(contactEmail1)){
            recipientEmails= recipientEmails.append(SEPARATOR).append(contactEmail1);
        }
        if(StringUtils.isNotBlank(contactEmail2)){
            recipientEmails= recipientEmails.append(SEPARATOR).append(contactEmail2);
        }
        
        log.info("\n Recipient Emails :: {} " , recipientEmails.toString());
        EmailData emailData = new EmailData();
        emailData.setMailMessage(msgBody.toString());
        emailData.setAttachment(false);
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getFullName());
        emailData.setStatus(PENDING);
        emailData.setRecipientType(SCHOOL_FOLDER);
        return emailData;
    }
    
}
