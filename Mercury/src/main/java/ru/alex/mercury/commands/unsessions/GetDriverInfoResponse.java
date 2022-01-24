package ru.alex.mercury.commands.unsessions;

import ru.alex.mercury.commands.Response;
import ru.dreamkas.semver.Version;

@SuppressWarnings("unused")
public class GetDriverInfoResponse extends Response {
    private Version driverVer;
    private Version protocolVer;
    private Version driverBaseVer;

    public Version getDriverVer() {
        return driverVer;
    }

    public Version getProtocolVer() {
        return protocolVer;
    }

    public Version getDriverBaseVer() {
        return driverBaseVer;
    }

}
