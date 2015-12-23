package ru.crystals.egais;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Commons {

    protected static String openFile(MultipartFile file) {
        String result = null;
        try {
            if (!file.isEmpty()) {
                result = new String(file.getBytes());
                if (result.startsWith("\uFEFF")) {
                    result = result.substring(1);
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    protected static String generateShortSign() {
        return RandomStringUtils.random(8, "0123456789abcdef") + "-" +
                RandomStringUtils.random(4, "0123456789abcdef") + "-" +
                RandomStringUtils.random(4, "0123456789abcdef") + "-" +
                RandomStringUtils.random(4, "0123456789abcdef") + "-" +
                RandomStringUtils.random(12, "0123456789abcdef");
    }

    public static String generateLongSign() {
        return RandomStringUtils.random(128, "0123456789ABCDEF");
    }

    public static String getMainHTML(String url) {
        ClassLoader classLoader = Commons.class.getClassLoader();
        File file = new File(classLoader.getResource("ru/crystals/egais/TT.html").getFile());
        try {
            return FileUtils.readFileToString(file).replaceAll("./eagle.png", url + "/eagle.png");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
