package ru.alex.xls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import sun.awt.shell.ShellFolder;

public class USBDetector {

    public USBDetector() {
//        for (File d : oldListRoot) {
//            try {
//                if (d.exists()) {
//                    System.out.println(d + " - " + ShellFolder.getShellFolder(d).getFolderType());
//                } else {
//                    System.out.println(d + " не найден");
//                }
//            } catch (Exception e) {
//                e.printStackTrace(System.out);
//            }
//        }
        waitForNotifying();
    }

    private void waitForNotifying() {
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                File[] oldListRoot = File.listRoots();
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                        File[] newListRoot = File.listRoots();
                        if (newListRoot.length > oldListRoot.length) {
                            File d = subtract(newListRoot, oldListRoot);
                            if (d.exists()) { 
                                String type = ShellFolder.getShellFolder(d).getFolderType();
                                if (StringUtils.containsAny(type.toLowerCase(), "съемный", "removable")) {
                                    System.out.println(type + " " + d + " detected");
                                }
                            }
                            oldListRoot = File.listRoots();
                        } else if (newListRoot.length < oldListRoot.length) {
                            File d = subtract(oldListRoot, newListRoot);
                            System.out.println("drive " + d + " removed");
                            oldListRoot = File.listRoots();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    protected <T> T subtract(T[] array1, T[] array2) {
        List<T> result = new ArrayList<>();
        for (T o : array1) {
            if (ArrayUtils.indexOf(array2, o) < 0) {
                result.add(o);
            }
        }
        return result.size() > 0 ? result.remove(0) : null;
    }

    public static void main(String[] args) {
        new USBDetector();
    }
}
