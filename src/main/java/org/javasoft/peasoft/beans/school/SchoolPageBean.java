/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.school;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.entity.templates.AddressTemplate;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("schoolPageBean")
@ViewScoped
public class SchoolPageBean extends AbstractBean implements Serializable {

    @Getter @Setter
    private School school;

    @Getter
    private List<School> schools;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_SCHOOL, pageResource)) {
            school = new School();
            AddressTemplate addressTemplate = new AddressTemplate();
            school.setAddressTemplate(addressTemplate);
            super.setPageResource(appendFolderPath("school", NEW_SCHOOL));
        } else if (StringUtils.equals(EDIT_SCHOOL, pageResource)) {

            super.setPageResource(appendFolderPath("school", EDIT_SCHOOL));
        } else if (StringUtils.equals(VIEW_SCHOOL, pageResource)) {

            super.setPageResource(appendFolderPath("school", VIEW_SCHOOL));
        } else if (StringUtils.equals(LIST_SCHOOLS, pageResource)) {

            super.setPageResource(appendFolderPath("school", LIST_SCHOOLS));
        }else  if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
            cleanup();
        }
    }
    
    public void saveSchool(){
    
    }
    
    public void editSchool(){
    
    }
    
    private void cleanup(){
    
    }
}
