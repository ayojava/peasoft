/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.ejb.settings;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.ejb.dao.GenericDAO;
import org.javasoft.peasoft.entity.settings.EnvSettings;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class EnvSettingsFacade extends GenericDAO<EnvSettings, Long>{
    
    public EnvSettingsFacade(){
        super(EnvSettings.class);
    }
    
}
