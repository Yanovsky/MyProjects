package ru.dreamkas.update.data;

public class VersionInfo {
    private final String from;
    private final String to;

    public VersionInfo(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
