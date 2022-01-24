package ru.dreamkas.ucs;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface UcsNative extends Library {
    Pointer eftp_create(Pointer path);

    int eftp_do(Pointer pvSelf, Pointer pchInBuffer, Pointer pchOutBuffer, Pointer pfIdle, Pointer pbData);

    void eftp_destroy(Pointer pvSelf);
}
