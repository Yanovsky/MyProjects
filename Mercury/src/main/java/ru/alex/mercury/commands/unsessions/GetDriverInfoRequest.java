package ru.alex.mercury.commands.unsessions;

import ru.alex.mercury.commands.Request;

public class GetDriverInfoRequest extends Request {
    public GetDriverInfoRequest() {
        setCommand("GetDriverInfo");
    }
}
