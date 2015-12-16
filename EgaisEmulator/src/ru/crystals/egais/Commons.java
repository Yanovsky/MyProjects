package ru.crystals.egais;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import ru.crystals.egais.generators.WayBillGenerator;

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
        return RandomStringUtils.random(125, "0123456789ABCDEF");
    }

    public static void main(String[] args) {
        WayBillGenerator.INSTANCE.setStartIndex(1);
        for (int i = 1; i <= 10; i++) {
            WayBillGenerator.INSTANCE.setWayBillNumber(RandomStringUtils.randomAlphanumeric(5)).generate("3463047");
        }
    }
}
