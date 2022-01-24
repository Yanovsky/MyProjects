package ru.alex.mercury.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class OwnToString {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append('{').append("\r\n");
        for (Field field : this.getClass().getSuperclass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value != null) {
                    sb.append("\t").append(field.getName()).append("=").append("'").append(value).append("'").append("\r\n");
                }
            } catch (IllegalAccessException ignored) {}
        }
        for (Field field : this.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value != null) {
                    sb.append("\t").append(field.getName()).append("=").append("'").append(value).append("'").append("\r\n");
                }
            } catch (IllegalAccessException ignored) {}
        }
        return sb.append('}').toString();
    }
}
