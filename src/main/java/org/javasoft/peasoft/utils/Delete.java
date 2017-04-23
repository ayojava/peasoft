/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
public class Delete {
    
    private String dlr;

    public void setDlr(String dlr) {
        this.dlr = dlr;
    }
    
    
  
     public static void main(String [] args){
//        String val="/home/ayojava/programs/wildfly-9.0/standalone/tmp/vfs/temp/temp64a9645cacc1341e/content-24fb6dad413ab749";
//        int position = StringUtils.indexOf(val, "wildfly");
//        System.out.println("====" + StringUtils.substring(val, 0,position));
        
        System.out.println("====" + StringUtils.substring("08023991517" ,1));
        System.out.println("====" + StringUtils.right("08023991517" ,10));
    }
}
