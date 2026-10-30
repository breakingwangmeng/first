# Proposal: basic-framework

## Why

每次新开项目都要重复搭建同一套东西：Spring Boot 多模块结构、统一响应与异常处理、登录鉴权、前端工程骨架……既耗时又容易前后不一致。需要一个个人用的全栈种子工程，clone 下来即可起步，把"搭架子"的时间省给业务。

## What Changes

- 新建 Maven 多模块后端工程：`parent` + `common` + `admin-app` + `api-app`
- `common`：统一响应体 `Result<T>`、全局异常处理与业务错误码、参数校验（jakarta validation）、通用工具类
- `admin-app`（端口 8080）：Sa-Token 登录鉴权、用户/角色/菜单最小 RBAC、MyBatis-Plus + MySQL 8、springdoc 接口文档
- `api-app`（端口 8081）：健康检查 + 一个带鉴权的示例接口（开放服务骨架）
- `frontend`：Vue 3 + Vite + Element Plus，登录页 + 基础布局 + 示例页面，与后端同仓库、独立构建
- 基础设施：Maven Wrapper（`mvnw`）、README、`.env.example`

## Non-goals

- 不做代码生成器（后续独立 change）
- 不做部门/组织架构、不做数据权限
- 不提供 docker-compose（MySQL 8 由虚拟机自行启动）
- 不发布 Maven archetype，不做开源分发
- 不引入 core 业务模块（当前无共享业务逻辑，YAGNI）

## Capabilities

### New Capabilities

- `basic-framework`: 全栈开发脚手架（种子工程），覆盖后端多模块骨架、最小 RBAC 管理端、开放 API 服务骨架、前端种子工程

### Modified Capabilities

（无——仓库当前无既有 spec）

## Impact

- 全部为新增代码，不修改既有系统
- 新增依赖：Spring Boot 3.5.x、MyBatis-Plus、Sa-Token、springdoc-openapi、MySQL 驱动；前端 Vue 3 / Vite / Element Plus
- 归档时回填 `openspec/project.md` 的技术栈占位内容

## 决策点（Human Decision Point）

请确认以下定案，签字后进入 design 与 tasks：

1. 形状：Maven 多模块双应用（common / admin-app / api-app）
2. 技术栈：JDK 17 + Spring Boot 3.5.x + MyBatis-Plus + MySQL 8（虚拟机自启）+ Sa-Token；前端 Vue 3 + Vite + Element Plus
3. 范围：admin-app 最小 RBAC；api-app 纯骨架；frontend 登录页 + 布局 + 示例页；本期无代码生成器
