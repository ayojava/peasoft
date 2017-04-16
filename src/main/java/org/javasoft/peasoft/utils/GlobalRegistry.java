/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import java.io.File;
import lombok.Data;

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
}
