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
import org.hibernate.annotations.ColumnTransformer;
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
    @ColumnTransformer(write = "UPPER(?)")
    private String street;

    @NotNull
    @ColumnTransformer(write = "UPPER(?)")
    private String city;

    @NotNull
    @ColumnTransformer(write = "UPPER(?)")
    private String nearestBusStop;

    private String contactPhoneNo1;

    private String contactPhoneNo2;

    @Email
    @ColumnTransformer(write = "UPPER(?)")
    private String contactEmail1;

    private String contactEmail2;
    
    @Transient
    private String fullAddress;
    
    public String getFullAddress(){
        return street + "   " + city ;
    }

}
