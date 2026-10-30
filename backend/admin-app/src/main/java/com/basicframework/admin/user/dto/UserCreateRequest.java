package com.basicframework.admin.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "username 不能为空") @Size(max = 64, message = "username 长度不能超过 64") String username,
        @NotBlank(message = "password 不能为空") @Size(min = 6, max = 64, message = "password 长度需在 6-64 之间") String password,
        @Size(max = 64, message = "nickname 长度不能超过 64") String nickname) {
}
