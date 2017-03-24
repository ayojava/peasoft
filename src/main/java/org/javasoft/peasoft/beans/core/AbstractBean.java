/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.beans.core;

import java.io.File;
import javax.inject.Inject;
import org.apache.commons.lang3.text.StrBuilder;
import org.javasoft.peasoft.constants.PeaResource;

/**
 *
 * @author ayojava
 */
public abstract class AbstractBean<T> implements PeaResource{
    
    private String pageResource;
    
    @Inject
    private NavigatorBean navigatorBean;
    
    protected void init() {
        
    }
    
    private void setContentPath(String pagePath) {
        navigatorBean.setSelectedContentPath(pagePath);
    }
    
    @Override
    public void setPageResource(String pageResource) {
        StrBuilder builder = new StrBuilder();
        builder = builder.append(DEFAULT_INCLUDE_PATH).append(File.separator).append(pageResource).append(PAGE_EXTENSION);
        setContentPath(builder.toString());
    }
    
    @Override
    public String getPageResource() {
        return pageResource;
    }
}