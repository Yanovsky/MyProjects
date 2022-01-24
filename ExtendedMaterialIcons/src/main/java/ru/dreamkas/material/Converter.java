package ru.dreamkas.material;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Converter {

    public static void main(String[] args) {
        try {
            File file = Paths.get(
                "SetRetail10_Start", "touch", "ui",
                "gwt-base", "gwt-viki-material", "src", "main", "resources",
                "ru", "crystals", "viki", "gwt", "public", "css",
                "materialdesignicons.min.css"
            ).toFile();
            String last = null;
            StringBuilder content = new StringBuilder();
            for (String s : FileUtils.readLines(file)) {
                if (StringUtils.startsWithIgnoreCase(s, ".mdi-") && StringUtils.endsWithIgnoreCase(s, "::before {")) {
                    last = StringUtils.substringBetween(s, ".mdi-", "::before {");
                    continue;
                }
                if (last != null) {
                    String value = StringUtils.substringBetween(s, "content: \"", "\"");
                    if (StringUtils.length(value) == 6) {
                        value = "\"\\"+value+"\"";//"String.valueOf(Character.toChars(0x" + value + "))";
                    } else if (StringUtils.length(value) < 4) {
                        value = "\"\\u" + StringUtils.leftPad(value, 4, "0") + "\"";
                    } else {
                        value = "\"\\u" + value + "\"";
                    }

                    String element = last.replace("-", "_").toUpperCase();

                    Enum<?> enumiration;
                    try {
                        eit = ExtendedIconType.valueOf(element);
                    } catch (Exception ignored) {}

                    if (eit != null) {
                        content
                            .append(element)
                            .append("(")
                            .append("\"").append("mdi-").append(last).append("\", ").append(value)
                            .append("),")
                            .append("\n");
                    }
                    last = null;
                }
            }
            System.out.println(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
