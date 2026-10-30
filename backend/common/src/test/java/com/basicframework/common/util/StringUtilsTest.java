package com.basicframework.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void blankDetection() {
        assertThat(StringUtils.isBlank(null)).isTrue();
        assertThat(StringUtils.isBlank("   ")).isTrue();
        assertThat(StringUtils.isBlank("a")).isFalse();
        assertThat(StringUtils.isNotBlank("a")).isTrue();
    }

    @Test
    void trimToEmptyNeverReturnsNull() {
        assertThat(StringUtils.trimToEmpty(null)).isEmpty();
        assertThat(StringUtils.trimToEmpty("  x  ")).isEqualTo("x");
    }
}
