package com.basicframework.admin.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 64, message = "nickname 长度不能超过 64") String nickname,
        @Min(value = 0, message = "status 只能是 0 或 1") @Max(value = 1, message = "status 只能是 0 或 1") Integer status) {
}
