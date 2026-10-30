---
description: Git 分支与提交规范，always-on 全局生效
trigger: always_on
---

# Git Workflow

## 分支策略

- 主分支 `main` 保持始终可发布：测试全绿、review 通过后才可合并。
- 每个 change 对应一个 feature 分支：`feat/<change-name>`（与 `openspec/changes/<name>/` 同名）。
- 隔离开发推荐使用 git worktree（见 `.qoder/skills/using-git-worktrees/`）。

## Commit Message

遵循 Conventional Commits 格式：

```
<type>(<scope>): <subject>
```

- type：`feat` / `fix` / `refactor` / `test` / `docs` / `chore`
- scope：change 名或模块名（kebab-case）
- subject：祈使句，50 字符内，说明"做什么"

## TDD 提交节奏

- RED：先提交失败测试（允许 `test` 前缀）。
- GREEN：最小实现使测试通过后提交。
- REFACTOR：重构提交与行为变更提交分离，禁止混在一起。

## 归档要求

- `/opsx:archive` 后，spec 合入与代码变更应在同一批提交中关联。
- 提交信息关联 change 名，便于回溯。
