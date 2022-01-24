package ru.dreamkas.dns;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import ru.dreamkas.utils.time.Timer;

public class VikiPrintWiFiResolver implements ServiceListener {
    private static final String TYPE = "_piritserver._tcp.local.";
    private final List<VikiPrintInfo> vikiPrintInfo = new ArrayList<>();
    private Timer timer;

    public List<VikiPrintInfo> resolve() {
        Timer totalTimer = Timer.of(Duration.ofSeconds(20));
        try (JmDNS jmDNS = JmDNS.create()) {
            vikiPrintInfo.clear();
            timer = Timer.of(Duration.ofSeconds(1));
            jmDNS.addServiceListener(TYPE, this);
            while (timer.isNotExpired()) {
                Thread.yield();
                if (totalTimer.isExpired()) {
                    System.out.printf("Stop finding spend %s%n", totalTimer.getElapsedTimeAsString());
                    break;
                }
            }
            if (timer.isExpired()) {
                System.out.printf("Stop finding because after last founded spend %s%n", timer.getElapsedTimeAsString());
            }
            timer.restart();
        } catch (Exception e) {
            System.err.printf("Resolve throws an Exception %s%n", e.getMessage());
        }
        System.out.printf("JmDNS.close() in %s%n", timer.getElapsedTimeAsString());
        timer.restart();
        Collections.sort(vikiPrintInfo);
        System.out.printf("Sort complete in %s. Total resolve time is %s%n", timer.getElapsedTimeAsString(), totalTimer.getElapsedTimeAsString());
        return vikiPrintInfo;
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        ServiceInfo info = event.getInfo();
        VikiPrintInfo vikiPrintInfo = new VikiPrintInfo(event.getName(), Arrays.stream(info.getHostAddresses()).findFirst().orElse(null), info.getPort());
        this.vikiPrintInfo.add(vikiPrintInfo);
        System.out.printf("Found %s in %s%n", vikiPrintInfo, timer.getElapsedTimeAsString());
        timer.restart();
    }

    @Override
    public void serviceAdded(ServiceEvent event) {}

    @Override
    public void serviceRemoved(ServiceEvent event) {}
}
