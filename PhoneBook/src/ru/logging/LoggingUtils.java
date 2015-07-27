package ru.logging;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LoggingUtils {

    public static String logClass(Object obj) {
        return ReflectionToStringBuilder.toString(obj, new RecursiveToStringStyle());
    }

    public static String logMethodInvokeWithTrace(Class<?> clazz, String methodName, Object... params) {
        String result = logMethodInvoke(clazz, methodName, params);
        result += "\r\n";
        result += LoggingUtils.getStackTrace(Thread.currentThread().getStackTrace());

        return result;
    }

    public static String logMethodInvoke(Class<?> clazz, String methodName, Object... params) {
        String logString = "Invoked method: " + clazz.getSimpleName() + "." + methodName;
        logString += "(";
        if (params.length > 0) {
            for (Object param : params) {
                if (param instanceof String) {
                    logString += "\"" + param.toString() + "\"";
                } else if (param instanceof BigDecimal) {
                    logString += param.getClass().getSimpleName() + "[" + param.toString() + "]";
                } else if (param instanceof BigInteger) {
                    logString += param.getClass().getSimpleName() + "[" + param.toString() + "]";
                } else {
                    logString += ReflectionToStringBuilder.toString(param, new RecursiveToStringStyle());
                }
                logString += ", ";
            }
            logString = logString.substring(0, logString.lastIndexOf(", "));
        }
        logString += ")";
        return logString;
    }

    private static String getStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder("StackTrace is:\r");
        String current = LoggingUtils.class.getName();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith("ru.crystals.") && !StringUtils.equals(element.getClassName(), current)) {
                sb.append("\t");
                String className = StringUtils.substringAfterLast(element.getClassName(), ".");
                sb.append(className + "." + element.getMethodName());
                sb.append("(" + element.getFileName() + ":" + element.getLineNumber() + ")");
                sb.append("\r\n");
            }
        }
        sb.append("\t...");
        return sb.toString();
    }

}
