package ru.crystals.egais;

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

}
