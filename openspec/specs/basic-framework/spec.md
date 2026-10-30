# basic-framework Specification

## Purpose
个人使用的全栈开发脚手架（种子工程）：Spring Boot 3 多模块后端（parent + common + admin-app + api-app）+ Vue 3 前端，含统一响应体与全局异常处理、Sa-Token 登录鉴权、最小 RBAC、用户管理、api 开放服务骨架、springdoc 接口文档与前端种子页，可 clone 后按 README 复用。
## Requirements
### Requirement: Maven 多模块工程骨架

工程 SHALL 采用 Maven 多模块结构：`parent`（统一依赖与插件版本）+ `common` + `admin-app` + `api-app`，并提交 Maven Wrapper（`mvnw`），使未安装 Maven 的机器可直接构建。

#### Scenario: 从零构建成功

- **WHEN** 在未预装 Maven 的机器上克隆仓库并执行 `./mvnw clean package`（Windows 为 `mvnw.cmd`）
- **THEN** 全部模块编译打包成功，产出 `common` jar 与两个可执行应用 jar

#### Scenario: 模块边界

- **WHEN** 查看模块依赖关系
- **THEN** `admin-app` 与 `api-app` 依赖 `common`，二者之间互不依赖，`common` 不依赖任何应用模块

### Requirement: 统一响应体与全局异常处理

所有 REST 接口 SHALL 返回统一响应体 `Result<T>`（字段 `code` / `message` / `data`）；`common` 模块 SHALL 提供全局异常处理器与业务错误码枚举，业务异常、参数校验异常均转换为 `Result` 结构，且 HTTP 状态码与业务码约定一致。

#### Scenario: 业务异常转换

- **WHEN** 接口抛出携带错误码的业务异常
- **THEN** 响应体为该错误码对应的 `Result` 结构，`message` 可读

#### Scenario: 参数校验失败

- **WHEN** 请求参数违反 jakarta validation 约束
- **THEN** 响应体为校验错误码的 `Result` 结构，`message` 指明具体字段

### Requirement: admin-app 登录鉴权

admin-app SHALL 提供基于 Sa-Token 的账号密码登录接口，登录成功返回 token；受保护接口在无 token 或 token 失效时 MUST 拒绝访问并返回明确错误码。

#### Scenario: 登录成功

- **WHEN** 用户以正确账号密码调用登录接口
- **THEN** 返回成功 `Result` 且携带有效 token

#### Scenario: 未登录访问

- **WHEN** 无 token 请求受保护接口
- **THEN** 返回未登录错误码，不执行业务逻辑

### Requirement: admin-app 最小 RBAC

admin-app SHALL 提供用户、角色、菜单三个实体及关联关系，并基于 Sa-Token 注解按角色拦截接口；种子数据 SHALL 包含一个管理员角色与一个管理员账号，保证首次启动即可登录。

#### Scenario: 管理员访问

- **WHEN** 管理员 token 请求要求管理员角色的接口
- **THEN** 请求成功

#### Scenario: 无权限角色访问

- **WHEN** 非管理员角色 token 请求要求管理员角色的接口
- **THEN** 返回无权限错误码

### Requirement: admin-app 用户管理接口

admin-app SHALL 提供用户的增删改查与分页列表接口，通过 MyBatis-Plus 操作 MySQL 8。

#### Scenario: 分页查询用户

- **WHEN** 管理员请求用户分页列表接口
- **THEN** 返回用户分页数据与总数

### Requirement: api-app 开放服务骨架

api-app SHALL 独立于 admin-app 启动（端口 8081），提供健康检查接口与至少一个演示 Sa-Token 鉴权的示例接口。

#### Scenario: 健康检查

- **WHEN** 请求 api-app 健康检查接口
- **THEN** 返回服务正常状态

#### Scenario: 鉴权示例

- **WHEN** 以有效 token 请求示例接口
- **THEN** 返回成功；以无效或缺失 token 请求则被拒绝

### Requirement: 接口文档

admin-app 与 api-app SHALL 集成 springdoc 并提供可访问的 OpenAPI 文档地址。

#### Scenario: 查看文档

- **WHEN** 应用启动后访问文档路径
- **THEN** 展示该应用全部接口的 OpenAPI 文档

### Requirement: 前端种子工程

仓库 SHALL 包含 Vue 3 + Vite + Element Plus 前端工程（`frontend/`），提供登录页、基础布局与一个调用后端接口的示例页面；dev 期 SHALL 通过 Vite proxy 转发到 admin-app/api-app，避免跨域。

#### Scenario: 登录页联调

- **WHEN** 前端 dev 服务启动并访问登录页，输入种子管理员账号
- **THEN** 登录成功并进入基础布局页面，示例页面可加载后端数据

### Requirement: 配置与文档

仓库 SHALL 提供 `.env.example`（数据库连接等环境变量模板）与 README（模块图、双端启动步骤、新项目复用时的重命名清单）；数据库连接信息 MUST 从环境变量读取，不得硬编码。

#### Scenario: 换机器起步

- **WHEN** 开发者按 README 配置环境变量后启动后端与前端
- **THEN** 无需修改代码即可运行并完成登录联调

