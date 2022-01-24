package ru.dreamkas.start.server;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ru.dreamkas.semver.Version;

public class SemverJacksonModule {
    public static SimpleModule simpleModule() {
        JsonDeserializer<Version> semverDeserializer = semverDeserializer();
        JsonSerializer<Version> semverSerializer = semverSerializer();
        return new SimpleModule().addDeserializer(Version.class, semverDeserializer).addSerializer(Version.class, semverSerializer);
    }

    private static JsonDeserializer<Version> semverDeserializer() {
        return new JsonDeserializer<Version>() {
            @Override
            public Version deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return Version.of(p.getValueAsString());
            }
        };
    }

    private static JsonSerializer<Version> semverSerializer() {
        return new JsonSerializer<Version>() {
            @Override
            public void serialize(Version value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.toString());
            }
        };
    }
}
