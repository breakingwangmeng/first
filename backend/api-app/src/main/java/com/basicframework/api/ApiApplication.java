package com.basicframework.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 开放服务应用（端口 8081）：健康检查 + Sa-Token 鉴权示例。
 * 与 admin-app 完全独立，可单独部署/扩缩容。
 */
@SpringBootApplication(scanBasePackages = "com.basicframework")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
