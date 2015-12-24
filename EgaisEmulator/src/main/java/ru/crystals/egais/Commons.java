package ru.crystals.egais;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Commons {

    private static final String WEB_PATH = "./web/";

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

    public static byte[] getBytes(String path) {
        return readBytesFromFile(WEB_PATH + (StringUtils.isBlank(path) ? "index.html" : path));
    }

    public static byte[] readBytesFromFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            FileInputStream is = null;
            DataInputStream reader = null;
            try {
                is = new FileInputStream(file);
                reader = new DataInputStream(is);
                byte bytes[] = new byte[is.available()];
                reader.readFully(bytes);
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }

    public static byte[] readBytesFromJar(String path) {
        InputStream is = null;
        DataInputStream reader = null;
        try {
            is = FileUtils.class.getResourceAsStream(path);
            reader = new DataInputStream(is);
            byte bytes[] = new byte[is.available()];
            reader.readFully(bytes);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    public static String readTextFromJar(String path) {
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = FileUtils.class.getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
