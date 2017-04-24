/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OrderBy;
import org.javasoft.peasoft.entity.templates.AddressTemplate;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "studentRecords")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false, unique = true ,updatable = false)
    private String identificationNo;
    
    @OrderBy(clause = "name asc")
    @ColumnTransformer(write = "UPPER(?)")
    private String name ;
    
    private boolean examVenue;
    
    @Valid
    @Column
    @Embedded
    private AddressTemplate addressTemplate;
    
    @JoinTable(name = "School_StudentRecord",
            joinColumns = {@JoinColumn(name = "schoolId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "recordId", referencedColumnName = "recordId")})
    //@Cascade({org.hibernate.annotations.CascadeType.PERSIST})
    @OneToMany()
    private List<StudentRecord> studentRecords;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;

    @Formula("(select count(*) from School_StudentRecord s where s.schoolId = id)")
    private int studentCount;
        
}
