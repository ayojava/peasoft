/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.settings;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javasoft.peasoft.entity.core.School;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
public class BatchSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int batchA_Start;
    
    private int batchB_Start;
    
    private int batchA_Stop;
    
    private int batchB_Stop;
        
    @OneToOne
    private School examCentre;
    
    @Future
    @Temporal(TemporalType.DATE)
    private Date examDate;
}
