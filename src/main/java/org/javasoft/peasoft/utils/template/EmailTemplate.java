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
    
    public static final String TABLE_ROW_HIGHLIGHT_TEMPLATE = "{tableRow.highlight}";

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
    
    public static final String OUTER_TABLE_REVERT_TEMPLATE = "{outerTable.revert}";
    
    public static final String OUTER_TABLE_QUIZ_FOOTNOTE_TEMPLATE = "{outerTable.quizFootnote}";
    
    public static final String OUTER_TABLE_DIRECTION_FOOTNOTE_TEMPLATE = "{outerTable.directionFootnote}";
    
    public static final String OUTER_TABLE_ORAL_INTERVIEW_FOOTNOTE_TEMPLATE = "{outerTable.oralInterviewFootnote}";
    
    public static final String OUTER_TABLE_DISREGARD_EMAIL_TEMPLATE = "{outerTable.disregardEmail}";
    
    public static final String OUTER_TABLE_FOOTNOTE_TEMPLATE = "{outerTable.footNote}";

    public static final String OUTER_TABLE_BRAINCHALLENGE_EMAIL_TEMPLATE = "{outerTable.brainChallengeEmail}";

    public static final String OUTER_TABLE_BRAINCHALLENGE_TELEPHONE_TEMPLATE = "{outerTable.brainChallengeTelephone}";

    public static final String OUTER_TABLE_BRAINCHALLENGE_WEBSITE_TEMPLATE = "{outerTable.brainChallengeWebsite}";

    public static final String OUTER_TABLE_OFFICE_ADDRESS_TEMPLATE = "{outerTable.officeAddress}";
    
    //outerTable.rowTitleTag  outerTable.rowTag
    
    public static final String OUTER_TABLE_ROW_TITLE_TAG_TEMPLATE = "{outerTable.rowTitleTag}";
    
    public static final String OUTER_TABLE_ROW_TAG_TEMPLATE = "{outerTable.rowTag}";
    
       
    public static final String OUTER_TABLE_TIMETABLE_FOOTNOTE_TEMPLATE = "{outerTable.timetableFootnote}";
    
    public static final String OUTER_TABLE_REGISTRATION_FOOTNOTE_TEMPLATE = "{outerTable.registrationFootnote}";
    
    public static final String OUTER_TABLE_RESULT_FOOTNOTE_TEMPLATE = "{outerTable.resultFootnote}";

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

//     outerTable.timetableFootnote
//    
    public static final String BRAINCHALLENGE_ACADEMY_SUBJECT_TEMPLATE="{brainchallengeAcademy.subject}";
    
    public static final String BRAINCHALLENGE_ACADEMY_TOP_TEMPLATE="{brainchallengeAcademy.top}";
    
    public static final String BRAINCHALLENGE_ACADEMY_MORE_INFO_TEMPLATE="{brainchallengeAcademy.moreInfo}";
    
    
    
    public static final String EXAMINATION_CENTRE_DETAILS_SUBJECT_TEMPLATE="{examinationCenterDetails.subject}";
            
    public static final String EXAMINATION_CENTRE_DETAILS_TOP_TEMPLATE="{examinationCenterDetails.top}";
        
    public static final String ACADEMY_SUBJECT_TEMPLATE = "{academy.subject}";

    public static final String ACADEMY_TOP_TEMPLATE = "{academy.top}";

    public static final String ACADEMY_NOT_SELECTED_NOTIFICATION_TEMPLATE = "{academy.notSelectedNotification}";

    public static final String ACADEMY_SELECTED_NOTIFICATION_TEMPLATE = "{academy.selectedNotification}";

    public static final String ACADEMY_VENUE_TEMPLATE = "{academy.venue}";
    
    public static final String EMAIL1_TEMPLATE = "{email1}";
    
    public static final String EMAIL2_TEMPLATE = "{email2}";
    
    public static final String EMAIL3_TEMPLATE = "{email3}";
    
    public static final String EMAIL4_TEMPLATE = "{email4}";
    
    public static final String EMAIL5_TEMPLATE = "{email5}";
    
    public static final String EMAIL6_TEMPLATE = "{email6}";
    
    public static final String EMAIL7_TEMPLATE = "{email7}";
    
    public static final String EMAIL8_TEMPLATE = "{email8}";
    
    public static final String EMAIL9_TEMPLATE = "{email9}";
    
    public static final String EMAIL10_TEMPLATE = "{email10}";
    
    public static final String EMAIL11_TEMPLATE = "{email11}";
    
    public static final String EMAIL12_TEMPLATE = "{email12}";
    
    public static final String EMAIL13_TEMPLATE = "{email13}";
    
    public static final String EMAIL14_TEMPLATE = "{email14}";
    
    public static final String EMAIL15_TEMPLATE = "{email15}";
    
    public static final String EMAIL16_TEMPLATE = "{email16}";
    
    public static final String EMAIL17_TEMPLATE = "{email17}";
    
    public static final String EMAIL18_TEMPLATE = "{email18}";
    
    public static final String EMAIL19_TEMPLATE = "{email19}";
    
    public static final String EMAIL20_TEMPLATE = "{email20}";
    
    public static final String EMAIL21_TEMPLATE = "{email21}";
    
    public static final String EMAIL22_TEMPLATE = "{email22}";
    
    public static final String EMAIL23_TEMPLATE = "{email23}";
    
    public static final String EMAIL24_TEMPLATE = "{email24}";
    
    public static final String EMAIL25_TEMPLATE = "{email25}";
    
    public static final String EMAIL26_TEMPLATE = "{email26}";
    
    public static final String EMAIL27_TEMPLATE = "{email27}";
    
    public static final String EMAIL28_TEMPLATE = "{email28}";
    
    public static final String EMAIL29_TEMPLATE = "{email29}";
    
    public static final String EMAIL30_TEMPLATE = "{email30}";
    
    public static final String EMAIL31_TEMPLATE = "{email31}";
    
    public static final String EMAIL32_TEMPLATE = "{email32}";
    
    public static final String EMAIL33_TEMPLATE = "{email33}";
    
    public static final String EMAIL34_TEMPLATE = "{email34}";
    
    public static final String EMAIL35_TEMPLATE = "{email35}";
    
    public static final String EMAIL36_TEMPLATE = "{email36}";
    
    public static final String EMAIL37_TEMPLATE = "{email37}";
    
    public static final String EMAIL38_TEMPLATE = "{email38}";
    
    public static final String EMAIL39_TEMPLATE = "{email39}";
    
    public static final String EMAIL40_TEMPLATE = "{email40}";
    
    public static final String EMAIL41_TEMPLATE = "{email41}";
    
    public static final String EMAIL42_TEMPLATE = "{email42}";
    
    public static final String EMAIL43_TEMPLATE = "{email43}";
    
    public static final String EMAIL44_TEMPLATE = "{email44}";
    
    public static final String EMAIL45_TEMPLATE = "{email45}";
    
    public static final String EMAIL46_TEMPLATE = "{email46}";
    
    public static final String EMAIL47_TEMPLATE = "{email47}";
    
    public static final String EMAIL48_TEMPLATE = "{email48}";
}
