package ru.alex.hub;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ru.alex.hub.response.HubResponse;

public class ReportItem {
    private int index;
    private String name;
    private LocalDateTime startTime;
    private Duration duration;
    private BigDecimal price;
    private String message;
    private String result;

    public ReportItem(int index, String name, String response) {
        this.index = index;
        this.name = name;
        try {
            if (StringUtils.startsWith(response, "ERROR")) {
                result = "ERROR";
                message = response;
                return;
            }
            HubResponse hubResponse = Mapper.INSTANCE.getMapper().readValue(response, HubResponse.class);
            if (hubResponse != null) {
                result = hubResponse.getResult();
                message = hubResponse.getMessage();
                if (hubResponse.getData() != null) {
                    price = BigDecimal.valueOf(hubResponse.getData().getPrice()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                }
            }
        } catch (IOException e) {
            result = "ERROR";
            message = e.getMessage();
        }
    }

    public ReportItem setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ReportItem setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public String toCommaText() {
        return index + ";" +
            name + ";" +
            startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ";" +
            startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) + ";" +
            BigDecimal.valueOf(duration.getNano()).divide(BigDecimal.valueOf(1_000_000_000), 3, RoundingMode.HALF_UP) + ";" +
            price + ";" +
            result + ";" +
            StringUtils.trimToEmpty(message) + ";";
    }
}
