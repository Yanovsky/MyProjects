package ru.alex.xls;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class TestXLS {

    public static void main(String[] args) throws Exception {
        WritableWorkbook workbook = Workbook.createWorkbook(new File("myfile1.xls"));
        WritableSheet sheet = workbook.createSheet("Sheet 1", 0);
        sheet.addCell(new Label(0, 0, "A label record"));
        sheet.addCell(new Label(0, 1, "B label record"));
        sheet.addCell(new Label(0, 2, "C label record"));

        WritableFont font = new WritableFont(WritableFont.ARIAL, 20);
        WritableCellFormat format = new WritableCellFormat(font, NumberFormats.FLOAT);
        jxl.write.Number number = new jxl.write.Number(3, 4, 3.1459, format);
        sheet.addCell(number);

        workbook.write(); 
        workbook.close();
    }
}
