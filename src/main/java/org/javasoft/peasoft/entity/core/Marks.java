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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
public class Marks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double mathScore;
    
    private double englishScore;
    
    private double currentAffairsScore;
    
    private double ictScore;
    
    private double totalAcademicScore;
    
    
    private double communicationSkill;
    
    private double personalAppearance;
    
    private double selfAwareness;
    
    private double plansAndGoals;
    
    private double bookKnowledge;
    
    private double confidenceLevel;
    
    private double totalInterviewScore;
    
    private double totalScore;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    @PrePersist
    public void prePersist(){
        mathScore = englishScore = currentAffairsScore = ictScore = totalAcademicScore = 0.0;
        communicationSkill = personalAppearance = selfAwareness =plansAndGoals = 0.0;
        bookKnowledge = confidenceLevel = totalInterviewScore = 0.0;
    }
    
    @PreUpdate
    public void preUpdate(){
        totalAcademicScore = mathScore + englishScore + currentAffairsScore + ictScore;
        totalInterviewScore = communicationSkill + personalAppearance + selfAwareness + plansAndGoals + bookKnowledge + confidenceLevel;
        totalScore = totalAcademicScore + totalInterviewScore;
    }
        
}
