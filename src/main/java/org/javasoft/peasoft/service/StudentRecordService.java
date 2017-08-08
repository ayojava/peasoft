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
import static org.javasoft.peasoft.constants.PeaResource.BATCH_A;
import static org.javasoft.peasoft.constants.PeaResource.SEPARATOR;
import static org.javasoft.peasoft.constants.PeaResource.STUDENT_RECORD_FOLDER;
import static org.javasoft.peasoft.email.ParentInviteEmail.LIST_1;
import static org.javasoft.peasoft.email.ParentInviteEmail.LIST_2;
import static org.javasoft.peasoft.email.ParentInviteEmail.LIST_3;
import static org.javasoft.peasoft.email.ParentInviteEmail.LIST_4;
import static org.javasoft.peasoft.email.ParentInviteEmail.LIST_5;
import static org.javasoft.peasoft.email.ParentInviteEmail.LIST_6;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARAGRAPH_1;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARAGRAPH_2;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARAGRAPH_3;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARAGRAPH_4;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARAGRAPH_5;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARAGRAPH_6;
import static org.javasoft.peasoft.email.ParentInviteEmail.PARENT_MAIL_SUBJECT;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import static org.javasoft.peasoft.utils.template.EmailTemplate.BRAINCHALLENGE_ACADEMY_MORE_INFO_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.BRAINCHALLENGE_ACADEMY_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.BRAINCHALLENGE_ACADEMY_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.EXAMINATION_CENTER_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.EXAMINATION_CENTER_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.HEADER_CLOSE_DIV_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.HEADER_OPEN_DIV_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_BOTTOM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.INNER_TABLE_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_CLOSE_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_DIRECTION_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_DISREGARD_EMAIL_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_FACEBOOK_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_HOSTING_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_INSTAGRAM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_ORAL_INTERVIEW_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_QUIZ_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_REGISTRATION_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_RESULT_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_REVERT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_TIMETABLE_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_TOPIC_LIST_FOOTNOTE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_TWITTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_WEBSITE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_EVEN_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_ODD_TEMPLATE;
import static org.javasoft.peasoft.utils.template.SMSTemplate.EXAM_DETAILS_SMS_TEMPLATE;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class StudentRecordService {
    
    private String quiz_Info ="Instructions For Quiz (Screening Test)";
    
    private String quiz_list_1="You are to come along with  HB pencil, Biro ( Blue or Black ) and Eraser.The pencil will be used to shade your answers on the answer sheet, while you write your names with the Biro.";
    
    private String quiz_list_2="The Time alloted for the Quiz is One Hour (60 minutes) for 60 questions (Maths 20 questions, English 20 questions , Current Affairs 10 questions , I.C.T 10 questions) . ";
    
    private String quiz_list_3="Ensure you are available at the alloted time,You will not be allowed to join another Batch if you miss your time slot , and no extra time will be alloted if you arrive late.";
    
    private String interview_Info ="Instructions For Oral Interview";
    
    private String interview_list_1="All Students must be FORMALLY DRESSED (Not in Native attire , School uniform or slippers )for the interview. Marks will be alloted for your dressing .";
    
    private String interview_list_2="The Interview session will be between 10 - 15 minutes. Ensure you arrive at the venue on time.";
    
    
    
    
    public SMSData generateExamDetailsNotification(SMSUtilBean smsUtilBean,String fullName , String email){
    
        //Student studentObj =  studentRecord.getStudent();
        SMSData smsData = new SMSData();
        //EXAM_DETAILS_SMS_TEMPLATE
        String smsMsg = smsUtilBean.showMessageFromTemplate(EXAM_DETAILS_SMS_TEMPLATE, fullName,email);
        smsData.setMessage(smsMsg);
        return smsData;
    }
    
    public EmailData generateAcademyInteractiveSessionNotificationForParents(StudentRecord studentRecord,EmailUtilBean emailUtilBean){
        Student studentObj =  studentRecord.getStudent();
                
        StringBuilder msgBody = new StringBuilder();
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));
        msgBody = msgBody.append(" Dear Parent ,<br/>" );
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(PARAGRAPH_1));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(PARAGRAPH_2));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(PARAGRAPH_3));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(PARAGRAPH_4));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(PARAGRAPH_5)).append("<div>");
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "", "BRAINCHALLENGE 2017 , PARENTS AND PRINCIPALS FORUM "));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Date : ", "Friday August 11, 2017"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Venue : ", "Alinco Events Centre"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Address : ", "No 1 Asuku Layout,Ijaiye(Behind Ojokoro LCDA) Lagos"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Time  : ", " 2pm - 4pm"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_TOPIC_LIST_FOOTNOTE_TEMPLATE,PARAGRAPH_6,LIST_1,LIST_2,LIST_3,LIST_4,LIST_5,LIST_6));
        

