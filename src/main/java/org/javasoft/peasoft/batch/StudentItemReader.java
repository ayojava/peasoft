/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.batch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.batch.api.chunk.AbstractItemReader;
import javax.ejb.EJB;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.batch.dto.EmailBatchDTO;
import org.javasoft.peasoft.ejb.school.SchoolFacade;
import org.javasoft.peasoft.ejb.student.StudentFacade;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("studentItemReader")
public class StudentItemReader extends AbstractItemReader{
    
    @EJB
    private StudentFacade studentFacade;
    
    @EJB
    private SchoolFacade schoolFacade;
    
    private HashSet<String> emailAddress ;
    
    @Override
    public void open(Serializable checkpoint) throws Exception {
        log.info("------------ Called Open Method -------------");
        emailAddress = new HashSet<>();
    }

    @Override
    public String readItem() throws Exception {
        
        List<EmailBatchDTO> studentEMailBatchDTO = studentFacade.findEmailBatchDTO();
                
        List<EmailBatchDTO> schoolEmailBatchDTO = schoolFacade.findEmailBatchDTO();
        
        List<EmailBatchDTO> allEmailBatchDTO = new ArrayList<>();
        
        allEmailBatchDTO.addAll(studentEMailBatchDTO);
        allEmailBatchDTO.addAll(schoolEmailBatchDTO);
        
        log.info("Students :: {} --- Schools :: {}  Total :: {} " ,studentEMailBatchDTO.size(),schoolEmailBatchDTO.size(),allEmailBatchDTO.size());
                
        allEmailBatchDTO.stream().forEach(aEmailBatchDTO->{
            emailAddress.add(aEmailBatchDTO.getEmail1());
            emailAddress.add(aEmailBatchDTO.getEmail2());
        });
        
        Iterator<String> itr = emailAddress.iterator();
        while(itr.hasNext()){
            return itr.next();
        }
        return null;
    }
    
}
