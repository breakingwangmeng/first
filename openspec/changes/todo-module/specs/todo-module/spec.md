# Spec: todo-module

单用户私有的待办清单模块：admin-app 内的 CRUD + 分页列表 + 前端页面。字段命名前后端统一驼峰；异常路径全覆盖。

## ADDED Requirements

### Requirement: 模块边界与范围

Todo 模块 SHALL 仅包含：待办的创建、详情、更新、删除、分页列表（按创建时间倒序）五个接口，以及对应的前端页面（列表 / 新建 / 编辑 / 完成勾选 / 删除）。模块 MUST NOT 包含多用户协作或共享、按状态筛选接口、完成状态单独切换端点、标签 / 分类 / 子任务 / 提醒 / 附件、数据权限。

#### Scenario: 范围约束

- **WHEN** 开发者实现 Todo 模块
- **THEN** 仅实现上述五个接口与对应前端交互
- **AND** 不新增协作、筛选、单独切换完成状态等超出范围的接口
- **AND** 完成状态仅能通过更新接口修改

### Requirement: 创建待办

登录用户 SHALL 能通过 `POST /admin/todos` 创建待办；标题必填，其余字段可选，服务端 SHALL 补齐默认值（`completed=false`、`priority=MEDIUM`）并生成 `id` 与时间戳。

#### Scenario: 创建成功

- **WHEN** 已登录用户提交仅含合法 `title` 的请求体
- **THEN** 返回 `code=0`，`data` 为完整 Todo 实体（含新生成的 `id`）
- **AND** `completed=false`、`priority=MEDIUM`、`dueDate`/`description` 为 null、`createdAt` 与 `updatedAt` 为服务端时间

#### Scenario: 标题缺失或超长

- **WHEN** 提交的 `title` 缺失、为空白字符、或去首尾空白后超过 100 字符
- **THEN** 返回 HTTP 400 与业务码 40000
- **AND** `message` 指明 `title` 字段问题，不创建任何数据

#### Scenario: 可选字段非法

- **WHEN** `description` 超过 2000 字符，或 `priority` 不是 `HIGH`/`MEDIUM`/`LOW` 之一，或 `dueDate` 不是 `yyyy-MM-dd` 格式
- **THEN** 返回 HTTP 400 与业务码 40000
- **AND** `message` 指明具体非法字段，不创建任何数据

#### Scenario: 未登录创建

- **WHEN** 无有效 token 请求 `POST /admin/todos`
- **THEN** 返回 HTTP 401 与业务码 40100，不执行业务逻辑

### Requirement: 查询待办详情

登录用户 SHALL 能通过 `GET /admin/todos/{id}` 查询单条待办。

#### Scenario: 查询成功

- **WHEN** 已登录用户请求存在的 `id`
- **THEN** 返回 `code=0` 与该 Todo 实体

#### Scenario: 资源不存在

- **WHEN** 已登录用户请求不存在的 `id`
- **THEN** 返回 HTTP 404 与业务码 40400
- **AND** `message` 包含该 `id` 便于定位

#### Scenario: 未登录查询

- **WHEN** 无有效 token 请求 `GET /admin/todos/{id}`
- **THEN** 返回 HTTP 401 与业务码 40100

### Requirement: 更新待办

登录用户 SHALL 能通过 `PUT /admin/todos/{id}` 全量更新待办；`title`/`completed`/`priority` 必填，`description`/`dueDate` 可空。

#### Scenario: 更新成功

- **WHEN** 已登录用户对存在的 `id` 提交合法全量字段
- **THEN** 返回 `code=0` 与更新后的完整实体
- **AND** `updatedAt` 刷新为本次更新时间，未提交的字段不被保留（全量语义）

#### Scenario: 更新不存在的资源

- **WHEN** 对不存在的 `id` 提交更新
- **THEN** 返回 HTTP 404 与业务码 40400，不产生数据

#### Scenario: 更新字段非法

- **WHEN** `title` 空白或超 100 字符，或 `completed` 缺失，或 `priority` 非法，或 `dueDate` 格式错误
- **THEN** 返回 HTTP 400 与业务码 40000，`message` 指明字段，数据不变

#### Scenario: 未登录更新

- **WHEN** 无有效 token 请求 `PUT /admin/todos/{id}`
- **THEN** 返回 HTTP 401 与业务码 40100

### Requirement: 删除待办

登录用户 SHALL 能通过 `DELETE /admin/todos/{id}` 删除待办。

#### Scenario: 删除成功

- **WHEN** 已登录用户删除存在的 `id`
- **THEN** 返回 `code=0`，该记录被物理删除
- **AND** 再次查询该 `id` 返回 HTTP 404 与业务码 40400

