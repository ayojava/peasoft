/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.excel.interviewSlot;

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
import static org.javasoft.peasoft.constants.PeaResource.ORAL_INTERVIEW_FOLDER;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.excel.ExcelProcessor;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class InterviewSlotExcelReport extends ExcelProcessor{
    
    private int rowCount = 1;

    private String[] columnHeaders = {"S/N","Reg No" ,"Name", " Gender "," School " ," Class ", " Department ", " Interview Slot "};
    
    private String fileName;
    
    private List<StudentRecord> studentRecords;
    
    public boolean populateExcelSheet(List<StudentRecord> records, String sheetName, String fileName){
        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        String slotName ="";
        String timeSlot ="";
        populateSheetHeaders(slotName ,timeSlot);
        populateColumnHeaders(columnHeaders);
        studentRecords = records;
        try {
            writeExcelData();
        } catch (IOException ex) {
            log.error("An Error has Occurred :::", ex);
            return false;
        }
        return true;
    }
    
    private void writeExcelData() throws IOException {
        
        @Cleanup
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        GenericBean genericBean = Beans.getInstance(GenericBean.class);
        
        studentRecords.forEach((StudentRecord aRecord) -> {
            Student student = aRecord.getStudent();
            String[] rowValues = {
                String.valueOf(rowCount),aRecord.getIdentificationNo() ,student.getFullName(),genericBean.gender(student.getGender()),
                aRecord.getSchool().getName(),aRecord.getSss(), genericBean.department(aRecord.getDepartment()),
                aRecord.getInterviewSlot()
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
        
        log.info(" Directory ::: [ {} ]    FileName ::: [ {} ]" ,globalRegistry.getInitFilePath() + ORAL_INTERVIEW_FOLDER, fileName );
        File filePath = globalRegistry.createFile(globalRegistry.getInitFilePath() + ORAL_INTERVIEW_FOLDER, fileName);
        
        @Cleanup
        FileOutputStream fileStream = new FileOutputStream(filePath);
        outStream.writeTo(fileStream);
        outStream.flush();
    }
}