//
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_ROW_TAG_TEMPLATE,PARAGRAPH_6,));
//        
//        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_ROW_TAG_TEMPLATE,PARAGRAPH_7));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_HOSTING_FOOTNOTE_TEMPLATE));
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
    
        
        StringBuilder recipientEmails = new StringBuilder();
        recipientEmails = recipientEmails.append(studentObj.getEmail());

        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();
        
        if (StringUtils.isNotBlank(contactEmail1) && !StringUtils.equalsIgnoreCase(studentObj.getEmail(), contactEmail1)) {
            recipientEmails = recipientEmails.append(SEPARATOR).append(contactEmail1);
        }
        
        EmailData emailData = new EmailData();
        emailData.setMailMessage(msgBody.toString());
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getParent().getName());
        emailData.setAttachment(false);
        emailData.setMailSubject(PARENT_MAIL_SUBJECT);
        emailData.setRecipientType(STUDENT_RECORD_FOLDER);
        return emailData;
    }
    
    public EmailData generateAcademyDetailsNotificationForStudents(StudentRecord studentRecord,String attachmentFile,EmailUtilBean emailUtilBean){
        Student studentObj =  studentRecord.getStudent();
        String mailSubject = emailUtilBean.showMessageFromTemplate(BRAINCHALLENGE_ACADEMY_SUBJECT_TEMPLATE,studentObj.getFullName());
        StringBuilder msgBody = new StringBuilder();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(BRAINCHALLENGE_ACADEMY_TOP_TEMPLATE, studentObj.getFullName()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(BRAINCHALLENGE_ACADEMY_MORE_INFO_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "", "BRAINCHALLENGE ACADEMY DETAILS "));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Date : ", "Thursday August 10 & Friday August 11, 2017"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Venue : ", "Alinco Events Centre"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Address : ", "No 1 Asuku Layout,Ijaiye(Behind Ojokoro LCDA) Lagos"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Time  : ", " Thursday 9am - 5pm ; Friday 9am - 2.30pm"));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_TIMETABLE_FOOTNOTE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_REGISTRATION_FOOTNOTE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_RESULT_FOOTNOTE_TEMPLATE));
        
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
        
        StringBuilder recipientEmails = new StringBuilder();
        recipientEmails = recipientEmails.append(studentObj.getEmail());

        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();
        
        if (StringUtils.isNotBlank(contactEmail1) && !StringUtils.equalsIgnoreCase(studentObj.getEmail(), contactEmail1)) {
            recipientEmails = recipientEmails.append(SEPARATOR).append(contactEmail1);
        }
        
        EmailData emailData = new EmailData();
        emailData.setMailMessage(msgBody.toString());
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getFullName());
        emailData.setAttachment(true);
        emailData.setAttachmentFile(attachmentFile);
        emailData.setMailSubject(mailSubject);
        emailData.setRecipientType(STUDENT_RECORD_FOLDER);
        return emailData;
        
    }
    
    public EmailData generateExamDetailsNotification(StudentRecord studentRecord,String attachmentFile,EmailUtilBean emailUtilBean , 
            BatchSettings batchSettings){
        GenericBean genericBean = Beans.getInstance(GenericBean.class);
        
        Student studentObj =  studentRecord.getStudent();
        School schoolObj = studentRecord.getSchool();
        String mailSubject = emailUtilBean.showMessageFromTemplate(EXAMINATION_CENTER_SUBJECT_TEMPLATE,studentObj.getFullName());
        
        StringBuilder msgBody = new StringBuilder();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(EXAMINATION_CENTER_TOP_TEMPLATE, studentObj.getFullName()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "", "QUIZ ( SCREENING TEST ) DETAILS "));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Date : ", genericBean.formatFullDate(batchSettings.getExamDate())));
       
        School examCentre = batchSettings.getExamCentre();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Venue : ", examCentre.getName().toUpperCase()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Address : ", examCentre.getAddressTemplate().getFullAddress().toUpperCase()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Batch : ", genericBean.batch(studentRecord.getExamBatch())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Arrival Time : ", 
                (StringUtils.equalsIgnoreCase(BATCH_A, studentRecord.getExamBatch())? batchSettings.getBatchA_Start() : batchSettings.getBatchB_Start()) + " a.m"));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Exam Class : ", studentRecord.getExamClass()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "","ORAL INTERVIEW DETAILS "));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Date : ", genericBean.formatFullDate(batchSettings.getInterviewDate())));
        
        School interviewCentre = batchSettings.getInterviewCentre();
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Venue : ", interviewCentre.getName().toUpperCase()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Address : ", interviewCentre.getAddressTemplate().getFullAddress().toUpperCase()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Interview Slot : ", studentRecord.getInterviewSlot()));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));
        
//        log.info("Name :: [{}] -- Batch :: [{}] -- Arrival Time :: [{}] -- Interview Slot -- [{}]",
//                studentObj.getFullName(),genericBean.batch(studentRecord.getExamBatch()),
//                (StringUtils.equalsIgnoreCase(BATCH_A, studentRecord.getExamBatch())? batchSettings.getBatchA_Start() : batchSettings.getBatchB_Start()) + " a.m",
//                studentRecord.getInterviewSlot());
        //put LL THE OTHER INSTRUCTIONS
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_QUIZ_FOOTNOTE_TEMPLATE,
                quiz_Info,quiz_list_1,quiz_list_2,quiz_list_3));
        
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_ORAL_INTERVIEW_FOOTNOTE_TEMPLATE,
                interview_Info,interview_list_1,interview_list_2));
        
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
        
        StringBuilder recipientEmails = new StringBuilder();
        recipientEmails = recipientEmails.append(studentObj.getEmail());

        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();
        
        if (StringUtils.isNotBlank(contactEmail1) && !StringUtils.equalsIgnoreCase(studentObj.getEmail(), contactEmail1)) {
            recipientEmails = recipientEmails.append(SEPARATOR).append(contactEmail1);
        }
        
        EmailData emailData = new EmailData();
        emailData.setMailMessage(msgBody.toString());
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getFullName());
        emailData.setAttachment(true);
        emailData.setAttachmentFile(attachmentFile);
        emailData.setMailSubject(mailSubject);
        emailData.setRecipientType(STUDENT_RECORD_FOLDER);
        return emailData;
    }
    
    
}
