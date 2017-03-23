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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
    
    @Transient
    private double totalScore;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    
}
