/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.envSettings;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.settings.EnvSettingsFacade;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import org.javasoft.peasoft.entity.settings.EmailSettings;
import org.javasoft.peasoft.entity.settings.EnvSettings;
import org.javasoft.peasoft.entity.settings.SMSSettings;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("envSettingsPageBean")
@ViewScoped
public class EnvSettingsPageBean extends AbstractBean implements Serializable {
    
    @Getter @Setter
    private EnvSettings envSettings;
    
    @EJB
    private EnvSettingsFacade envSettingsFacade;
    
    @Getter
    private List<School> schools;

    @EJB
    private SchoolFacade schoolFacade;

    @Override
    @PostConstruct
    public void init() {
        super.init();
    }
    
    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(EDIT_ENV_SETTINGS, pageResource)) {
            envSettings = envSettingsFacade.findOne();
            if(envSettings == null){
                envSettings = new EnvSettings();
                SMSSettings smsSettings = new SMSSettings();
                envSettings.setSmsSettings(smsSettings);
                EmailSettings emailSettings = new EmailSettings();
                envSettings.setEmailSettings(emailSettings);
                BatchSettings batchSettings = new BatchSettings();
                School examCentre = new School();
                batchSettings.setExamCentre(examCentre);
                envSettings.setBatchSettings(batchSettings);
            }
            //log.info("EnvSettings :: {} " ,  envSettings);
            schools = schoolFacade.findAll("name");
            super.setPageResource(appendFolderPath(ENV_SETTINGS_FOLDER, EDIT_ENV_SETTINGS));
        }else  if (StringUtils.equals(VIEW_HOME_PAGE, pageResource)) {
            setHomePageResource();
        }
    }
    
    public void editEnvSettings(){
        try{
            envSettingsFacade.edit(envSettings);
            Messages.addGlobalInfo("Edit Operation Successful");
            setPageResource(EDIT_ENV_SETTINGS);
        }catch(Exception ex){
            log.error("An Error has Occurred :::", ex);
            Messages.addGlobalError("An Error has Occured");
        }
    }
}
