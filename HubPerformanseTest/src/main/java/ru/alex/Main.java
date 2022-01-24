package ru.alex;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

import ru.alex.hub.Caller;
import ru.alex.hub.Mapper;
import ru.alex.hub.ReportItem;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(Mapper.INSTANCE.name());
        int THREAD_COUNT = 20000;
        Map<Integer, ReportItem> result = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        IntStream.range(0, THREAD_COUNT).forEach(number -> executor.execute(() -> {
            LocalDateTime startTime = LocalDateTime.now();
            long start = System.nanoTime();
            String response = new Caller().call();
            long finish = System.nanoTime();
            ReportItem item = new ReportItem(number, "Thread "+number, response)
                .setDuration(Duration.ofNanos(finish - start))
                .setStartTime(startTime);
            result.put(number, item);
            System.out.println(String.format("Thread %d has got a '%s'", number, response));
        }));
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.yield();
        }
        System.out.println("\nFinished all threads");
        FileUtils.writeLines(new File("D:\\Work\\MyProjects\\HubPerformanseTest\\result.txt"), result.values().stream().map(ReportItem::toCommaText).collect(Collectors.toList()));
    }
}
