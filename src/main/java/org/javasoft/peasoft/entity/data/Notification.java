/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.javasoft.peasoft.entity.core.StudentRecord;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Entity
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean batchNotification;
    
    private boolean resultNotification;
    
    private boolean guidelineNotification;
    
    private boolean academyNotification;
    
    @OneToOne(cascade = CascadeType.MERGE)
    private StudentRecord studentRecord;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    @PrePersist
    public void init(){
        batchNotification = resultNotification = guidelineNotification = false;
    }
    
}
