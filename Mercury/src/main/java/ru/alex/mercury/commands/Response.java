package ru.alex.mercury.commands;

import java.io.IOException;

import ru.alex.mercury.mapper.DefaultServerMapper;

@SuppressWarnings("unused")
public class Response extends OwnToString {
    protected static DefaultServerMapper mapper = new DefaultServerMapper();
    protected int result;
    protected String description;
    protected String sessionKey;

    public int getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public static <T> T fromBytes(byte[] bytes, Class<T> valueType) throws IOException {
        return mapper.getMapper().readValue(bytes, valueType);
    }
}
