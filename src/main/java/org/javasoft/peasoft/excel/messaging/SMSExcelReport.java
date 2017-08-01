/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.excel.messaging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.excel.ExcelProcessor;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SMSExcelReport extends ExcelProcessor {

    private String[] columnHeaders = {"S/N","message", "phoneNo"};

    private List<SMSData> allSMSData;

    private String fileName;

    private int rowCount = 1;

    public boolean populateExcelSheet(String sheetName, String fileName, List<SMSData> allSMS) {

        init();
        this.fileName = fileName;
        setCurrentSheet(sheetName);
        populateSheetHeaders("Nothing to populate");
        populateColumnHeaders(columnHeaders);

        try {
            allSMSData = allSMS;
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

        allSMSData.stream().forEach((SMSData smsdata) -> {
            String[] rowValues = {
                String.valueOf(rowCount),smsdata.getMessage(),smsdata.getRecipientPhoneNo()
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
        
        File filePath = globalRegistry.createFile(globalRegistry.getInitFilePath() + "SMS", fileName);
        
        @Cleanup
        FileOutputStream fileStream = new FileOutputStream(filePath);
        outStream.writeTo(fileStream);
        outStream.flush();
    }
    
    @Override
    public void destroy(){
        super.destroy();
        allSMSData = null;
        columnHeaders = null;
    }
}