#### Scenario: 删除不存在的资源

- **WHEN** 删除不存在的 `id`
- **THEN** 返回 HTTP 404 与业务码 40400

#### Scenario: 未登录删除

- **WHEN** 无有效 token 请求 `DELETE /admin/todos/{id}`
- **THEN** 返回 HTTP 401 与业务码 40100

### Requirement: 分页查询待办列表

登录用户 SHALL 能通过 `GET /admin/todos?page=1&size=10` 分页查询待办，按创建时间倒序。

#### Scenario: 分页成功

- **WHEN** 已登录用户请求合法的 `page` 与 `size`
- **THEN** 返回 `code=0`，`data` 为 `{list, total, page, size}`，`total` 为全部记录数
- **AND** `list` 按 `createdAt` 倒序排列，元素为完整 Todo 实体

#### Scenario: 分页参数非法

- **WHEN** `page` 小于 1，或 `size` 小于 1 或大于 100
- **THEN** 返回 HTTP 400 与业务码 40000，`message` 指明参数

#### Scenario: 未登录分页

- **WHEN** 无有效 token 请求 `GET /admin/todos`
- **THEN** 返回 HTTP 401 与业务码 40100

### Requirement: 鉴权与异常约定

Todo 接口 SHALL 仅允许登录用户访问（沿用 admin-app 全局 Sa-Token 拦截，无需角色限制）；HTTP 状态码 MUST 与业务码约定一致；请求体 JSON 格式错误 SHALL 返回 400/40000。

#### Scenario: 请求体 JSON 格式错误

- **WHEN** 请求体不是合法 JSON
- **THEN** 返回 HTTP 400 与业务码 40000

### Requirement: 数据契约

接口契约字段 SHALL 全部使用驼峰命名，前后端一致；下表定义 Todo 实体与请求 / 响应结构：

| 字段 | 类型 | 约束 | 读写 |
|---|---|---|---|
| `id` | number (int64) | 服务端生成，自增 | 只读 |
| `title` | string | 必填，去首尾空白后 1–100 字符 | 创建/更新必填 |
| `description` | string | 可空，≤2000 字符 | 读写 |
| `completed` | boolean | 默认 false | 读写（更新必填） |
| `priority` | string | 枚举 `HIGH`/`MEDIUM`/`LOW`，默认 `MEDIUM` | 读写（更新必填） |
| `dueDate` | string | 可空，格式 `yyyy-MM-dd` | 读写 |
| `createdAt` | string | ISO-8601 日期时间，服务端生成 | 只读 |
| `updatedAt` | string | ISO-8601 日期时间，服务端维护 | 只读 |

- 创建请求体：`{title, description?, priority?, dueDate?}`
- 更新请求体：`{title, description?, completed, priority, dueDate?}`（全量更新）
- 分页响应：`{list: Todo[], total: number, page: number, size: number}`
- 所有响应包在统一 `Result<T>`（`code`/`message`/`data`）中

#### Scenario: 命名一致性

- **WHEN** 前后端交换 Todo 数据
- **THEN** 所有字段名使用驼峰（`completed`/`priority`/`dueDate`/`createdAt`/`updatedAt`）
- **AND** 不允许出现 `completed_status`、`due_date` 等下划线契约字段

### Requirement: 前端待办页面

前端 SHALL 提供登录后可访问的待办页：列表展示（含完成勾选、优先级与截止日期展示）、新建、编辑、删除；无 token 访问 SHALL 被路由守卫重定向登录页；接口失败 SHALL 弹出错误提示且不冒泡未处理异常。

#### Scenario: 待办页操作链路

- **WHEN** 登录用户进入待办页
- **THEN** 列表加载分页数据，支持新建、编辑、删除
- **AND** 勾选完成状态时调用更新接口保存

#### Scenario: 待办页异常处理

- **WHEN** 接口返回非 `code=0`（如 40000/40400）或未登录
- **THEN** 弹出对应错误提示，页面不崩溃、控制台无未处理异常

### Requirement: 验收标准

以下验收项 SHALL 全部满足（与 tasks.md 勾选清单一致）：

- [ ] 后端自动化测试覆盖五个接口的正常路径与全部异常路径（未登录 401、校验 400、404、JSON 错误 400），且全绿
- [ ] `mvnw clean package` 全模块构建通过
- [ ] 前端 `npm run build`（含类型检查）通过
- [ ] 双端联调：登录后待办页完成列表加载、新建、编辑（含完成勾选）、删除全链路
- [ ] 契约字段为驼峰命名，前后端一致
- [ ] `sql/schema.sql` 含 `todo` 表定义，既有库可直接增量执行
