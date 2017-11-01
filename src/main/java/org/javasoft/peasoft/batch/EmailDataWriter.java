/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.batch;

import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.ejb.EJB;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.ejb.data.EmailDataFacade;
import org.javasoft.peasoft.entity.data.EmailData;

/**
 *
 * @author ayojava
 */

@Slf4j
@Named("emailDataWriter")
public class EmailDataWriter extends AbstractItemWriter {

    @EJB
    private EmailDataFacade emailDataFacade;
    
    @Override
    public void writeItems(List<Object> items) throws Exception {
        items.stream().forEach((obj) -> {
            emailDataFacade.persist((EmailData)obj);
        });
    }
    
}

//http://www.mastertheboss.com/javaee/batch-api/batch-applications-tutorial-on-wildfly
//http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/BatchProcessing/BatchProcessing.html#section3s1
//https://www.genuitec.com/create-ecommerce-dashboard-angular-4/