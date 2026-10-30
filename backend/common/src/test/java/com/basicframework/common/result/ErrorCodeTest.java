package com.basicframework.common.result;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorCodeTest {

    @Test
    void successIsZero() {
        assertThat(ErrorCode.SUCCESS.getCode()).isZero();
    }

    @Test
    void codesAreUnique() {
        Set<Integer> codes = Arrays.stream(ErrorCode.values())
                .map(ErrorCode::getCode)
                .collect(Collectors.toSet());

        assertThat(codes).hasSize(ErrorCode.values().length);
    }

    @Test
    void messagesAreNotBlank() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertThat(errorCode.getMessage()).isNotBlank();
        }
    }
}
