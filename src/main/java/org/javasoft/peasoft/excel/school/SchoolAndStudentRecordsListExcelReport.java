/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.excel.school;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.javasoft.peasoft.beans.core.GenericBean;
import static org.javasoft.peasoft.constants.PeaResource.BATCH_A;
import static org.javasoft.peasoft.constants.PeaResource.SCHOOL_FOLDER;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.entity.settings.BatchSettings;
import org.javasoft.peasoft.excel.ExcelProcessor;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SchoolAndStudentRecordsListExcelReport extends ExcelProcessor{
    
    private School school;

    private int rowCount = 1;
    
    private List<StudentRecord> studentRecords;
    
    private GenericBean genericBean ;
    
    private String[] columnHeaders = {"S/N", "Name", "Gender", "Class", "Department","Batch (Quiz)","Time Slot (Quiz)","Exam Class(Quiz)",
                                    "Time Slot(Oral Interview)"};

    private String fileName;
    
    public boolean populateExcelSheet(String sheetName, String fileName, School schoolObj, List<StudentRecord> allRecords,
            BatchSettings batchSettings) {
        if (schoolObj == null || allRecords.isEmpty() || StringUtils.isBlank(sheetName) || StringUtils.isBlank(fileName)) {
            log.error("=== SheetName or School is not supplied . Excel sheet can't be generated");
            return false;
        }
        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        school = schoolObj;
        
        genericBean = Beans.getInstance(GenericBean.class);
        String quizVenue="VENUE FOR QUIZ ::: " + batchSettings.getExamCentre().getAddressTemplate().getFullAddress();
        String quizDate="DATE FOR QUIZ ::: " +genericBean.formatFullDate(batchSettings.getExamDate());
        String oralInterviewVenue="VENUE FOR ORAL INTERVIEW ::: " + batchSettings.getInterviewCentre().getAddressTemplate().getFullAddress();
        String oralInterviewDate="DATE FOR ORAL INTERVIEW ::: " +genericBean.formatFullDate(batchSettings.getInterviewDate());
        populateSheetHeaders(quizVenue,quizDate,oralInterviewVenue,oralInterviewDate);
        
        populateColumnHeaders(columnHeaders);
        try {
            studentRecords = allRecords;
            writeExcelData(batchSettings);
        } catch (IOException ex) {
            log.error("An Error has Occurred :::", ex);
            return false;
        }
        return true;
    }
    
    private void writeExcelData(BatchSettings batchSettings) throws IOException {
        @Cleanup
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        
        studentRecords.forEach((StudentRecord aRecord) -> {
           
            Student student = aRecord.getStudent();
            String[] rowValues = {
                String.valueOf(rowCount), student.getFullName(), genericBean.gender(student.getGender()), aRecord.getSss(),
                genericBean.department(aRecord.getDepartment()),genericBean.batch(aRecord.getExamBatch()),
                (StringUtils.equalsIgnoreCase(BATCH_A, aRecord.getExamBatch())? batchSettings.getBatchA_Start() : batchSettings.getBatchB_Start()) + " a.m",
                aRecord.getExamClass(),aRecord.getInterviewSlot()
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
        
        File filePath = globalRegistry.createFile(globalRegistry.getInitFilePath() + SCHOOL_FOLDER, fileName);
        
        @Cleanup
        FileOutputStream fileStream = new FileOutputStream(filePath);
        outStream.writeTo(fileStream);
        outStream.flush();
    }
    
    @Override
    public void destroy(){
        super.destroy();
        school = null;
        columnHeaders = null;
    }
}
