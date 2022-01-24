package ru.dreamkas.ucs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import static ru.dreamkas.ucs.UcsBank.ENCODING;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, String> params = Arrays.stream(args)
            .map(StringUtils::deleteWhitespace)
            .collect(Collectors.toMap(e -> StringUtils.substringBefore(e, "="), e -> StringUtils.substringAfter(e, "=")));

        File output = Paths.get("output.log").toFile();
        FileUtils.deleteQuietly(output);
        File print = Paths.get("cheque.txt").toFile();
        FileUtils.deleteQuietly(print);
        writeOutFile(output, "Params:%n\t%s\n", params.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("\n\t")));

        Pointer cfgPath = new Memory(512);
        cfgPath.clear(512);
        cfgPath.setString(0, params.getOrDefault("cfg", "some.cfg"));

        Pointer pvSelf = UcsBank.INSTANCE.getUcs().eftp_create(cfgPath);
        String handle = Long.toHexString(pvSelf.getNativeLong(0).longValue()).toUpperCase();
        writeOutFile(output, "ucs.eftp_create(%s)=%s", cfgPath.getString(0, ENCODING), handle);

//        if (params.get("class").equals("1") && params.get("code").equals("0")) {
//            doOperation(pvSelf, output, print, params.get("terminalId"), "3", "0", "1");
//        }
        doOperation(pvSelf, output, print, params.get("terminalId"), params.get("class"), params.get("code"), params.getOrDefault("param", ""));

//        Executors.newCachedThreadPool().execute(() -> {
//            try {
//                Thread.sleep(1000);
//                Runtime.getRuntime().exec("TASKKILL /F /T /IM ucs_mm.exe");
//                logString(finalOutput, "ucs_mm.exe has killed");
//                System.exit(0);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        writeOutFile(output, "ucs.eftp_destroy(%s)", handle);
        UcsBank.INSTANCE.getUcs().eftp_destroy(pvSelf);
        writeOutFile(output, "destroyed");
    }

    private static void doOperation(Pointer pvSelf, File output, File print, String terminalId, String aClass, String aCode, String param) throws Exception {
        String handle = Long.toHexString(pvSelf.getNativeLong(0).longValue()).toUpperCase();
        String len = StringUtils.leftPad(Integer.toHexString(param.length()), 2, '0').toUpperCase();
        String request = aClass + aCode + terminalId + len + param;

        Pointer msgIn = new Memory(512);
        msgIn.clear(512);
        msgIn.setString(0, request);

        Pointer msgOut = new Memory(512);
        msgOut.clear(512);

        Pointer pfIdle = new Pointer(0);
        Pointer pbData = new Pointer(0);
        int result = 0;
        List<String> printStrings = new ArrayList<>();
        while (result == 0) {
            result = UcsBank.INSTANCE.getUcs().eftp_do(pvSelf, msgIn, msgOut, pfIdle, pbData);
            String out = msgOut.getString(0, ENCODING);
            int clazz = 0;
            char code = '\u0000';
            int length = 0;
            if (out.length() > 2) {
                clazz = Integer.parseInt(StringUtils.defaultIfBlank(StringUtils.substring(out, 0, 1), "0"));
                code = StringUtils.substring(out, 1, 2).charAt(0);
                length = Integer.parseInt(StringUtils.defaultIfBlank(StringUtils.substring(out, 12, 14), "00"), 16);
            }
            writeOutFile(output, "ucs.eftp_do(%s, '%s', '%s', %s, %s)=%d", handle, msgIn instanceof Memory ? msgIn.getString(0, ENCODING) : msgIn.toString(), msgOut.getString(0, ENCODING).replace("\n", "\\n"), pfIdle, pbData, result);
            msgIn = new Pointer(0);
            msgOut.clear(512);
            if (result == 0) {
                String value = StringUtils.substring(out, 14, length + 15);
                if (clazz == 3 && code == '2') {
                    //boolean lastLine = BooleanUtils.toBoolean(value.substring(0,1), "1", "0");
                    value = value.substring(1);
                    //if (!lastLine) {
                    printStrings.addAll(
                        Arrays.stream(value.split("\n"))
                            .filter(StringUtils::isNotBlank)
                            .collect(Collectors.toList())
                    );
                    //}
                } else if (clazz == 5 && code == 'M') {
                    System.out.println(value);
                }
            }
        }

        if (!printStrings.isEmpty()) {
            FileUtils.writeLines(print, ENCODING, printStrings, "\n");
        }
    }

    private static void writeOutFile(File output, String format, Object... params) {
        try {
            FileUtils.writeStringToFile(output, String.format(format, params) + '\n', ENCODING, true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
