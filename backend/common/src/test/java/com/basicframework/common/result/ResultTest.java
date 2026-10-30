package com.basicframework.common.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    @Test
    void okWithData() {
        Result<String> result = Result.ok("hello");

        assertThat(result.getCode()).isZero();
        assertThat(result.getMessage()).isNotBlank();
        assertThat(result.getData()).isEqualTo("hello");
    }

    @Test
    void okWithoutData() {
        Result<Void> result = Result.ok();

        assertThat(result.getCode()).isZero();
        assertThat(result.getData()).isNull();
    }

    @Test
    void errorUsesErrorCode() {
        Result<Void> result = Result.error(ErrorCode.UNAUTHORIZED);

        assertThat(result.getCode()).isEqualTo(ErrorCode.UNAUTHORIZED.getCode());
        assertThat(result.getMessage()).isEqualTo(ErrorCode.UNAUTHORIZED.getMessage());
        assertThat(result.getData()).isNull();
    }

    @Test
    void errorWithCustomMessage() {
        Result<Void> result = Result.error(ErrorCode.BAD_REQUEST, "username 不能为空");

        assertThat(result.getCode()).isEqualTo(ErrorCode.BAD_REQUEST.getCode());
        assertThat(result.getMessage()).isEqualTo("username 不能为空");
    }
}
