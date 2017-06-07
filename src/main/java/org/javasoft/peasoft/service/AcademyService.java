/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import static org.javasoft.peasoft.constants.PeaResource.RESULTS_FOLDER;
import static org.javasoft.peasoft.constants.PeaResource.SEPARATOR;
import org.javasoft.peasoft.entity.core.Marks;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import static org.javasoft.peasoft.utils.template.EmailTemplate.ACADEMY_NOT_SELECTED_NOTIFICATION_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.ACADEMY_SELECTED_NOTIFICATION_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.ACADEMY_SUBJECT_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.ACADEMY_TOP_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.ACADEMY_VENUE_TEMPLATE;
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
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_INSTAGRAM_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_BODY_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_FOOTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_OPEN_TABLE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_TWITTER_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.OUTER_TABLE_WEBSITE_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_EVEN_TEMPLATE;
import static org.javasoft.peasoft.utils.template.EmailTemplate.TABLE_ROW_ODD_TEMPLATE;

/**
 *
 * @author ayojava
 */
@Slf4j
public class AcademyService {

    public EmailData generateNotificationEmail(EmailUtilBean emailUtilBean, StudentRecord studentRecord, BatchSettings batchSetting,
            boolean selected) {
        Student studentObj = studentRecord.getStudent();

        Marks marks = studentRecord.getMarks();

        String mailSubject = emailUtilBean.showMessageFromTemplate(ACADEMY_SUBJECT_TEMPLATE, studentObj.getFullName());

        StringBuilder msgBody = new StringBuilder();

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(HEADER_OPEN_DIV_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_TABLE_TEMPLATE));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_BODY_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(ACADEMY_TOP_TEMPLATE, studentObj.getFullName()));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_TOP_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, "Identification No : ", studentObj.getIdentificationNo()));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, "School : ", studentRecord.getSchool().getName()));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Mathematics (%) : ", String.valueOf(marks.getMathScore())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " English (%) : ", String.valueOf(marks.getEnglishScore())));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Current Affairs (%) : ", String.valueOf(marks.getCurrentAffairsScore())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " I.C.T (%) : ", String.valueOf(marks.getIctScore())));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Book Knowledge (%) : ", String.valueOf(marks.getBookKnowledge())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Communication Skill (%) : ", String.valueOf(marks.getCommunicationSkill())));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Confidence Level (%) : ", String.valueOf(marks.getConfidenceLevel())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Personal Appearance (%) : ", String.valueOf(marks.getPersonalAppearance())));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Plans And Goals (%) : ", String.valueOf(marks.getPlansAndGoals())));
        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_EVEN_TEMPLATE, " Self Awareness (%) : ", String.valueOf(marks.getSelfAwareness())));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(TABLE_ROW_ODD_TEMPLATE, " Total Score (%) : ", String.valueOf(marks.getTotalScore())));

        msgBody.append(emailUtilBean.showMessageFromTemplate(INNER_TABLE_BOTTOM_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_CLOSE_BODY_TEMPLATE));

        msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(OUTER_TABLE_OPEN_FOOTER_TEMPLATE));

        if (selected) {
            msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(ACADEMY_SELECTED_NOTIFICATION_TEMPLATE));
            msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(ACADEMY_VENUE_TEMPLATE));
        } else {
            msgBody = msgBody.append(emailUtilBean.showMessageFromTemplate(ACADEMY_NOT_SELECTED_NOTIFICATION_TEMPLATE));
        }

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
        recipientEmails = recipientEmails.append(studentObj.getEmail());

        String contactEmail1 = studentObj.getParent().getAddressTemplate().getContactEmail1();

        if (StringUtils.isNotBlank(contactEmail1) && !StringUtils.equalsIgnoreCase(studentObj.getEmail(), contactEmail1)) {
            recipientEmails = recipientEmails.append(SEPARATOR).append(contactEmail1);
        }

        EmailData emailData = new EmailData();
        emailData.setAttachment(false);
        emailData.setRecipientEmail(recipientEmails.toString()); //
        emailData.setRecipientID(studentObj.getId());
        emailData.setRecipientName(studentObj.getFullName());
        emailData.setAttachmentFile(null);
        emailData.setMailSubject(mailSubject);
        emailData.setMailMessage(msgBody.toString());
        emailData.setRecipientType(RESULTS_FOLDER);

        return emailData;
    }
}
