package com.basicframework.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 管理后台应用（端口 8080）：登录、最小 RBAC、用户管理。
 * scanBasePackages 覆盖 common 模块，使统一响应与全局异常等组件生效。
 */
@SpringBootApplication(scanBasePackages = "com.basicframework")
@MapperScan("com.basicframework.admin.mapper")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
