package ru.crystals.egais;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Commons {

    private static final String WEB_PATH = "./web/";
    private static final String SIGN_SHORT_CHARS = "0123456789abcdef";
    private static final String SIGN_LONG_CHARS = SIGN_SHORT_CHARS.toUpperCase();
    private static final String INDEX_HTML = "index.html";
    private static final String BOM_CHAR = "\uFEFF";

    protected static String openFile(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                return StringUtils.removeStart(new String(file.getBytes(), StandardCharsets.UTF_8), BOM_CHAR);
            }
        } catch (Exception e) {
        }
        return null;
    }

    protected static String generateShortSign() {
        return RandomStringUtils.random(8, SIGN_SHORT_CHARS) + "-" +
                RandomStringUtils.random(4, SIGN_SHORT_CHARS) + "-" +
                RandomStringUtils.random(4, SIGN_SHORT_CHARS) + "-" +
                RandomStringUtils.random(4, SIGN_SHORT_CHARS) + "-" +
                RandomStringUtils.random(12, SIGN_SHORT_CHARS);
    }

    public static String generateLongSign() {
        return RandomStringUtils.random(128, SIGN_LONG_CHARS);
    }

    public static byte[] getBytes(String path) {
        return readBytesFromFile(WEB_PATH + (StringUtils.isBlank(path) ? INDEX_HTML : path));
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

    public static void main(String[] args) {
        System.out.println(getCurrentIp().getHostAddress());
    }

    public static InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress()
                            && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
            // LOG.error("unable to get current IP " + e.getMessage(), e);
        }
        return null;
    }

}
