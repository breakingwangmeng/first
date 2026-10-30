# Design: basic-framework

## Context

个人使用的全栈种子工程：clone 后即可起步，覆盖"管理后台 + 开放 API"两类业务。当前仓库为空模板，无既有代码约束。动机与范围见 `proposal.md`。

## Goals / Non-Goals

**Goals:**

- Maven 多模块骨架：`parent` + `common` + `admin-app` + `api-app`，模块边界清晰、可增删
- 管理端最小 RBAC 可跑通：登录 → 按角色拦接口
- 开放 API 服务骨架：健康检查 + 鉴权示例
- 前端种子：登录页 + 布局 + 示例页，与后端同仓库、独立构建
- 换机器零配置：`mvnw` + `.env.example` + README

**Non-Goals:**

- 不做代码生成器、部门/数据权限、docker-compose、core 业务模块（详见 proposal）

## Decisions

| 决策 | 选择 | 理由 | 拒绝的替代 |
|---|---|---|---|
| 模块形状 | 多模块双应用 | admin 与 api 的部署/鉴权/限流策略天然分离；种子工程可按需删模块 | 单应用双面孔：起步最快，但两类业务边界糊在一起 |
| JDK / Spring Boot | JDK 17 + Spring Boot 3.5.x | 用户机器已装 JDK 17；3.5 对 17 支持完善、生态成熟、个人用求稳 | 4.0.x（2025 年底刚发，生态兼容未稳）；2.7.x（已 EOL） |
| 鉴权 | Sa-Token | 轻量、注解式鉴权、中文文档好，个人脚手架求省心 | Spring Security + JWT：配置重、概念多，收益不匹配 |
| ORM | MyBatis-Plus | 通用 CRUD 快、中文生态多、管理后台主流选择 | Spring Data JPA：自动建表风格与 MySQL 管理类项目习惯不符 |
| 前端工程 | 同仓库 `frontend/`，独立构建 | 一个 clone 拿全部；构建互不耦合，前后端分离度高 | frontend-maven-plugin 并入 Maven：构建耦合，分离不彻底 |
| 依赖治理 | parent pom 统一版本 + 各模块最小依赖 | 版本漂移最小化 | 各模块自管版本：将来升级是一场噩梦 |
| 共享业务层 | 不设 core 模块 | 种子阶段无共享业务逻辑，YAGNI | 预设 core：预防性抽象 |
| 复用方式 | Git 模板仓库 | 个人用最省事 | Maven archetype：维护成本高，收益低 |

### 目标结构

```
backend/
├── pom.xml                 # parent：依赖与插件版本管理
├── mvnw
├── common/                 # Result<T>、全局异常、错误码枚举、校验、工具
├── admin-app/              # 8080：登录、用户/角色/菜单、springdoc
└── api-app/                # 8081：健康检查 + 鉴权示例接口
frontend/                   # Vue 3 + Vite + Element Plus
.env.example                # 数据库连接等环境变量
README.md
```

### 关键约定

- 统一响应体 `Result<T>`：`code` / `message` / `data`；业务错误码枚举放 `common`
- 全局异常处理器在 `common`，`@RestControllerAdvice`，各应用自动受益
- Sa-Token 走 `sa-token-spring-boot3-starter`，admin 用账号密码登录 + token；api-app 示例接口演示 token 鉴权
- 数据库：MySQL 8 由虚拟机自启；连接串走环境变量，`.env.example` 提供模板
- 前端 dev 期经 Vite proxy 转发到 8080/8081，避免 CORS 心智负担；后端同时开 CORS 以兼容独立部署
- 包名统一 `com.basicframework.*`；复用为新项目时按 README 步骤重命名

## Risks / Trade-offs

- [MySQL 虚拟机 IP/端口变动导致连不上] → 连接串外置为环境变量，README 说明配置位置
- [Sa-Token 与 Spring Boot 3.5 兼容] → 采用最新 `sa-token-spring-boot3-starter`，首个任务先做依赖冒烟
- [多模块构建心智负担] → parent 统一版本，各模块保持最小依赖，README 画模块图
- [模板复用时 artifactId/包名替换易漏] → 命名集中约定 + README 提供替换清单
- [前端后端各自跑，开发体验割裂] → README 提供双端启动命令；本期不做一键脚本（YAGNI）

## Migration Plan

无既有系统，纯新增。落地即交付；回滚 = 删除本 change 的文件。

## Open Questions

- 无。范围与技术选型已在探索阶段与用户对齐并签字。
