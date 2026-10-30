# Tasks: basic-framework

## 1. 工程骨架

- [x] 1.1 创建 `backend/pom.xml`（parent，pom 打包）：Spring Boot 3.5.x 依赖管理、MyBatis-Plus、Sa-Token、springdoc、MySQL 驱动版本统一管理
- [x] 1.2 创建 `backend/common/`、`backend/admin-app/`、`backend/api-app/` 三个子模块目录与 pom，接入 parent
- [x] 1.3 在 `backend/` 提交 Maven Wrapper（`mvnw` / `mvnw.cmd` / `.mvn/`）
- [x] 1.4 创建根目录 `.env.example`（数据库地址/端口/账号/密码模板）与 `.gitignore` 规则
- [x] 1.5 验证：`./mvnw clean package` 全模块构建通过

## 2. common 模块

- [x] 2.1 编写失败测试：`Result<T>` 构造与错误码枚举 `ErrorCode` 的行为约定（`backend/common/src/test/`）
- [x] 2.2 实现 `Result<T>`（`code`/`message`/`data`）与业务错误码枚举（`backend/common/src/main/java/com/basicframework/common/`）
- [x] 2.3 编写失败测试：全局异常处理器对业务异常、参数校验异常、兜底异常的转换
- [x] 2.4 实现 `@RestControllerAdvice` 全局异常处理器与业务异常类（携带错误码）
- [x] 2.5 补充通用工具类（时间、字符串），保持最小集

## 3. admin-app 基础

- [x] 3.1 创建 admin-app 启动类与 `application.yml`（端口 8080、环境变量读取数据库连接、MyBatis-Plus 配置）
- [x] 3.2 集成 springdoc 与 Sa-Token（`sa-token-spring-boot3-starter`），依赖冒烟测试通过
- [x] 3.3 开启 CORS 配置，允许前端 dev 来源
- [x] 3.4 编写失败测试：健康检查/文档端点可达
- [x] 3.5 验证：admin-app 启动成功，文档地址可访问（H2 测试上下文验证）

## 4. admin-app 登录与最小 RBAC

- [x] 4.1 编写失败测试：登录接口成功/失败、未登录访问被拒、角色拦截（`backend/admin-app/src/test/`）
- [x] 4.2 创建用户/角色/菜单实体与建表 SQL 种子数据（管理员账号 + 管理员角色）
- [x] 4.3 实现 Sa-Token 登录接口（账号密码校验、返回 token）
- [x] 4.4 实现基于注解的角色权限拦截与全局未登录/无权限错误码
- [x] 4.5 实现用户管理接口（分页列表 + 增删改查，MyBatis-Plus + MySQL 8）
- [x] 4.6 验证：测试全绿，手工走通"登录 → 分页查用户 → 无权限角色被拒"（自动化测试覆盖同链路）

## 5. api-app 骨架

- [x] 5.1 创建 api-app 启动类与 `application.yml`（端口 8081、环境变量数据库连接）
- [x] 5.2 编写失败测试：健康检查与鉴权示例接口
- [x] 5.3 实现健康检查接口与带 Sa-Token 鉴权的示例接口，集成 springdoc
- [x] 5.4 验证：api-app 独立启动，文档可访问，测试全绿（测试上下文验证）

## 6. 前端种子工程

- [x] 6.1 用 Vite 创建 `frontend/`（Vue 3 + TypeScript），接入 Element Plus 与路由
- [x] 6.2 配置 Vite dev proxy：`/admin` → 8080、`/api` → 8081
- [x] 6.3 实现登录页（调用 admin 登录接口，存储 token）
- [x] 6.4 实现基础布局（侧边栏 + 顶栏 + 路由出口）与路由守卫（无 token 跳登录页）
- [x] 6.5 实现示例页面（带 token 调用用户分页接口展示数据）
- [x] 6.6 验证：双端联调走通（登录 → 布局 → 示例页数据）

## 7. 收尾

- [x] 7.1 编写根目录 README：模块图、环境变量配置说明、双端启动命令、新项目复用重命名清单
- [x] 7.2 全量验证：`./mvnw clean package` 全绿；双端启动按 README 步骤零代码修改可跑通
- [x] 7.3 自检 spec 九条 Requirement 均有对应实现与测试证据，更新 `openspec/project.md` 技术栈占位
