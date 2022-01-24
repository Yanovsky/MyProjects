package ru.dreamkas.update.data;

import java.util.ArrayList;
import java.util.List;

public class Info {
    private final String header;
    private List<String> content = new ArrayList<>();

    public Info(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public Info setContent(List<String> content) {
        this.content = content;
        return this;
    }

    public List<String> getContent() {
        return content;
    }
}
