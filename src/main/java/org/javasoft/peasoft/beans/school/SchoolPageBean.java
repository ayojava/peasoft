/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.school;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.beans.core.AbstractBean;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("schoolPageBean")
@ViewScoped
public class SchoolPageBean extends AbstractBean implements Serializable {

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }
    
    @Override
    public void setPageResource(String pageResource) {
        
    }
}
