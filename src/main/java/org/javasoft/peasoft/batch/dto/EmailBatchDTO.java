/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.batch.dto;

import lombok.Data;

/**
 *
 * @author ayojava
 */
@Data
public class EmailBatchDTO {
    
    private Long recipientID;
    
    private String recipientName;
    
    private String email1;
    
    private String email2;
    
}
