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
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1025;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1701;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1702;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1703;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1704;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1705;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1706;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1707;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1708;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1709;
import static org.javasoft.peasoft.constants.SMSResource.CODE_1710;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1025;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1701;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1702;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1703;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1704;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1705;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1706;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1707;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1708;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1709;
import static org.javasoft.peasoft.constants.SMSResource.MSG_1710;
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
    
    public void setResponseMessage(String responseMessage){
        switch(responseCode){
            case CODE_1701:
                this.responseMessage = MSG_1701;
            case CODE_1702:
                this.responseMessage = MSG_1702;
            case CODE_1703:
                this.responseMessage = MSG_1703;
            case CODE_1704:
                this.responseMessage = MSG_1704;
            case CODE_1705:
                this.responseMessage = MSG_1705;
            case CODE_1706:
                this.responseMessage = MSG_1706;
            case CODE_1707:
                this.responseMessage = MSG_1707;
            case CODE_1708:
                this.responseMessage = MSG_1708;
            case CODE_1709:
                this.responseMessage = MSG_1709;
            case CODE_1710:
                this.responseMessage = MSG_1710;
            case CODE_1025:
                this.responseMessage = MSG_1025;
        }
    }
    
    @PrePersist
    public void prePersist(){
        setStatus(PENDING);
    }
}
