/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.core;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id") 
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true ,updatable = false)
    private String identificationNo;
    
    @OrderBy(clause = "surname asc")
    private String surname;

    private String othernames;

    private String gender;

    @Email
    private String email;

    private String phoneNo;

    private String otherPhoneNo;
    
    @OneToOne()
    @Cascade({CascadeType.DELETE})
    private StudentRecord studentRecord;
    
    @OneToOne
    @Cascade({CascadeType.DELETE , CascadeType.PERSIST})
    private Parent parent;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    @Transient
    private int age;
    
    @Transient
    private String fullName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    
    
    public String getFullName(){
        StringBuilder builder = new StringBuilder();
        builder = builder.append(surname).append("  ").append(othernames);
        return builder.toString();
    }
    
    public int getAge(){
        LocalDate startDate= dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period currentAge = Period.between(startDate, LocalDate.now());
        return currentAge.getYears();
    }

}
