package com.basicframework.api.auth;

import cn.dev33.satoken.stp.StpUtil;
import com.basicframework.api.auth.dto.TokenResponse;
import com.basicframework.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示用登录：仅用于展示 token 签发与鉴权链路，真实业务应替换为对外的鉴权方案。
 */
@RestController
@RequestMapping("/api/auth")
public class DemoAuthController {

    @PostMapping("/demo-login")
    public Result<TokenResponse> demoLogin() {
        StpUtil.login("demo");
        return Result.ok(new TokenResponse(StpUtil.getTokenName(), StpUtil.getTokenValue()));
    }
}
