package ru.dreamkas;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import ru.dreamkas.datamax.Datamax;

public class DatamaxTest {
/*
1 9 1 1 006 0200 0200 ROTATION 1
191100600500090ROTATION 1

1 9 1 1 006 0050 0090 ROTATION 1

1 F 1 3 050 0025 0090 2208077013543
 */

    public static void main(String[] args) {
        Map<String, String> parameters = Arrays.stream(args)
            .collect(
                Collectors.toMap(
                    s -> StringUtils.trimToEmpty(StringUtils.substringBefore(s, "=")),
                    s -> StringUtils.trimToNull(StringUtils.substringAfter(s, "="))
                )
            );
        try {
            new Datamax(parameters).printOnSystemPrinter();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
