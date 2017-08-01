/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.service;

import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.beans.core.GenericBean;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.SCHOOL_FOLDER;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import static org.javasoft.peasoft.utils.template.EmailTemplate.EXAMINATION_CENTRE_DETAILS_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.EXAMINATION_CENTRE_DETAILS_TOP_TEMPLATE;
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
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_DIRECTION_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_DISREGARD_EMAIL_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_ENQUIRY_TEMPLATE;
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
import static org.javasoft.peasoft.utils.template.EmailTemplate.SCHOOL_RESULT_DETAILS_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.SCHOOL_RESULT_DETAILS_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_EVEN_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_ODD_TEMPLATE;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SchoolService {
    
    public EmailData generateStudentRecordsBySchool(EmailUtilBean emailUtilBean,BatchSettings batchSettings,int enrolledStudents
    , School schoolObj){
        GenericBean genericBean = Beans.getInstance(GenericBean.class);
        String mailSubject = emailUtilBean.showMessageFromTemplate(EXAMINATION_CENTRE_DETAILS_SUBJECT_TEMPLATE,schoolObj.getName());
        
        StringBuilder msgBody = new StringBuilder();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(EXAMINATION_CENTRE_DETAILS_TOP_TEMPLATE, schoolObj.getName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "Registered Students : ",String.valueOf(enrolledStudents)));
        
        School examCentre = batchSettings.getExamCentre();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " QUIZ ( Venue ) : ", examCentre.getName().toUpperCase()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Address : ", examCentre.getAddressTemplate().getFullAddress().toUpperCase()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Date : ", genericBean.formatFullDate(batchSettings.getExamDate())));
        
        School interviewCentre = batchSettings.getInterviewCentre();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " INTERVIEW ( Venue ) : ", interviewCentre.getName().toUpperCase()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Address : ", interviewCentre.getAddressTemplate().getFullAddress().toUpperCase()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Date : ", genericBean.formatFullDate(batchSettings.getInterviewDate())));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_DIRECTION_FOOTNOTE_TEMPLATE));
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
        
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_FOOTER_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_CLOSE_DIV_TEMPLATE));
        
        EmailData emailData = new EmailData();
        emailData.setRecipientEmail(schoolObj.getAddressTemplate().getContactEmail1());
        emailData.setRecipientID(schoolObj.getId());
        emailData.setRecipientName(schoolObj.getName());
        emailData.setAttachment(true);
        emailData.setRecipientType(SCHOOL_FOLDER);
        emailData.setMailSubject(mailSubject);
        emailData.setMailMessage(msgBody.toString());
        return emailData;
    }
    
    public EmailData generateResultBySchoolEmail(EmailUtilBean emailUtilBean, School schoolObj ,int totalStudents ,int selectedStudents,
            int notSelectedStudents,int artStudents, int scienceStudents , int commercialStudents , int ss1 , int ss2 , String filePath ){
        
        String mailSubject = emailUtilBean.showMessageFromTemplate(SCHOOL_RESULT_DETAILS_SUBJECT_TEMPLATE,schoolObj.getName() );
        
        StringBuilder msgBody = new StringBuilder();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(SCHOOL_RESULT_DETAILS_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE,"Art Students  : ", String.valueOf(artStudents)));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE,"Commercial Students : ", String.valueOf(commercialStudents)));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE,"Science Students  : ", String.valueOf(scienceStudents)));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE,"S.S 1 Students : ", String.valueOf(ss1)));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE,"S.S 2 Students  : ", String.valueOf(ss2)));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE,"Selected  Students : ", String.valueOf(selectedStudents)));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE,"Not Selected Students  : ", String.valueOf(notSelectedStudents)));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE,"Total  Students : ", String.valueOf(totalStudents)));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
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
        
        EmailData emailData = new EmailData();
        
        emailData.setMailMessage(msgBody.toString());
        emailData.setAttachment(true);
        emailData.setAttachmentFile(filePath);
        emailData.setMailSubject(mailSubject);
        emailData.setRecipientEmail(schoolObj.getAddressTemplate().getContactEmail1()); 
        emailData.setRecipientID(schoolObj.getId());
        emailData.setRecipientName(schoolObj.getName());
        emailData.setRecipientType(SCHOOL_FOLDER);
        return emailData;
    }
}
