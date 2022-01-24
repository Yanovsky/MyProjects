package ru.dreamkas.dns;

import java.util.Objects;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import com.sun.istack.internal.NotNull;

public class VikiPrintInfo implements Comparable<VikiPrintInfo> {
    private final String name;
    private final String factoryNumber;
    private final String host;
    private final int port;

    public VikiPrintInfo(String name, String host, int port) {
        if (name == null) {
            throw new IllegalStateException();
        }
        this.name = name;
        this.factoryNumber = StringUtils.substringAfter(name, "viki_print_");
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VikiPrintInfo that = (VikiPrintInfo) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VikiPrintInfo.class.getSimpleName() + "(", ")")
            .add("name='" + name + "'")
            .add("factoryNumber='" + factoryNumber + "'")
            .add("host='" + host + "'")
            .add("port=" + port)
            .toString();
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }


    @Override
    public int compareTo(@NotNull VikiPrintInfo other) {
        return StringUtils.compare(name, other.name);
    }
}
