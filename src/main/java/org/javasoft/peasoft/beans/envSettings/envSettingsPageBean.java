/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.envSettings;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.peasoft.beans.core.AbstractBean;
import org.javasoft.peasoft.ejb.envSettings.EnvSettingsFacade;
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
public class envSettingsPageBean extends AbstractBean implements Serializable {
    
    @Getter @Setter
    private EnvSettings envSettings;
    
    @EJB
    private EnvSettingsFacade envSettingsFacade;

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
            }
            //log.info("EnvSettings :: {} " ,  envSettings);
            super.setPageResource(appendFolderPath("envSettings", EDIT_ENV_SETTINGS));
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
