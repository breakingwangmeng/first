# AGENTS.md

本仓库是 **my-first-project** —— 一个由 AI coding agent 驱动、Spec-Driven 的 **AIWorkSpace**（工作空间）：主仓库只保留 AI 协作资产（规则 / 技能 / spec 活文档），业务代码以 git submodule 形式存放在 `backend/` 与 `frontend/` 子仓库。任何 agent（Qoder / Claude Code / Codex / Cursor 等）进入仓库时，**先读这一份**。

## 三层架构

本模板融合三大理念，形成"流程约束 + 技能方法论 + 规格活文档"的完整开发体系：

| 层 | 目录 | 职责 | 灵感来源 |
|---|---|---|---|
| Harness | `.qoder/` | 当前 IDE 的 agent 配置（rules / skills / commands / agents），约束 agent 生命周期与行为 | Qoder 原生配置 |
| OpenSpec | `openspec/` | Spec-driven 工作流：先对齐 spec，再写代码 | https://github.com/Fission-AI/OpenSpec |
| Superpowers | `.qoder/skills/` | 可组合的开发技能库，自动触发，强制流程 | https://github.com/obra/superpowers |

## 仓库结构（submodule）

业务代码拆为两个 git submodule 子仓库，主仓库只记录 submodule 指针（commit）：

| 路径 | 子仓库 | 内容 |
|---|---|---|
| `backend/` | git submodule（相对路径 `../backend`） | Spring Boot 3 多模块后端（parent + common + admin-app + api-app），独立构建与测试 |
| `frontend/` | git submodule（相对路径 `../frontend`） | Vue 3 + Vite + Element Plus 前端，独立构建 |

**克隆后初始化**：

```bash
git submodule update --init --recursive
```

**在子仓库提交**：进入 `backend/` 或 `frontend/` 目录后按各自仓库的 git 流程提交（子仓库有独立历史与分支）；改动子仓库后，回到主仓库提交 submodule 指针更新。

## 硬规则（不可违反）

1. **任何代码改动前必须先走 OpenSpec 流程**：在 `openspec/changes/<change-name>/` 下产出 `proposal.md` → `design.md` → `tasks.md`，人类签字后才动代码。
2. **TDD 不可绕过**：实现阶段严格 RED → GREEN → REFACTOR，先写失败测试再写实现。
3. **YAGNI / DRY**：不做没要求的事，不搞预防性抽象。
4. **变更完成后归档**：把 `openspec/changes/<name>/` 移入 `openspec/changes/archive/<date>-<name>/`，spec 增量合入 `openspec/specs/`。
5. **任务前检查技能**：任何任务开始前，先检查 `.qoder/skills/` 下是否有相关技能，有则必须遵循（见 `using-superpowers`）。

## 开发流程

```
brainstorming  →  propose  →  design  →  tasks  →  TDD 实现  →  code review  →  archive
```

各阶段触发技能、产出物与人类把关点详见 [`.qoder/rules/spec-driven-workflow.md`](.qoder/rules/spec-driven-workflow.md)。

## 快速入口

- 工作流规则：[`.qoder/rules/spec-driven-workflow.md`](.qoder/rules/spec-driven-workflow.md)
- 编码规约：[`.qoder/rules/coding-conventions.md`](.qoder/rules/coding-conventions.md)
- Git 规范：[`.qoder/rules/git-workflow.md`](.qoder/rules/git-workflow.md)
- 子代理定义：[`.qoder/agents/README.md`](.qoder/agents/README.md)
- OpenSpec 命令：`.qoder/commands/opsx/{propose,apply,archive,explore}.md`
- OpenSpec 技能：`.qoder/skills/openspec-{propose,apply-change,archive-change,explore}/SKILL.md`
- Superpowers 技能：`.qoder/skills/{using-superpowers,brainstorming,writing-plans,executing-plans,test-driven-development,subagent-driven-development,using-git-worktrees,requesting-code-review,verification-before-completion}/SKILL.md`
- OpenSpec 配置：[`openspec/config.yaml`](openspec/config.yaml)
- 项目级 spec：[`openspec/project.md`](openspec/project.md)
- 进行中的变更：`openspec/changes/`

## 第一次使用

本项目用 `openspec init --tools qoder` 初始化，斜杠命令由 OpenSpec CLI 提供：

- `/opsx:explore` — 探索现状、理清模糊需求
- `/opsx:propose <idea>` — 创建变更（生成 proposal/design/tasks）
- `/opsx:apply` — 按 tasks.md 推进实现（走 TDD）
- `/opsx:archive` — 归档完成的变更

> 重启 Qoder 让斜杠命令生效。

## 从模板开始

1. 与 AI 做一次 brainstorming，确定项目愿景与技术栈，回填 `openspec/project.md`。
2. 用 `/opsx:propose` 创建第一个 change，走完整流程。
3. 每次 `/opsx:archive` 后检查 `openspec/project.md` 是否需要更新。
