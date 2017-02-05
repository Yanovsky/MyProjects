package ru.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ru.crystals.pos.fiscalprinter.nonfiscalmode.emulator.exceptions.ManualExceptionAppender;

public class TestRMI {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8887);

        ManualExceptionAppender hw = (ManualExceptionAppender) registry.lookup("ManualExceptionAppender");
        System.out.println(hw.hasCutter());
    }
}
