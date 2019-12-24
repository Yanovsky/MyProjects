package ru.alex.hub.response;

public class HubResponse {
    private String message;
    private String result;
    private Data data;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
}
