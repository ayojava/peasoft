/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils.template;

/**
 *
 * @author ayojava
 */
public interface EmailTemplate {

    public static final String EMAIL_TEMPLATE_FILE = "org.javasoft.peasoft.i18n.emailTemplate";

    public static final String TABLE_ROW_ODD_TEMPLATE = "{tableRow.odd}";

    public static final String TABLE_ROW_EVEN_TEMPLATE = "{tableRow.even}";

    public static final String REGISTRATION_DETAILS_SUBJECT_TEMPLATE = "{registrationDetails.subject}";

    public static final String REGISTRATION_DETAILS_TOP__TEMPLATE = "{registrationDetails.top}";

    public static final String INNER_TABLE_TOP_TEMPLATE = "{innerTable.top}";

    public static final String INNER_TABLE_BOTTOM_TEMPLATE = "{innerTable.bottom}";

    public static final String HEADER_OPEN_DIV_TEMPLATE = "{header.openDivTag}";

    public static final String OUTER_TABLE_OPEN_TABLE_TEMPLATE = "{outerTable.openTableTag}";

    public static final String OUTER_TABLE_OPEN_BODY_TEMPLATE = "{outerTable.openBodyTag}";

    public static final String OUTER_TABLE_CLOSE_BODY_TEMPLATE = "{outerTable.closeBodyTag}";

    public static final String OUTER_TABLE_OPEN_FOOTER_TEMPLATE = "{outerTable.openFooterTag}";

    public static final String OUTER_TABLE_GUIDELINE_TEMPLATE = "{outerTable.guideline}";

    public static final String OUTER_TABLE_ENQUIRY_TEMPLATE = "{outerTable.enquiry}";
    
    public static final String OUTER_TABLE_DISREGARD_EMAIL_TEMPLATE = "{outerTable.disregardEmail}";

    public static final String OUTER_TABLE_BRAINCHALLENGE_EMAIL_TEMPLATE = "{outerTable.brainChallengeEmail}";

    public static final String OUTER_TABLE_BRAINCHALLENGE_TELEPHONE_TEMPLATE = "{outerTable.brainChallengeTelephone}";

    public static final String OUTER_TABLE_BRAINCHALLENGE_WEBSITE_TEMPLATE = "{outerTable.brainChallengeWebsite}";

    public static final String OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE = "{outerTable.officeAddress}";

    public static final String OUTER_TABLE_FACEBOOK_TEMPLATE = "{outerTable.facebook}";

    public static final String OUTER_TABLE_INSTAGRAM_TEMPLATE = "{outerTable.instagram}";

    public static final String OUTER_TABLE_TWITTER_TEMPLATE = "{outerTable.twitter}";

    public static final String OUTER_TABLE_WEBSITE_TEMPLATE = "{outerTable.website}";

    public static final String OUTER_TABLE_CLOSE_FOOTER_TEMPLATE = "{outerTable.closeFooterTag}";

    public static final String OUTER_TABLE_CLOSE_TABLE_TEMPLATE = "{outerTable.closeTableTag}";

    public static final String HEADER_CLOSE_DIV_TEMPLATE = "{header.closeDivTag}";

    public static final String EXAMINATION_CENTER_SUBJECT_TEMPLATE = "{examinationCenter.subject}";

    public static final String EXAMINATION_CENTER_TOP_TEMPLATE = "{examinationCenter.top}";

    public static final String SCHOOL_RESULT_DETAILS_SUBJECT_TEMPLATE = "{schoolResultDetails.subject}";

    public static final String SCHOOL_RESULT_DETAILS_TOP_TEMPLATE = "{schoolResultDetails.top}";

    public static final String STUDENT_RESULT_SUBJECT_TEMPLATE = "{studentResult.subject}";

    public static final String STUDENT_RESULT_TOP_TEMPLATE = "{studentResult.top}";

    public static final String STUDENT_RESULT_SELECTED_NOTIFICATION_TEMPLATE = "{studentResult.selectedNotification}";

    public static final String STUDENT_RESULT_NOT_SELECTED_NOTIFICATION_TEMPLATE = "{studentResult.notSelectedNotification}";

    //
    //academy.top
    //academy.notSelectedNotification
    //academy.selectedNotification
    public static final String ACADEMY_SUBJECT_TEMPLATE = "{academy.subject}";

    public static final String ACADEMY_TOP_TEMPLATE = "{academy.top}";

    public static final String ACADEMY_NOT_SELECTED_NOTIFICATION_TEMPLATE = "{academy.notSelectedNotification}";

    public static final String ACADEMY_SELECTED_NOTIFICATION_TEMPLATE = "{academy.selectedNotification}";

    public static final String ACADEMY_VENUE_TEMPLATE = "{academy.venue}";
}
