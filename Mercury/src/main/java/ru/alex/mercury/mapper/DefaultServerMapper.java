package ru.alex.mercury.mapper;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * ObjectMapper для POJO сервера обновлений и сервера хаба Позволяет мапить сущности в/из JSON Tree Model ({@link JsonNode})
 */
public class DefaultServerMapper {
    private static DefaultServerMapper defaultServerMapper;

    public static DefaultServerMapper getInstance() {
        if (defaultServerMapper == null) {
            defaultServerMapper = new DefaultServerMapper();
        }
        return defaultServerMapper;
    }

    protected final ObjectMapper mapper = new ObjectMapper();

    public DefaultServerMapper() {
        mapper.registerModule(new JavaTimeModule())
            .registerModule(SemverJacksonModule.simpleModule())
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(StdDateFormat.getInstance())
            .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public <C> C createAnyData(JsonNode node, Class<C> clazz) throws JsonProcessingException {
        return mapper.treeToValue(node, clazz);
    }

    public <C> C createAnyData(String data, Class<C> clazz) throws IOException {
        return mapper.readValue(data, clazz);
    }

    public <T> T createAnyList(String data, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(data, typeReference);
    }
}
