/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.constants;

/**
 *
 * @author ayojava
 */
public interface PeaResource {

    public static final String DEFAULT_INCLUDE_PATH = "/WEB-INF/includes";
    
    public static final String DEFAULT_HOME_PATH = "/WEB-INF/includes/home";
    
    public static final String DEFAULT_MENU_PATH = "/WEB-INF/includes/menu";
    
    public static final String CONTENT_HTML = "text/html";

    public static final String PAGE_EXTENSION = ".xhtml";
    
    public static final String VIEW_HOME_PAGE = "view-home";
    
    public static final String MENU_PAGE = "menu-page";
    
    /*=========== SCHOOLS ===========*/
    public static final String LIST_SCHOOLS = "list-schools";

    public static final String NEW_SCHOOL = "new-school";

    public static final String EDIT_SCHOOL = "edit-school";

    public static final String VIEW_SCHOOL = "view-school";
    
    /*=========== STUDENT ===========*/
    public static final String LIST_STUDENTS = "list-students";

    public static final String NEW_STUDENT = "new-student";

    public static final String EDIT_STUDENT = "edit-student";

    public static final String VIEW_STUDENT = "view-student";
    
    /*=========== STUDENT-RECORD ===========*/
    public static final String LIST_STUDENT_RECORDS = "list-student-records";

    public static final String NEW_STUDENT_RECORD = "new-student-record";

    public static final String EDIT_STUDENT_RECORD = "edit-student-record";

    public static final String VIEW_STUDENT_RECORD = "view-student-record";
    
    /*=========== ACADEMY ===========*/
    public static final String LIST_ACADEMY_RECORDS = "list-academy-records";
    
    /*=========== ENV-SETTINGS ===========*/
    public static final String EDIT_ENV_SETTINGS = "edit-env-settings";
    
    /*=========== RESULTS ===========*/
    public static final String LIST_RESULTS = "list-results";
    
    public static final String LIST_RESULTS_OTHER = "list-results-other";
    
    public static final String VIEW_RESULT = "view-result";
    
       
    /*=========== EXAM-BATCH ===========*/
    public static final String LIST_EXAM_BATCH = "list-exam-batch";
    
    public static final String VIEW_EXAM_BATCH = "view-exam-batch";
    
    public static final String EDIT_EXAM_BATCH = "edit-exam-batch";
    
    public static final String LIST_EXAM_BATCH_OTHER = "list-exam-batch-other";
    
    /*=========== ORAL-INTERVIEW ===========*/
    public static final String LIST_ORAL_INTERVIEW = "list-oral-interview";
    
    public static final String VIEW_ORAL_INTERVIEW = "view-oral-interview";
    
    public static final String EDIT_ORAL_INTERVIEW = "edit-oral-interview";
    
    public static final String LIST_ORAL_INTERVIEW_OTHER = "list-oral-interview-other";
    
    /*=========== EMAIL ===========*/
    public static final String LIST_EMAILS = "list-emails";
    
    /*=========== SMS ===========*/
    public static final String LIST_SMS = "list-sms";
    
    //===============================
    public static final String ACTIVE ="AC";
    
    public static final String ABSENT ="AB";
    
    public static final String PRESENT ="PR";
    
    public static final String DISQUALIFIED ="DQ";
    
    public static final String DECLINED ="DC";
    
    public static final String SELECTED ="SD";
    
    public static final String SENT ="ST";
    
    public static final String NOT_SELECTED ="NSD";
    
    public static final String PENDING ="P";
    
    public static final String ARTS ="A";
    
    public static final String SCIENCE ="SC";
    
    public static final String COMMERCIAL ="CM";
    
    public static final String MALE ="M";
    
    public static final String FEMALE ="F";
    
    public static final String SSS1 ="SSS 1";
    
    public static final String SSS2 ="SSS 2";
    
    public static final String SSS3 ="SSS 3";
    
    public static final String DISPLAY_DATE_FORMAT_DAYS = "dd-MM-yyyy";
    
    public static final String FULL_DATE_FORMAT = "EEEE dd MMMM yyyy";
    
    public static final String FULL_DATE_FORMAT_SMS = "EEE dd MMMM yyyy";
    
    public static final String SCHOOL_FOLDER ="school";
    
    public static final String STUDENT_FOLDER ="student";
    
    public static final String STUDENT_RECORD_FOLDER ="student-record";
    
    public static final String ORAL_INTERVIEW_FOLDER ="oral-interview";
    
    public static final String EXAM_BATCH_FOLDER ="exam-batch";
    
    public static final String ACADEMY_BATCH_FOLDER ="academy";
    
    public static final String RESULTS_FOLDER ="results";
    
    public static final String MESSAGES_FOLDER ="messages";
    
    public static final String GUIDELINES_FOLDER ="guidelines";
    
    public static final String ENV_SETTINGS_FOLDER ="env-settings";
    
    public static final String SENDER ="PEAF";
    
    public static final String SUCCESS ="S";
    
    public static final String FAIL ="F";
    
    public static final String BATCH_A ="A";
    
    public static final String BATCH_B ="B";
    
    public static final String SEPARATOR ="|";
    
    public static final String DOLLAR_SEPARATOR ="$";
    
    public static final String COLON =":";
    
    public static final String SLOT_1 ="9 AM - 11 AM";
    
    public static final String SLOT_2 ="11 AM - 1 PM";
    
    public static final String SLOT_3 ="1 PM - 3 PM";
    
    public static final String SLOT_4 ="3 PM - 5 PM";
    
    public static final int BATCH_SIZE = 60;
    
    public static final int SMS_BATCH_SIZE = 70;
    
    public static final String SENDER_EMAIL ="brainchallenge2017@peafoundation.org";
    
    public static final int TABLE_LIST_SIZE = 50;
    
    //=======================================================
    
    public   static final String HALL ="Hall";
    
    public   static final String SS3A = "SS 3A";
    
    public   static final String SS3B = "SS 3B";
    
    public   static final String SS3C = "SS 3C";
    
    public   static final String SS3D = "SS 3D";
    
    public   static final String SS2A = "SS 2A";
    
    public   static final String SS2B = "SS 2B";
    
    public   static final String SS2C = "SS 2C";
    
    public   static final String SS1B = "SS 1B";
    
    
    
    void setPageResource(String pageResource);

    String getPageResource();

}
