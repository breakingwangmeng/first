package com.basicframework.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.basicframework.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权示例接口：演示 @SaCheckLogin 与统一响应体的配合。
 */
@RestController
@RequestMapping("/api")
public class ExampleController {

    @GetMapping("/example")
    @SaCheckLogin
    public Result<String> example() {
        return Result.ok("hello, " + StpUtil.getLoginId());
    }
}
