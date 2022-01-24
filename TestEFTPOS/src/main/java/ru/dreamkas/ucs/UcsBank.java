package ru.dreamkas.ucs;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.SystemUtils;

import com.sun.jna.Native;

public enum UcsBank {
    INSTANCE;
    private UcsNative ucs;
    public static final String ENCODING = "cp1251";

    public UcsNative getUcs() throws Exception {
        if (ucs == null) {
            try {
                Path dllPath = Paths.get("banks", "bank_ucs")
                    .resolve(SystemUtils.IS_OS_WINDOWS ? "windows" : "linux_arm")
                    .resolve("ucs_ms")
                    .toAbsolutePath()
                    .normalize();
                ucs = Native.loadLibrary(dllPath.toString(), UcsNative.class);
            } catch (Throwable e) {
                throw new Exception("Can't load UCS library", e);
            }
        }
        return ucs;
    }
}
