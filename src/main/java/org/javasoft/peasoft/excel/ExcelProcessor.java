/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.javasoft.peasoft.utils.GlobalRegistry;

/**
 *
 * @author ayojava
 */
public abstract class ExcelProcessor {

    private Workbook workbook;

    private Sheet currentSheet;

    private int rowPosition = 1;

    private CellStyle headerTitleStyle, columnTitleStyle, cellContentStyle;
    
    protected GlobalRegistry globalRegistry;
    
    public void init(){
        globalRegistry= GlobalRegistry.getInstance();
    }
    
    public void populateSheetHeaders(String... titles) {
        Stream.of(titles).forEach(
                aTitle -> {
                    Row row = currentSheet.createRow(rowPosition);
                    Cell cell = row.createCell(1);
                    cell.setCellStyle(getSheetHeaderStyle());
                    cell.setCellValue(aTitle);
                    row.setHeightInPoints(30f);
                    rowPosition++;
                }
        );
    }

    public void populateColumnHeaders(String... columnHeaders) {
        rowPosition++;
        Row row = currentSheet.createRow(rowPosition);
        //row.setHeightInPoints(18f);

        for (int x = 0; x < columnHeaders.length; x++) {
            if (columnHeaders[x] == null) {
                continue;
            }
            Cell cell = row.createCell(x);
            cell.setCellStyle(getColumnTitleStyle());
            cell.setCellValue(columnHeaders[x]);
            currentSheet.autoSizeColumn(x);
        }
        rowPosition++;
    }

    private CellStyle getColumnTitleStyle() {
        if (columnTitleStyle == null) {
            Font font = getWorkbook().createFont();
            font.setFontHeightInPoints((short) 11);
            font.setFontName("Century Gothic");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            columnTitleStyle = getWorkbook().createCellStyle();
            columnTitleStyle.setAlignment(CellStyle.ALIGN_CENTER);
            columnTitleStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
            columnTitleStyle.setFont(font);
            columnTitleStyle.setWrapText(true);
            columnTitleStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
        }
        return columnTitleStyle;
    }

    private CellStyle getSheetHeaderStyle() {
        if (headerTitleStyle == null) {
            Font font = getWorkbook().createFont();
            font.setFontHeightInPoints((short) 15);
            font.setFontName("Century Gothic");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setUnderline(Font.U_SINGLE);

            headerTitleStyle = getWorkbook().createCellStyle();
            headerTitleStyle.setAlignment(CellStyle.ALIGN_LEFT);
            headerTitleStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
            headerTitleStyle.setFont(font);
        }
        return headerTitleStyle;
    }

    public CellStyle getCellContentsStyle() {
        if (cellContentStyle == null) {
            Font font = getWorkbook().createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Century Gothic");
            cellContentStyle = getWorkbook().createCellStyle();
            cellContentStyle.setFont(font);
        }
        return cellContentStyle;
    }

    public void setCurrentSheet(String sheetName) {
        currentSheet = getWorkbook().createSheet(sheetName);
    }

    public Workbook getWorkbook() {
        if (workbook == null) {
            workbook = new HSSFWorkbook();
        }
        return workbook;
    }

    public void writeTo(OutputStream outStream) throws IOException {
        getWorkbook().write(outStream);
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public void destroy() {
        workbook = null;
        currentSheet = null;
        headerTitleStyle = null;
        columnTitleStyle = null;
        cellContentStyle = null;
    }
}
