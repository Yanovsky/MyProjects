package ru.alex.xls;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class TestXLSX {

    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("Книга 1");

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 20);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        int rowIndex = 0;
        {
            SXSSFRow row = sheet.createRow(rowIndex++);
            for (int col = 0; col < 10; col++) {
                row.createCell(col).setCellValue("Проверка " + RandomStringUtils.randomAlphanumeric(10));
            }
        }
        {
            SXSSFRow row = sheet.createRow(rowIndex++);
            for (int col = 0; col < 10; col++) {
                row.createCell(col).setCellValue(RandomUtils.nextInt(1, 100));
            }
        }
        {
            SXSSFRow row = sheet.createRow(rowIndex++);
            for (int col = 0; col < 10; col++) {
                SXSSFCell cell = row.createCell(col);
                cell.setCellStyle(style);
                cell.setCellValue(RandomUtils.nextDouble(1, 100));
            }
        }
        SXSSFRow rowexcel = sheet.getRow(sheet.getLastRowNum());
        for (short i = 0; i < rowexcel.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(new File("data.xlsx"));
        workbook.write(fileOut);
        fileOut.close();
    }
}
