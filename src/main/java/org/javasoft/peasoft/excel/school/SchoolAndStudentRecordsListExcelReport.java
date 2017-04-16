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
import static org.javasoft.peasoft.constants.PeaResource.SCHOOL_FOLDER;
import org.javasoft.peasoft.entity.brainChallenge.Marks;
import org.javasoft.peasoft.entity.brainChallenge.School;
import org.javasoft.peasoft.entity.brainChallenge.Student;
import org.javasoft.peasoft.entity.brainChallenge.StudentRecord;
import org.javasoft.peasoft.excel.ExcelProcessor;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SchoolAndStudentRecordsListExcelReport extends ExcelProcessor {

    private School school;

    private int rowCount = 1;

    private String[] columnHeaders = {"S/N", "Name", "Gender", "Class", "Department", "Status", "Maths (%)", "English (%)",
        "Current Affairs (%)", "ICT Score (%)", "Communication Skill (%)", "Personal Awareness (%)", "SelfAwareness (%)",
        "PlansAndGoals (%)", "BookKnowledge (%)", "Confidence Level (%) ", "Total Score (%)", "Grade (%)"};

    private String fileName;

    public boolean populateExcelSheet(String sheetName, String fileName, School schoolObj) {
        if (schoolObj == null || schoolObj.getStudentRecords().isEmpty() || StringUtils.isBlank(sheetName) || StringUtils.isBlank(fileName)) {
            log.error("=== SheetName or School is not supplied . Excel sheet can't be generated");
            return false;
        }

        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        school = schoolObj;
        String schoolName = "NAME  : " + school.getName();
        String schoolAddress = "ADDRESS  : " + school.getAddressTemplate().getFullAddress();
        populateSheetHeaders(schoolName, schoolAddress);
        populateColumnHeaders(columnHeaders);
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

        //int currentPosition = getRowPosition();
        //int no = 1;

//        private String[] columnHeaders = {"S/N" ,"Name" ,"Gender" ,"Class" ,"Department" ,"Status" ,"Maths (%)","English (%)",
//        "Current Affairs (%)","ICT Score (%)","Communication Skill (%)","Personal Awareness (%)","SelfAwareness (%)",
//        "PlansAndGoals (%)","BookKnowledge (%)","Confidence Level (%) ","Total Score (%)","Grade (%)"};
        List<StudentRecord> studentRecords = school.getStudentRecords();
        studentRecords.forEach((StudentRecord aRecord) -> {
            Student student = aRecord.getStudent();
            Marks mark = aRecord.getMarks();
            String[] rowValues = {
                String.valueOf(rowCount), student.getFullName(), student.getGender(), aRecord.getSss(), aRecord.getDepartment(),
                aRecord.getStatus(), String.valueOf(mark.getMathScore()), String.valueOf(mark.getEnglishScore()), String.valueOf(mark.getCurrentAffairsScore()),
                String.valueOf(mark.getIctScore()),String.valueOf(mark.getCommunicationSkill()),String.valueOf(mark.getPersonalAppearance()),
                String.valueOf(mark.getSelfAwareness()),String.valueOf(mark.getPlansAndGoals()),String.valueOf(mark.getBookKnowledge()),
                String.valueOf(mark.getConfidenceLevel()),String.valueOf(mark.getTotalScore()),aRecord.getGrade()
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
        
        log.info(" Directory ::: [ {} ]    FileName ::: [ {} ]" ,globalRegistry.getInitFilePath() + SCHOOL_FOLDER, fileName );
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
