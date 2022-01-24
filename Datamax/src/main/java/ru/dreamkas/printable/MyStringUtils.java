package ru.dreamkas.printable;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtils {

    public static void main(String[] args) {
        Font font = new Font("Roboto", Font.BOLD, 15);
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);
        String str = "Морковь Россия проверка длины строки";
        List<String> lines = MyStringUtils.wordWrap(str, fontMetrics, 146);
        System.out.println(lines);
    }

    public static List<String> wordWrap(String wrapMe, FontMetrics fm, int wrapInPixels){
        String[] split = wrapMe.split(" ");

        ArrayList<String> lines = new ArrayList<>();
        String currentLine = "";

        for(String s : split) {
            if( fm.stringWidth(currentLine + " " + s) >= wrapInPixels ) {
                if (StringUtils.isNotBlank(currentLine)) {
                    lines.add(currentLine);
                    currentLine = s;
                    continue;
                }
            }
            if (StringUtils.isNotBlank(currentLine))
                currentLine = currentLine + " ";

            currentLine = currentLine + s;
        }
        if (StringUtils.isNotBlank(currentLine))
            lines.add(currentLine);

        return lines;
    }
}
