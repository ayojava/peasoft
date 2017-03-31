/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.brainChallenge;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import static org.javasoft.peasoft.constants.PeaResource.ACTIVE;
import static org.javasoft.peasoft.constants.PeaResource.DISQUALIFIED;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
public class StudentRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;
        
    @NotNull
    private String sss ;
    
    @NotNull
    private String department;
    
    private String status; // Active , Disqualified
    
    private String grade;//Pending , Success , Fail
    
    @Valid
    @ManyToOne
    private School school;
    
    @OneToOne
    @Cascade(CascadeType.DELETE)
    private Marks marks;
    
    @OneToOne(mappedBy = "studentRecord")
    private Student student;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    @PrePersist
    public void prePersist(){
        status = ACTIVE;
        grade = PENDING;
    }
    
    public boolean isDisqualified() {
        return StringUtils.equalsIgnoreCase(status, DISQUALIFIED);
    }
    
}
