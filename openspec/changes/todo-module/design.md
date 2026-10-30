# Design: todo-module

## Context

`basic-framework` 脚手架已就绪：admin-app（8080）具备 Sa-Token 登录鉴权、统一 `Result` 响应体、全局异常处理、MyBatis-Plus + MySQL 8、前端 Vue 3 布局与路由守卫。本 change 在 admin-app 内新增单用户私有的待办清单模块，作为首个业务模块范例，同时满足个人日常使用。

## Goals / Non-Goals

**Goals:**

- 提供 Todo 的创建、详情、更新、删除与分页列表（按创建时间倒序）
- 异常路径全覆盖（未登录 / 参数校验 / 404 / 请求体错误）
- 前端提供列表、新建、编辑、完成勾选、删除交互
- 字段命名前后端统一驼峰

**Non-Goals:**

- 多用户协作；状态筛选接口；完成状态单独切换端点
- 标签 / 分类 / 子任务 / 提醒 / 附件

## Decisions

1. **归属 admin-app 而非新建模块**：复用现有 Sa-Token 拦截、`Result`/`ErrorCode`、全局异常处理与前端布局；新增一个独立应用对"单用户私有待办"而言是过度设计。备选：api-app（面向开放服务，语义不符）、新建 todo-app（YAGNI 排除）。
2. **`todo` 表不加 `user_id`**：单用户私有，全表即当前用户数据；将来若要做协作，再加字段迁移。备选：预留 user_id（预防性抽象，违反 YAGNI）。
3. **完成状态通过 PUT 更新，不提供 PATCH /complete 端点**：减少接口面，前端"勾选"交互调用更新接口即可。备选：单独 toggle 端点（被 Non-goals 排除）。
4. **优先级存字符串枚举 `HIGH`/`MEDIUM`/`LOW`**（DB `VARCHAR(16)`）：无查询排序需求时比 TINYINT 可读性好；默认 `MEDIUM`。备选：int 编码（可读性差）。
5. **`dueDate` 用 `LocalDate`，JSON 序列化为 `yyyy-MM-dd`**（Jackson 默认行为）；`createdAt`/`updatedAt` 为 `LocalDateTime`（ISO-8601）。前后端契约统一字符串，避免时区歧义。
6. **分页复用 `PageResult<T>`**（`list`/`total`/`page`/`size`），`page` 最小 1、`size` 1–100（默认 10），超限返回 40000。备选：返回裸数组（与现有用户管理不一致，排除）。
7. **DB 层下划线命名 + MyBatis-Plus `map-underscore-to-camel-case`**：契约字段（API/前端）统一驼峰，表字段沿用项目现有下划线风格。
8. **异常复用现有错误码**：未登录 40100、无权限 40300、参数错误 40000、资源不存在 40400；不新增错误码。备选：新增 TODO_* 专属错误码（无必要）。

## Risks / Trade-offs

- [单用户假设将来要协作] → 加 `user_id` 的列迁移简单，当前不预埋
- [优先级字符串可能有脏值] → Service 层白名单校验，非法值返回 40000
- [dueDate 可空且格式易错] → 全局 Jackson 反序列化失败走 40000，前端用日期选择器约束

## Migration Plan

- `sql/schema.sql` 追加 `todo` 表（`CREATE TABLE IF NOT EXISTS`），既有库执行一次即可
- 测试库沿用 H2 + `schema-test.sql`/`data-test.sql` 同步新增
- 无数据迁移、无回滚需求（纯新增）

## Open Questions

（无）
