---
name: using-superpowers
description: Superpowers 技能系统总入口。每个会话开始时读取，决定何时触发哪个技能。
when-to-use: 会话开始 / 收到任何开发任务时
---

# using-superpowers

Superpowers 是一套可组合的开发技能库，加上这条引导规则，保证技能**被真正使用**。

## 核心原则

1. **任务前检查技能**：任何任务开始前，检查 `.qoder/skills/` 下是否有相关技能，有则必须遵循。
2. **强制流程，不是建议**：技能定义的流程是硬约束，跳过流程等同于违反规则。
3. **技能自动触发**：按场景匹配，无需用户显式调用。

## 技能地图

| 场景 | 技能 |
|---|---|
| 需求模糊、要开始新东西 | brainstorming |
| 已有签字的设计，要拆任务 | writing-plans |
| 有任务清单，要动手实现 | executing-plans |
| 实现中写任何代码 | test-driven-development |
| 任务间需要独立审查 | requesting-code-review |
| 并行开发多个 change | using-git-worktrees |
| 完成前确认真的修好了 | verification-before-completion |
| 要派子代理逐任务开发 | subagent-driven-development |

## 工作流全景

```
brainstorming  →  propose（OpenSpec）  →  writing-plans  →  executing-plans
（每任务内 TDD）  →  requesting-code-review  →  verification-before-completion  →  archive（OpenSpec）
```

## 违规自检

发现自己跳过了某个技能定义的步骤时：停下来，回到该技能的起点重做，而不是假装做过。
