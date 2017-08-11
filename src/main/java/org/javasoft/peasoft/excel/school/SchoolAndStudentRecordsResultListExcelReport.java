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
import static org.javasoft.peasoft.constants.PeaResource.SCHOOL_FOLDER;
import org.javasoft.peasoft.entity.core.Marks;
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.Student;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.excel.ExcelProcessor;
import org.omnifaces.util.Beans;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SchoolAndStudentRecordsResultListExcelReport extends ExcelProcessor {

    private School school;

    private int rowCount = 1;
    
    private List<StudentRecord> studentRecords;

    private String[] columnHeaders = {"S/N", "Name", "Gender", "Class", "Department", "Status", "Maths (20)", "English (20)",
        "Current Affairs (10)", "ICT Score (10)", "Communication Skill (10)", "Personal Awareness (10)", "SelfAwareness (10)",
        "PlansAndGoals (10)", "BookKnowledge (10)", "Confidence Level (10) ", "Total Score (%)", "Grade "};

    private String fileName;

    public boolean populateExcelSheet(String sheetName, String fileName, School schoolObj, List<StudentRecord> allRecords) {
        if (schoolObj == null || allRecords.isEmpty() || StringUtils.isBlank(sheetName) || StringUtils.isBlank(fileName)) {
            log.error("=== SheetName or School is not supplied . Excel sheet can't be generated");
            return false;
        }

        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        school = schoolObj;
        String schoolName = "NAME  : " + school.getName();
        String schoolAddress = "ADDRESS  : " + school.getAddressTemplate().getFullAddress();
        populateSheetHeaders(schoolName, schoolAddress,"Total Academic Score : (Maths + English + Current Affairs + ICT Score)*100/60",
                "Total Interview Score : (Communication Skill + Personal Awareness + Self Awareness + PlansAndGoals + BookKnowlwedge"
                        + "+ Confidence Level ) * 100/60 ",
                "Total Score = (Total Academic Score + Total Interview Score)/2");
        populateColumnHeaders(columnHeaders);
        try {
            studentRecords = allRecords;
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

        //int currentPosition = getRowPosition();
        //int no = 1;

//        private String[] columnHeaders = {"S/N" ,"Name" ,"Gender" ,"Class" ,"Department" ,"Status" ,"Maths (%)","English (%)",
//        "Current Affairs (%)","ICT Score (%)","Communication Skill (%)","Personal Awareness (%)","SelfAwareness (%)",
//        "PlansAndGoals (%)","BookKnowledge (%)","Confidence Level (%) ","Total Score (%)","Grade (%)"};
       // List<StudentRecord> studentRecords = school.getStudentRecords();
        studentRecords.forEach((StudentRecord aRecord) -> {
            Student student = aRecord.getStudent();
            Marks mark = aRecord.getMarks();
            String[] rowValues = {
                String.valueOf(rowCount), student.getFullName(), genericBean.gender(student.getGender()), aRecord.getSss(),genericBean.department(aRecord.getDepartment()),
                genericBean.recordStatus(aRecord.getStatus()), String.valueOf(mark.getMathScore()), String.valueOf(mark.getEnglishScore()), String.valueOf(mark.getCurrentAffairsScore()),
                String.valueOf(mark.getIctScore()),String.valueOf(mark.getCommunicationSkill()),String.valueOf(mark.getPersonalAppearance()),
                String.valueOf(mark.getSelfAwareness()),String.valueOf(mark.getPlansAndGoals()),String.valueOf(mark.getBookKnowledge()),
                String.valueOf(mark.getConfidenceLevel()),String.valueOf(mark.getTotalScore()),genericBean.obtainedGrade(aRecord.getGrade())
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
