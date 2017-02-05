package ru.alex.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestXLSX {
    private static String[] fieldNames = { "barcode", "name", "price", "isWeight", "isAlcohol", "alc_code", "capacity", "toDelete" };
    public static void main(String[] args) throws Exception {
        load();
    }

    public static void load() throws Exception {
        FileInputStream fileIn = new FileInputStream(new File("goods.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
        XSSFSheet sh = workbook.getSheetAt(0);

        XSSFRow row = sh.getRow(0);
        Map<String, Short> fieldReferences = new HashMap<>();
        for (short fieldIndex = 0; fieldIndex < fieldNames.length; fieldIndex++) {
            for (short columnIndex = row.getFirstCellNum(); columnIndex < row.getLastCellNum(); columnIndex++) {
                XSSFCell cell = row.getCell(columnIndex);
                if (fieldNames[fieldIndex].equalsIgnoreCase(cell.toString())) {
                    fieldReferences.put(fieldNames[fieldIndex], columnIndex);
                }
            }
        }

        for (int rowIndex = sh.getFirstRowNum() + 1; rowIndex <= sh.getLastRowNum(); rowIndex++) {
            StringBuilder sb = new StringBuilder();
            row = sh.getRow(rowIndex);
            sb.append(row.getRowNum());
            sb.append(" ");
            XSSFCell cell;
            cell = row.getCell(fieldReferences.get("barcode"));
            sb.append("barcode: \"" + getCellData(cell) + "\"; ");
            cell = row.getCell(fieldReferences.get("name"));
            sb.append("name: \"" + getCellData(cell) + "\"; ");
            cell = row.getCell(fieldReferences.get("price"));
            sb.append("price: " + getCellData(cell) + "; ");
            cell = row.getCell(fieldReferences.get("alc_code"));
            sb.append("alc_code: " + getCellData(cell) + "; ");
            cell = row.getCell(fieldReferences.get("capacity"));
            sb.append("capacity: " + getCellData(cell) + "; ");
            cell = row.getCell(fieldReferences.get("isWeight"));
            sb.append("isWeight: " + getBooleanCellData(cell) + " ");
            System.out.println(row.getCell(3));
            cell = row.getCell(fieldReferences.get("isAlcohol"));
            sb.append("isAlcohol: " + getBooleanCellData(cell) + " ");
            cell = row.getCell(fieldReferences.get("toDelete"));
            sb.append("toDelete: " + getBooleanCellData(cell) + " ");
            System.out.println(sb.toString());
        }
        workbook.close();
        fileIn.close();
    }

    private static boolean getBooleanCellData(XSSFCell cell) {
        Object value = getCellData(cell);
        if (value != null) {
            if (value instanceof Double) {
                return BooleanUtils.toBoolean(((Double) value).intValue());
            }
            return BooleanUtils.toBoolean(value.toString());
        }
        return false;
    }

    private static Object getCellData(XSSFCell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_ERROR:
        case Cell.CELL_TYPE_BLANK:
            return "";
        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue();
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        case Cell.CELL_TYPE_NUMERIC:
            return cell.getNumericCellValue();
        case Cell.CELL_TYPE_FORMULA:
            return cell.toString();
        }
        return null;
    }

    public static void save() throws Exception {
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
            row.createCell(12).setCellType(Cell.CELL_TYPE_BOOLEAN);
            row.createCell(11).setCellValue(true);
            row.createCell(12).setCellType(Cell.CELL_TYPE_BOOLEAN);
            row.createCell(12).setCellValue(false);
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
