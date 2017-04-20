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
import org.javasoft.peasoft.entity.core.School;
import org.javasoft.peasoft.entity.core.StudentRecord;
import org.javasoft.peasoft.excel.ExcelProcessor;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SchoolListExcelReport extends ExcelProcessor {

    private List<School> schools;

    private String[] columnHeaders = {"S/N", "IdentificationNo", "Name", "Address", "Total Count", "Art Students", "Commercial Students",
        "Science Students", "Selected Students", "Not Selected Students", "Declined Students", "Disqualified Students"};
    
    private String fileName;

    private String[] sheetHeaders = {"LIST OF SCHOOLS"};

    public boolean populateExcelSheet(String sheetName, List<School> allSchools, String fileName) {
        if (allSchools == null || allSchools.isEmpty() || StringUtils.isBlank(sheetName) || StringUtils.isBlank(fileName)) {
            log.error("=== SheetName or Schools List is not supplied . Excel sheet can't be generated");
            return false;
        }
        
        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        schools = allSchools;
        populateSheetHeaders(sheetHeaders);
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

        int currentPosition = getRowPosition();
        int no = 1;

        for (School aSchool : schools) {

            List<StudentRecord> records = aSchool.getStudentRecords();
            int artStudents = 0, commercialStudents = 0, scienceStudents = 0, selectedStudents = 0, notSelectedStudents = 0,
                    declinedStudents = 0, disqualifiedStudents = 0;
            for (StudentRecord aRecord : records) {
                if (aRecord.isArts()) {
                    artStudents++;
                } else if (aRecord.isCommercial()) {
                    commercialStudents++;
                } else if (aRecord.isScience()) {
                    scienceStudents++;
                } else if (aRecord.isSelected()) {
                    selectedStudents++;
                } else if (aRecord.isNotSelected()) {
                    notSelectedStudents++;
                } else if (aRecord.isDeclined()) {
                    declinedStudents++;
                } else if (aRecord.isDisqualified()) {
                    disqualifiedStudents++;
                }
            }

            String[] rowValues = {
                String.valueOf(no), aSchool.getIdentificationNo(), aSchool.getName(), aSchool.getAddressTemplate().getFullAddress(),
                String.valueOf(aSchool.getStudentCount()), String.valueOf(artStudents), String.valueOf(commercialStudents),
                String.valueOf(scienceStudents), String.valueOf(selectedStudents), String.valueOf(notSelectedStudents),
                String.valueOf(declinedStudents), String.valueOf(disqualifiedStudents)
            };

            Row row = getCurrentSheet().createRow(currentPosition);
            for (int i = 0; i < rowValues.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(getCellContentsStyle());
                cell.setCellValue(rowValues[i]);
            }
            currentPosition++;
            no++;
        }
        writeTo(outStream);
        //String fileName = DateFormatUtils.format(new Date(), "yyyyMMdd")+"_"+ RandomStringUtils.randomNumeric(5)+".xls";  
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
        schools = null;
        columnHeaders = null;
        sheetHeaders = null;      
    }
}
