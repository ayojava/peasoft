/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.entity.templates;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Embeddable
public class AddressTemplate implements Serializable {

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String nearestBusStop;

    private String contactPhoneNo1;

    private String contactPhoneNo2;

    @Email
    private String contactEmail1;

    private String contactEmail2;
    
    @Transient
    private String fullAddress;
    
    public String getFullAddress(){
        return street + "   " + city ;
    }

}
