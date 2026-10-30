package com.basicframework.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilsTest {

    @Test
    void formatLocalDateTime() {
        LocalDateTime time = LocalDateTime.of(2026, 10, 29, 12, 30, 45);

        assertThat(DateTimeUtils.format(time)).isEqualTo("2026-10-29 12:30:45");
    }

    @Test
    void parseRoundTrip() {
        LocalDateTime time = LocalDateTime.of(2026, 10, 29, 12, 30, 45);

        assertThat(DateTimeUtils.parse("2026-10-29 12:30:45")).isEqualTo(time);
    }
}
