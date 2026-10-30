package com.basicframework.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具：统一项目内的时间格式（yyyy-MM-dd HH:mm:ss）。
 */
public final class DateTimeUtils {

    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeUtils() {
    }

    public static String format(LocalDateTime time) {
        return time.format(DEFAULT_FORMATTER);
    }

    public static LocalDateTime parse(String text) {
        return LocalDateTime.parse(text, DEFAULT_FORMATTER);
    }
}
