/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import java.io.File;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
@Data
public class GlobalRegistry {
    
    private GlobalRegistry(){
    }
    
    private static GlobalRegistry globalRegistry;

    public static GlobalRegistry getInstance() {
        if (globalRegistry == null) {
            globalRegistry = new GlobalRegistry();
        }
        return globalRegistry;
    }
    
    private int schoolCount;
    
    private int studentCount;
    
    private String initFilePath;
    
    public void updateSchoolCount(){
        schoolCount++;
    }
    
    public void updateStudentCount(){
        studentCount++;
    }
    
    public File createFile(String dir, String fileName)
    {
        File parentDest = dir != null ?new File(dir): new File(".");
        if(!parentDest.exists()){
            parentDest.mkdir();
        }
        return new File(parentDest,fileName);
    }
    
    ///home/ayojava/programs/wildfly-9.0/standalone/tmp/vfs/temp/temp1e240292d5ed8f67/content-e7f2aa42b02e46d4 
    public static void main(String[] args){
    
        String realPath="/home/ayojava/programs/wildfly-9.0/standalone/tmp/vfs/temp/temp1e240292d5ed8f67/content-e7f2aa42b02e46d4 ";
        int position = StringUtils.indexOf(realPath, "tmp");
        System.out.println("Real Path  " + StringUtils.substring(realPath, 0,position));
        
    }
}
