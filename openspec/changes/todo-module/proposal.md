# Proposal: todo-module

## Why

脚手架已有登录鉴权、统一响应体与前端布局，但缺少一个"表 → 接口 → 页面"完整链路的业务模块范例。Todo 待办清单简单清晰、单用户私有，适合作为第一个业务模块落地，同时也是日常个人开发可用的真实功能。

## What Changes

- admin-app 新增 Todo 领域：`todo` 表、实体 / Mapper / Service / Controller
- 新增接口：创建、详情、更新、删除、分页列表（按创建时间倒序），全部走统一 `Result` 响应体与 Sa-Token 登录鉴权
- 异常路径全覆盖：未登录、参数校验失败、资源不存在、请求体格式错误
- 前端新增待办页（列表 / 新建 / 编辑 / 完成勾选 / 删除）与侧边栏菜单入口
- 字段命名前后端统一驼峰（`completed` / `priority` / `dueDate` / `createdAt` / `updatedAt`）

## Capabilities

### New Capabilities

- `todo-module`: 单用户私有的待办清单模块——后端接口契约（含异常约定）与前端页面约定

### Modified Capabilities

（无：不修改 basic-framework 已有 Requirement，仅在其 admin-app 内新增模块）

## Impact

- `backend/admin-app`：新增 `todo/` 包（entity / mapper / service / controller）与测试
- `sql/schema.sql`：追加 `todo` 表
- `frontend/src`：新增 `views/TodoView.vue`、`api/todo.ts`，路由与侧边栏菜单项
- 无新增第三方依赖

## Non-goals

- 不做多用户协作 / 共享（单用户私有）
- 不做按状态筛选接口、不做完成状态单独切换端点（经更新接口修改）
- 不做标签、分类、子任务、提醒、附件
- 不做数据权限 / 部门隔离

---

**Human decision point**: 以上范围（归属 admin-app、CRUD + 分页、标准字段集、含前端页面、单用户私有）需用户签字确认后，再产出 design 与 specs。
