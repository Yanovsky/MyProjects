package ru.dreamkas.update.data;

public class FileInfo {
    private long size;
    private String md5;
    private String patch;

    public long getSize() {
        return size;
    }

    public FileInfo setSize(long size) {
        this.size = size;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public FileInfo setMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public String getPatch() {
        return patch;
    }

    public FileInfo setPatch(String patch) {
        this.patch = patch;
        return this;
    }
}
