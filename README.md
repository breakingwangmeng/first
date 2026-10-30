# basic-framework

个人使用的全栈开发脚手架（种子工程）：**Spring Boot 多模块 + Vue 3 前端**，clone 后即可起步。

## 模块结构

```
basic-framework/
├── backend/                     Maven 多模块工程
│   ├── pom.xml                  parent：依赖与插件版本统一管理
│   ├── mvnw / mvnw.cmd          Maven Wrapper（无需本地安装 Maven）
│   ├── common/                  统一响应 Result、错误码、全局异常、校验、工具
│   ├── admin-app/   (8080)      管理后台：Sa-Token 登录、最小 RBAC、用户管理
│   └── api-app/     (8081)      开放服务骨架：健康检查 + 鉴权示例
├── frontend/                    Vue 3 + Vite + Element Plus（独立构建）
├── sql/                         MySQL 8 建表与种子数据
└── .env.example                 环境变量模板
```

模块依赖：`admin-app`、`api-app` 依赖 `common`，两个应用之间互不依赖；按项目需要可整块删除 admin-app 或 api-app。

## 技术栈

| 层 | 选型 |
|---|---|
| 后端 | Java 17 + Spring Boot 3.5.x |
| 构建 | Maven 多模块 + Maven Wrapper |
| ORM | MyBatis-Plus 3.5.x |
| 数据库 | MySQL 8（自行在虚拟机中启动） |
| 鉴权 | Sa-Token 1.44.x |
| 接口文档 | springdoc-openapi 2.8.x |
| 前端 | Vue 3 + Vite + Element Plus + vue-router + axios |

## 环境准备

- JDK 17
- Node.js 20+（含 npm）
- MySQL 8（实例地址自备，如虚拟机）
- 无需本地安装 Maven（使用 `backend/mvnw`）

## 快速开始

### 1. 初始化数据库

```bash
mysql -h <DB_HOST> -u <DB_USERNAME> -p < sql/schema.sql
mysql -h <DB_HOST> -u <DB_USERNAME> -p < sql/data.sql
```

### 2. 配置环境变量

参照 `.env.example` 把变量注入运行环境（Spring Boot 不直接读取 `.env` 文件）。PowerShell 示例：

```powershell
$env:DB_HOST="192.168.1.10"; $env:DB_PORT="3306"; $env:DB_NAME="basic_framework"
$env:DB_USERNAME="root"; $env:DB_PASSWORD="your_password"
```

也可在 IDE 的运行配置中设置同样的环境变量。

### 3. 启动后端

```powershell
cd backend
# 首次：把 common 安装到本地仓库（后续 spring-boot:run 需要）
.\mvnw.cmd install -DskipTests

# 管理后台（8080）
.\mvnw.cmd -pl admin-app spring-boot:run

# 开放服务（8081，不依赖数据库，可单独启动）
.\mvnw.cmd -pl api-app spring-boot:run
```

也可以打包后运行：

```powershell
.\mvnw.cmd -pl admin-app -am clean package -DskipTests
java -jar admin-app/target/admin-app-0.0.1-SNAPSHOT.jar
```

### 4. 启动前端

```powershell
cd frontend
npm install
npm run dev
```

打开 http://localhost:5173 。dev 期 `/admin` 与 `/api` 经 Vite 代理转发到 8080/8081。

**种子账号**（密码均为 `123456`）：

| 账号 | 角色 | 说明 |
|---|---|---|
| admin | 管理员 | 可访问用户管理接口 |
| demo | 普通用户 | 访问用户管理接口返回 403 |

## 接口入口

| 应用 | 地址 | 说明 |
|---|---|---|
| admin-app | http://localhost:8080 | 文档 `/swagger-ui.html`，健康 `/actuator/health` |
| api-app | http://localhost:8081 | 文档 `/swagger-ui.html`，健康 `/actuator/health` |

鉴权约定：请求头 `satoken: <token>`；业务错误统一返回 `Result{code,message,data}`（`code=0` 成功）。

- `POST /admin/auth/login` 登录（body：`{"username":"admin","password":"123456"}`）
- `GET /admin/users?page=1&size=10` 用户分页（需 admin 角色）
- `POST /api/auth/demo-login` 获取演示 token；`GET /api/example` 鉴权示例

## 测试

```powershell
cd backend
.\mvnw.cmd clean test
```

- `common`：Result/错误码/全局异常/工具单测
- `admin-app`：冒烟（健康检查、文档）+ 登录/RBAC/用户管理链路（H2 内存库）
- `api-app`：健康检查 + 鉴权示例链路

前端构建校验：`cd frontend; npm run build`。

## 作为模板复用到新项目

1. 复制仓库后删除 `.git` 并重新初始化（或 `git clone` 到新目录）。
2. 全局替换：
   - 顶层坐标：`basic-framework`（`backend/pom.xml` 的 artifactId、README 标题）
   - 包名：`com.basicframework` → 你的包名
   - 数据库：`basic_framework`（`sql/*.sql` 与 `.env.example`）
3. 按需删除 `admin-app` 或 `api-app` 模块，并同步调整 `backend/pom.xml` 的 `<modules>`。
4. 修改种子账号与 `sql/data.sql`（生产环境务必更换密码）。

## 约定

- 所有接口返回 `Result<T>`；分页用 `PageResult<T>`；错误码集中维护在 `ErrorCode`。
- 业务异常使用 `BusinessException(ErrorCode...)`，由 `GlobalExceptionHandler` 统一转 `Result`。
- 数据库连接只从环境变量读取，禁止硬编码。
- 代码改动走 TDD：先写失败测试，再写最小实现（见 `.qoder/skills/test-driven-development/`）。
