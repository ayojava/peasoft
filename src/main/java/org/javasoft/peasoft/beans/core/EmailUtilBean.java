/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.core;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.apache.deltaspike.core.api.message.Message;
import static org.javasoft.peasoft.utils.EmailTemplate.EMAIL_TEMPLATE_FILE;


/**
 *
 * @author ayojava
 */
@Slf4j
@Named("emailUtilBean")
@RequestScoped
public class EmailUtilBean extends AbstractBean{

    private Message message ;
    
    @Override
    @PostConstruct
    public void init() {
        super.init();
        message = getMessage(EMAIL_TEMPLATE_FILE);
    }
    
     public   String showMessageFromTemplate(String template, String... args) { 
        return  message.template(template).argument((Serializable[]) args).toString();
    }
    
    public String showMessageFromTemplate(String template) {
        return message.template(template).toString();
    }
}
