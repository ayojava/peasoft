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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import org.javasoft.peasoft.entity.core.Student;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Entity
public class SMSData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String message;
    
    private String responseCode;
    
    private String responseMessage;
    
    private String status ;
    
    @ManyToOne
    private Student student;
       
    private String recipientPhoneNo;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
        
    @PrePersist
    public void prePersist(){
        setStatus(PENDING);
    }
}
