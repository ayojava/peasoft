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
public class SMSSettings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username; // Username that is to be used for submission

    private String password;// password that is to be used along with username
    
    private String type;// 0:means plain text ; 1:means flash ; 2:means Unicode (Message content should be in Hex) ; 6:means Unicode Flash (Message content should be in Hex)

    private boolean dlr;//0:means DLR is not Required ; 1:means DLR is Required
    
    private String source;// To what server you need to connect to for submission

    private int port;//Port that is to be used like 8080 or 8000
    
    public boolean isError(){
        return (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(type) || StringUtils.isBlank(source));
    }
}
