/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.core;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import org.javasoft.peasoft.constants.PeaResource;
import static org.javasoft.peasoft.constants.PeaResource.ABSENT;
import static org.javasoft.peasoft.constants.PeaResource.ACTIVE;
import static org.javasoft.peasoft.constants.PeaResource.ARTS;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_A;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_B;
import static org.javasoft.peasoft.constants.PeaResource.COMMERCIAL;
import static org.javasoft.peasoft.constants.PeaResource.DISQUALIFIED;
import static org.javasoft.peasoft.constants.PeaResource.FEMALE;
import static org.javasoft.peasoft.constants.PeaResource.FULL_DATE_FORMAT;
import static org.javasoft.peasoft.constants.PeaResource.MALE;
import static org.javasoft.peasoft.constants.PeaResource.NOT_SELECTED;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import static org.javasoft.peasoft.constants.PeaResource.PRESENT;
import static org.javasoft.peasoft.constants.PeaResource.SCIENCE;
import static org.javasoft.peasoft.constants.PeaResource.SELECTED;
import static org.javasoft.peasoft.constants.PeaResource.SENT;
import static org.javasoft.peasoft.constants.PeaResource.SLOT_1;
import static org.javasoft.peasoft.constants.PeaResource.SLOT_2;
import static org.javasoft.peasoft.constants.PeaResource.SLOT_3;
import static org.javasoft.peasoft.constants.PeaResource.SLOT_4;

/**
 *
 * @author ayojava
 */
@Named("genericBean")
@SessionScoped
public class GenericBean implements Serializable {
    
    private DecimalFormat decimalFormat;
    
    @Getter
    private int tableListSize;
    
    @PostConstruct
    public void init(){
        decimalFormat = new DecimalFormat("##.##");
        tableListSize = PeaResource.TABLE_LIST_SIZE;
    }

    public String recordStatus(String status) {
        switch (status) {
            case ACTIVE:
                return "Active";
            case DISQUALIFIED:
                return "Disqualified";
            case SENT:
                return "Sent";
            case PENDING:
                return "Pending";
        }
        return "";
    }
    
    public String attendance(String attendance){
        switch(attendance){
            case ABSENT:
                return "Absent";
            case PRESENT:
                return "Present";
        }
        return "";
    }
    
    public String obtainedGrade(String grade) {
        switch (grade) {
            case SELECTED:
                return "Selected";
            case NOT_SELECTED:
                return "Not Selected";
            case PENDING:
                return "Pending";
        }
        return "";
    }
    
    public String gender(String gender){
        switch (gender) {
            case MALE:
                return "Male" ;
            case FEMALE:
                return "Female" ;
        }
        return "";
    }
    
    public String department(String department){
        switch (department) {
            case ARTS:
                return "Arts" ;
            case COMMERCIAL:
                return "Commercial" ;
            case SCIENCE:
                return "Science" ;
        }
        return "";
    }
    
    public String batch(String batch){
        switch(batch){
            case BATCH_A :
                return "Batch A" ;
            case BATCH_B :
                return "Batch B" ;
        }
        return "";
    }
    
    public String interviewSlot(String interviewSlot){
        switch(interviewSlot){
            case SLOT_1:
                return "9 AM - 11 AM";
            case SLOT_2:
                return "11 AM - 1 PM";
            case SLOT_3:
                return "1 PM - 3 PM";
            case SLOT_4:
                return "3 PM - 5 PM";
        }
        return "";
    }
    
    public String formatDecimalPlaces(double value){
        return decimalFormat.format(value);
    }
    
    public String formatFullDate(Date dateObj){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FULL_DATE_FORMAT);
        return simpleDateFormat.format(dateObj);
    }
}
