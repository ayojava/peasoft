/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.settings;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Entity
public class EnvSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BatchSettings batchSettings;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private SMSSettings smsSettings;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private EmailSettings emailSettings;

}
