/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.core;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import static org.javasoft.peasoft.constants.PeaResource.ACTIVE;
import static org.javasoft.peasoft.constants.PeaResource.DISQUALIFIED;
import static org.javasoft.peasoft.constants.PeaResource.FAIL;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import static org.javasoft.peasoft.constants.PeaResource.SUCCESS;

/**
 *
 * @author ayojava
 */
@Named("genericBean")
@SessionScoped
public class GenericBean implements Serializable {

    public String recordStatus(String status) {
        switch (status) {
            case ACTIVE:
                return "Active";
            case DISQUALIFIED:
                return "Disqualified";
        }
        return "";
    }
    
    public String obtainedGrade(String grade) {
        switch (grade) {
            case SUCCESS:
                return "Success";
            case FAIL:
                return "Fail";
            case PENDING:
                return "Pending";
        }
        return "";
    }
}
