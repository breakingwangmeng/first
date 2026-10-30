# Tasks: todo-module

## 1. 数据库与实体

- [ ] 1.1 `sql/schema.sql` 追加 `todo` 表（下划线命名，字段与 spec 数据契约一一对应）
- [ ] 1.2 测试库 `schema-test.sql` 同步新增 `todo` 表
- [ ] 1.3 实现 `Todo` 实体与 `TodoMapper`（MyBatis-Plus BaseMapper）

## 2. 后端接口（TDD）

- [ ] 2.1 编写失败测试：创建/详情/更新/删除/分页的正常路径 + 异常路径（未登录 401、校验 400、404、JSON 错误 400）
- [ ] 2.2 实现 `TodoService`：CRUD + 分页（创建时间倒序）、字段校验（标题 1–100、描述 ≤2000、优先级白名单、日期格式）、默认值补齐
- [ ] 2.3 实现 `TodoController`：`POST /admin/todos`、`GET /admin/todos/{id}`、`PUT /admin/todos/{id}`、`DELETE /admin/todos/{id}`、`GET /admin/todos`（page/size 校验）
- [ ] 2.4 验证：admin-app 测试全绿

## 3. 前端页面

- [ ] 3.1 新增 `api/todo.ts`（Todo 类型与五个接口调用，字段驼峰）
- [ ] 3.2 新增 `views/TodoView.vue`：分页列表、新建/编辑弹窗、完成勾选（调更新接口）、删除确认
- [ ] 3.3 路由 `/todos` 与侧边栏菜单项接入
- [ ] 3.4 验证：`npm run build` 通过

## 4. 收尾

- [ ] 4.1 双端联调：登录后待办页完成列表加载、新建、编辑（含完成勾选）、删除全链路（真实 MySQL）
- [ ] 4.2 全量验证：`mvnw clean package` 全绿；README 补充 Todo 模块说明
- [ ] 4.3 自检 spec 全部 Requirement 有实现与测试证据

## 验收标准（可勾选）

- [ ] 后端自动化测试覆盖五个接口的正常路径与全部异常路径，且全绿
- [ ] `mvnw clean package` 全模块构建通过
- [ ] 前端 `npm run build`（含类型检查）通过
- [ ] 双端联调：登录后待办页完成列表加载、新建、编辑（含完成勾选）、删除全链路
- [ ] 契约字段为驼峰命名，前后端一致
- [ ] `sql/schema.sql` 含 `todo` 表定义，既有库可直接增量执行
