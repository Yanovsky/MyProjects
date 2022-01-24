package ru.dreamkas.update.data;

public class Patch {
    private FileInfo file;
    private VersionInfo version;

    public FileInfo getFile() {
        return file;
    }

    public Patch setFile(FileInfo file) {
        this.file = file;
        return this;
    }

    public VersionInfo getVersion() {
        return version;
    }

    public Patch setVersion(VersionInfo version) {
        this.version = version;
        return this;
    }
}
