/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.core;

import java.io.File;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import static org.javasoft.peasoft.constants.PeaResource.DEFAULT_INCLUDE_PATH;
import static org.javasoft.peasoft.constants.PeaResource.PAGE_EXTENSION;

/**
 *
 * @author ayojava
 */
@Named("navigatorBean")
@SessionScoped
public class NavigatorBean implements Serializable {

    private String selectedContentPath;

    private String selectedMenuPath;

    @PostConstruct
    public void init() {

    }

    public String getSelectedContentPath() {
        if (StringUtils.isBlank(selectedContentPath)) {
            StrBuilder builder = new StrBuilder();
            builder = builder.append(DEFAULT_INCLUDE_PATH).append(File.separator);
            builder = builder.append(PAGE_EXTENSION);
            selectedContentPath = builder.build();
        }
        return selectedContentPath;
    }

    public String getSelectedMenuPath() {
        if (StringUtils.isBlank(selectedMenuPath)) {
            StrBuilder builder = new StrBuilder();
            builder = builder.append(DEFAULT_INCLUDE_PATH);
            selectedMenuPath = builder.build();
        }
        return selectedMenuPath;
    }
    
    public void setSelectedMenuPath(String selectedMenuPath) {
        this.selectedMenuPath = selectedMenuPath;
    }

    public void setSelectedContentPath(String selectedContentPath) {
        this.selectedContentPath = selectedContentPath;
    }
}
