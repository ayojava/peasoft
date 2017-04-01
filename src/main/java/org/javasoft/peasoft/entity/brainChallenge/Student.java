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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String surname;

    private String othernames;


    private String gender;

    @Email
    private String email;

    private String phoneNo;

    private String otherPhoneNo;
    
    @OneToOne
    @Cascade(CascadeType.DELETE)
    private StudentRecord studentRecord;
    
    @OneToOne
    @Cascade(CascadeType.DELETE)
    private Parent parent;

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

}
