package ru.alex.mercury.commands;

import java.nio.ByteBuffer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.alex.mercury.mapper.DefaultServerMapper;

public class Request extends OwnToString {
    protected static DefaultServerMapper mapper = new DefaultServerMapper();
    private String command;

    @JsonIgnore
    public byte[] getBytes() {
        try {
            byte[] outData = mapper.getMapper().writeValueAsBytes(this);
            return ByteBuffer.allocate(4 + outData.length).putInt(outData.length).put(outData).array();
        } catch (Exception e) {
            return ByteBuffer.allocate(4).putInt(0).array();
        }
    }

    public String getCommand() {
        return command;
    }

    public Request setCommand(String command) {
        this.command = command;
        return this;
    }
}
