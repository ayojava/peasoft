/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.GenericBean;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.beans.core.util.SMSUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.SEPARATOR;
import static org.javasoft.peasoft.constants.PeaResource.STUDENT_FOLDER;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import static org.javasoft.peasoft.utils.template.EmailTemplate.HEADER_CLOSE_DIV_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.HEADER_OPEN_DIV_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_BOTTOM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_DISREGARD_EMAIL_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_FACEBOOK_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_INSTAGRAM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_REVERT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_TWITTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_WEBSITE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.REGISTRATION_DETAILS_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.REGISTRATION_DETAILS_TOP__TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_EVEN_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_ODD_TEMPLATE;
import static org.javasoft.peasoft.utils.template.SMSTemplate.REGISTRATION_DETAILS_SMS_TEMPLATE;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class StudentService {
    
    public SMSData generateWelcomeSMS(SMSUtilBean smsUtilBean, Student studentObj){
        
        String smsMsg = smsUtilBean.showMessageFromTemplate(REGISTRATION_DETAILS_SMS_TEMPLATE,studentObj.getSurnameAndFirstName(),
                studentObj.getEmail());
        log.info("smsMsg :: [ {} ] :: Length :: [ {} ] " , smsMsg , smsMsg.length());
        
        SMSData smsData = new SMSData();
        smsData.setMessage(smsMsg);
        //smsData.setStatus(PENDING);
        
        smsData.setStudent(studentObj);
        return smsData;
    }
    
    public EmailData generateWelcomeEmail(Student studentObj,EmailUtilBean emailUtilBean, String schoolName){
        
        GenericBean genericBean = Beans.getInstance(GenericBean.class);
        
        String mailSubject = emailUtilBean.showMessageFromTemplate(REGISTRATION_DETAILS_SUBJECT_TEMPLATE,studentObj.getFullName() );
        //log.info("Mail Subject :: {}" , mailSubject);
        
        StringBuilder msgBody = new StringBuilder();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(REGISTRATION_DETAILS_TOP__TEMPLATE,studentObj.getFullName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE,"Identification No : ",studentObj.getIdentificationNo()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE,"Full Name : ",studentObj.getFullName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Gender : ",genericBean.gender(studentObj.getGender())));
        
        String phoneNos = studentObj.getPhoneNo();
        if(StringUtils.isNotBlank(studentObj.getOtherPhoneNo())){
            phoneNos = phoneNos + " , " + studentObj.getOtherPhoneNo();
        }
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Phone No : ",phoneNos));
        phoneNos=StringUtils.EMPTY;
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," School Name : ",schoolName));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Class : ",studentObj.getStudentRecord().getSss()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Department : ",genericBean.department(studentObj.getStudentRecord().getDepartment())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Parent Name : ",studentObj.getParent().getName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Occupation : ",studentObj.getParent().getOccupation()));
        
        phoneNos= studentObj.getParent().getAddressTemplate().getContactPhoneNo1();
        if(StringUtils.isNotBlank(studentObj.getParent().getAddressTemplate().getContactPhoneNo2())){
           phoneNos = phoneNos + " , " + studentObj.getParent().getAddressTemplate().getContactPhoneNo2();
        }
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Parent Phone No(s) : ",phoneNos));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE," Parent Email : ",studentObj.getParent().getAddressTemplate().getContactEmail1()));
        //msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE," Parent Address  : ",studentObj.getParent().getAddressTemplate().getFullAddress()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_ENQUIRY_TEMPLATE));
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_BRAINCHALLENGE_EMAIL_TEMPLATE));
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_BRAINCHALLENGE_TELEPHONE_TEMPLATE));
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_BRAINCHALLENGE_WEBSITE_TEMPLATE));
//        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_REVERT_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_FOOTNOTE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_DISREGARD_EMAIL_TEMPLATE));
        
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_FACEBOOK_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_INSTAGRAM_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_TWITTER_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_WEBSITE_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_FOOTER_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_CLOSE_DIV_TEMPLATE));
        
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(FOOTER_SOCIAL_MEDIA_TEMPLATE));
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(FOOTER_ENQUIRY_MAIL_TEMPLATE));
        /*
        log.info("\nMail Subject :: {}\n\n " , mailSubject);
        */
        //log.info("Mail Body :: {}\n\n " , msgBody.toString());
        
        StringBuilder recipientEmails = new StringBuilder();
        recipientEmails= recipientEmails.append(studentObj.getEmail());
      
        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();
       // log.info("Contact Email 1 :: {} " , contactEmail1);
                
        if(StringUtils.isNotBlank(contactEmail1) && !StringUtils.equalsIgnoreCase(studentObj.getEmail(), contactEmail1)){
            recipientEmails= recipientEmails.append(SEPARATOR).append(contactEmail1);
        }

        //log.info("\n Recipient Emails :: {} " , recipientEmails.toString());
        EmailData emailData = new EmailData();
        emailData.setMailMessage(msgBody.toString());
        emailData.setAttachment(false);
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getFullName());
        
        emailData.setMailSubject(mailSubject);
        emailData.setRecipientType(STUDENT_FOLDER);
        return emailData;
    }
    
}
