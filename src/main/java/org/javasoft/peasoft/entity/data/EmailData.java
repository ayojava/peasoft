/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Entity
public class EmailData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailID;

    private String mailSubject;
    
    private boolean attachment;

    private String attachmentFile;
    
    private String status;//pending / sent
        
    @Lob
    private String mailMessage;
    
    private String recipientName ;
    
    private String recipientEmail; // one row per email
    
    private String recipientType ; // student or school
    
    private String recipientID;
    
}
