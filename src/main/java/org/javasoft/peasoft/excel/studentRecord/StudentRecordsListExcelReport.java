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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
    
    private int rowCount = 1;
    
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
        try {
            writeExcelData();
        }catch (IOException ex) {
            log.error("An Error has Occurred :::", ex);
            return false;
        }
        return true;        
    }
    
    private void writeExcelData()throws IOException{
        
        @Cleanup
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        studentRecords.forEach((StudentRecord aRecord) -> {
            
            Student student = aRecord.getStudent();
            String[] rowValues = {
                String.valueOf(rowCount), student.getFullName(), genericBean.gender(student.getGender()), aRecord.getSss(),
                genericBean.department(aRecord.getDepartment()),String.valueOf(student.getAge()),student.getParent().getOccupation()
            };
            Row row = getCurrentSheet().createRow(rowPosition);
            for (int i = 0; i < rowValues.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(getCellContentsStyle());
                cell.setCellValue(rowValues[i]);
            }
            rowPosition++;
            rowCount++;
        });
        
        writeTo(outStream);
        
        File filePath = globalRegistry.createFile(globalRegistry.getInitFilePath() + STUDENT_RECORD_FOLDER, fileName);
        
        @Cleanup
        FileOutputStream fileStream = new FileOutputStream(filePath);
        outStream.writeTo(fileStream);
        outStream.flush();
    }
    
    @Override
    public void destroy(){
        super.destroy();
        studentRecords = null;
        columnHeaders = null;
    }
}
