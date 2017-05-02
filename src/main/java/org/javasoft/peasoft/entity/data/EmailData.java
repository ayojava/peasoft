/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;

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
    
    private Long recipientID;
    
    @PrePersist
    public void prePersist(){
        setStatus(PENDING);
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date sentDate;
    
}
