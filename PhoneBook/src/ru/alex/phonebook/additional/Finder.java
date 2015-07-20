package ru.alex.phonebook.additional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class Finder {

    public List<File> search(String root, String extention) {
        List<File> files = new ArrayList<File>();

        File rootFolder = new File(root);
        if (rootFolder.isDirectory()) {
            inspectFolder(rootFolder, extention, files);
        }

        return files;
    }

    private void inspectFolder(File folder, String extention, List<File> files) {
        if (folder != null && folder.listFiles() != null) {
            for (File f : folder.listFiles()) {
                if (f.isFile()) {
                    if (StringUtils.equalsIgnoreCase(FilenameUtils.getExtension(f.getName()), extention)) {
                        files.add(f);
                    }
                } else if (f.isDirectory()) {
                    inspectFolder(f, extention, files);
                }
            }
        }
    }

}
