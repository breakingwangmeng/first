# Project Spec

> 项目级根 spec。所有 `specs/` 与 `changes/` 都建立在本文件描述的上下文之上。

## 项目名称

basic-framework —— 个人使用的全栈开发脚手架（Git 模板仓库形式复用）。

## 愿景

> （占位）用 2-3 句话描述：这个产品为谁解决什么问题，为什么值得做。

## 范围（in scope）

> （占位）列出本期要交付的能力模块。每个模块最终对应 `openspec/specs/<capability>/spec.md` 中的一个 capability。

## 非目标（out of scope）

> （占位）明确本期不做什么，防止范围蔓延。

## 关键术语

> （占位）维护一张术语表，确保人与 AI 对同一概念的理解一致。

| 术语 | 含义 |
|---|---|
| Capability | OpenSpec 体系中一个独立能力单元，对应 `openspec/specs/<capability>/spec.md` |
| Change | 一次变更提案，对应 `openspec/changes/<name>/`，含 proposal / design / tasks 三件套 |
| Brief | 人类可读的工程 brief，存于 `openspec/notes/<topic>/`，不进入 OpenSpec 工作流 |

## 技术栈

| 层 | 选型 | 决定性理由 |
|---|---|---|
| 后端 | Java 17 + Spring Boot 3.5.x | 本机 JDK 17；3.5 生态成熟、个人脚手架求稳 |
| 构建 | Maven 多模块（parent + common + admin-app + api-app）+ Wrapper | admin/api 边界清晰、可按需删模块；换机器零配置 |
| ORM | MyBatis-Plus 3.5.x | 通用 CRUD 快，管理后台主流 |
| 数据库 | MySQL 8（虚拟机自启，不用 docker-compose） | 已有环境，避免容器额外维护 |
| 鉴权 | Sa-Token 1.44.x（sa-token-spring-boot3-starter） | 轻量、注解式鉴权，个人开发省心 |
| 接口文档 | springdoc-openapi 2.8.x | 与 Spring Boot 3 原生适配 |
| 前端 | Vue 3 + Vite + Element Plus + vue-router + axios | 前后端分离、独立构建，与后端同仓库 |

禁止动作：

- 禁止硬编码数据库连接（只走环境变量）。
- 禁止新增未走 OpenSpec 流程的模块或依赖。
- 禁止跳过 TDD 直接写实现。

## OpenSpec Conventions

本模板对 OpenSpec 工作流的约定（与官方 schema 兼容，补充粒度规则）：

### Capability 粒度：每 change 一个 capability（精细粒度）

- 每个 change 对应**一个独立 capability**，命名与 change 名一致（kebab-case）。
- 理由：archive 时 `openspec/specs/<capability>/spec.md` 各归各位、零冲突，支持多个 change 并行 apply。
- 不建议把多个 change 写到同一个 capability。

### `openspec/notes/` 的角色

- `openspec/notes/<topic>/<name>.md` 存放**人类可读的工程 brief**——结构化但非 OpenSpec schema 格式。
- OpenSpec CLI 不会扫到 `notes/`，仅 `specs/` 与 `changes/` 进入工作流。
- brief 用途：propose 阶段作为上下文喂入；review 阶段作为团队对齐文档；不参与 archive 合入。

## 质量底线

- 所有代码改动走 TDD（见 `.qoder/skills/test-driven-development/`）。
- 主分支测试始终绿灯。
- 公开 API 改动必须先有对应 spec 更新。

## 维护

本文件每次 `/opsx:archive` 时检查是否需要更新。重大变化在对应 change 的 `design.md` 中写理由。
