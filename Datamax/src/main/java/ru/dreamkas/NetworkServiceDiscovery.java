package ru.dreamkas;

import java.util.List;
import java.util.stream.Collectors;

import ru.dreamkas.dns.VikiPrintInfo;
import ru.dreamkas.dns.VikiPrintWiFiResolver;
import ru.dreamkas.utils.time.StopTimer;

public class NetworkServiceDiscovery {
    public static void main(String[] args) {
        StopTimer timer = new StopTimer();
        List<VikiPrintInfo> info = new VikiPrintWiFiResolver().resolve();
        System.out.printf("Resolve complete in %s%nFound:%n\t%s%n",
            timer.getElapsedTimeAsString(),
            info.stream().map(VikiPrintInfo::toString).collect(Collectors.joining("\n\t"))
        );
    }
}
