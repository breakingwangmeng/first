package com.basicframework.admin.user.dto;

import java.time.LocalDateTime;

/**
 * 用户视图对象：刻意不包含 password 字段，避免敏感信息泄漏到接口。
 */
public record UserVO(Long id, String username, String nickname, Integer status, LocalDateTime createdAt) {
}
