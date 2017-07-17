/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.batch.dto.EmailBatchDTO;
import org.javasoft.peasoft.beans.core.util.EmailUtilBean;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.data.EmailData;
import org.javasoft.peasoft.service.StudentService;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentItemProcessor")
public class StudentItemProcessor implements ItemProcessor{

    @Inject
    private EmailUtilBean emailUtilBean;
    
    private StudentService studentService;
    
    @Override
    public EmailData processItem(Object item) throws Exception {
        studentService = new StudentService();
        String emailAddress = (String)item;
        
        try{
            //return studentService.generateData(emailAddress, emailUtilBean,"");
        }catch(Exception ex){
            log.error("Exception {}",ex);
        }
        return null;
    }
    
}
