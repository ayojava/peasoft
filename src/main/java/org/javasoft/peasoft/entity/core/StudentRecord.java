/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import static org.javasoft.peasoft.constants.PeaResource.ACTIVE;
import static org.javasoft.peasoft.constants.PeaResource.ARTS;
import static org.javasoft.peasoft.constants.PeaResource.COMMERCIAL;
import static org.javasoft.peasoft.constants.PeaResource.DECLINED;
import static org.javasoft.peasoft.constants.PeaResource.DISQUALIFIED;
import static org.javasoft.peasoft.constants.PeaResource.NOT_SELECTED;
import static org.javasoft.peasoft.constants.PeaResource.PENDING;
import static org.javasoft.peasoft.constants.PeaResource.SCIENCE;
import static org.javasoft.peasoft.constants.PeaResource.SELECTED;
import static org.javasoft.peasoft.constants.PeaResource.SSS1;
import static org.javasoft.peasoft.constants.PeaResource.SSS2;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "recordId")
public class StudentRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;
    
    @Column(nullable = false, unique = true ,updatable = false)
    private String identificationNo;
        
    @NotNull
    private String sss ;
    
    @NotNull
    private String examBatch;
    
    @NotNull
    private String department;
    
    private String status; // Active , Disqualified , Declined
    
    private String grade;//Pending , Selected , Not Selected
    
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    private School school;
    
    @OneToOne
    @Cascade({CascadeType.DELETE,CascadeType.PERSIST})
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
        identificationNo = "SRD"+ RandomStringUtils.randomNumeric(5);
    }
    
    public boolean isDisqualified() {
        return StringUtils.equalsIgnoreCase(status, DISQUALIFIED);
    }
    
    public boolean isDeclined(){
        return StringUtils.equalsIgnoreCase(status, DECLINED);
    }
    
    public boolean isSSS1(){
        return StringUtils.equalsIgnoreCase(sss, SSS1);
    }
    
    public boolean isSSS2(){
        return StringUtils.equalsIgnoreCase(sss, SSS2);
    }
    
    public boolean isSelected(){
        return StringUtils.equalsIgnoreCase(grade, SELECTED);
    }
    
    public boolean isNotSelected(){
        return StringUtils.equalsIgnoreCase(grade, NOT_SELECTED);
    }
    
    public boolean isArts(){
        return StringUtils.equalsIgnoreCase(department, ARTS);
    }
    
    public boolean isCommercial(){
        return StringUtils.equalsIgnoreCase(department, SCIENCE);
    }
    
    public boolean isScience(){
        return StringUtils.equalsIgnoreCase(department, COMMERCIAL);
    }
    
}

