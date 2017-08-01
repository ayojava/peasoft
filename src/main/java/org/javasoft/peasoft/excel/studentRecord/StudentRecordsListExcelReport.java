/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.excel.studentRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.beans.core.GenericBean;
import static org.javasoft.peasoft.constants.PeaResource.STUDENT_RECORD_FOLDER;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.excel.ExcelProcessor;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class StudentRecordsListExcelReport extends ExcelProcessor{
    
    private List<StudentRecord> studentRecords;
    
    private GenericBean genericBean ;
    
    private String fileName;
    
    private String[] columnHeaders = {"S/N", "Name", "Gender", "Class", "Department","Age","Parent Occupation"};
    
    public boolean populateExcelSheet(String sheetName, String fileName, List<StudentRecord> allRecords){
        
        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        studentRecords = allRecords;
        
        genericBean = Beans.getInstance(GenericBean.class);
        populateSheetHeaders("Student Records");
        populateColumnHeaders(columnHeaders);
        
        
    }
    
    private void writeExcelData()throws IOException{
        
        @Cleanup
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        studentRecords.forEach((StudentRecord aRecord) -> {
            
            Student student = aRecord.getStudent();
        });
        writeTo(outStream);
        
        File filePath = globalRegistry.createFile(globalRegistry.getInitFilePath() + STUDENT_RECORD_FOLDER, fileName);
        
        @Cleanup
        FileOutputStream fileStream = new FileOutputStream(filePath);
        outStream.writeTo(fileStream);
        outStream.flush();
    }
}
