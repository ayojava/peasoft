/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.constants;

/**
 *
 * @author ayojava
 */
public interface PeaResource {

    public static final String DEFAULT_INCLUDE_PATH = "/WEB-INF/includes/";

    public static final String PAGE_EXTENSION = ".xhtml";
    
    /*=========== MODULES ===========*/
    public static final String LIST_SCHOOLS = "list-schools";

    public static final String NEW_SCHOOL = "new-school";

    public static final String EDIT_SCHOOL = "edit-school";

    public static final String VIEW_SCHOOL = "view-school";
    
    
    
    void setPageResource(String pageResource);

    String getPageResource();

}