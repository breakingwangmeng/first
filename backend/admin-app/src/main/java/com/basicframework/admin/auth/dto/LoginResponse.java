package com.basicframework.admin.auth.dto;

public record LoginResponse(String tokenName, String tokenValue, String nickname) {
}
