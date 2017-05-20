/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.settings;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
public class EmailSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String sender;
    
    @NotNull
    private String server;
    
    @NotNull
    private String password;
    
    private boolean debug;
    
    private int port;
    
    
    public boolean isError(){
        return (StringUtils.isBlank(sender) || StringUtils.isBlank(server) || StringUtils.isBlank(password));
    }
    
}
