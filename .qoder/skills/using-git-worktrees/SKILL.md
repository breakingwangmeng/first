---
name: using-git-worktrees
description: 用 git worktree 在新分支隔离开发，每个 change 一个独立工作区
when-to-use: 并行开发多个 change / 需要隔离实验而不污染主工作区时
---

# using-git-worktrees

每个 change 一个 worktree：互不干扰、随时切换、主工作区保持干净。

## 创建 worktree

```bash
# 基于 main 为 change 创建分支 + 独立工作区
git worktree add -b feat/<change-name> ../<change-name> main

# 新工作区内安装依赖、跑基线测试
cd ../<change-name>
<install-commands>
<test-command>   # 必须全绿，不绿先修基线
```

## 纪律

- worktree 目录放在主仓库同级（`../<change-name>`），不要嵌套在仓库内。
- 每个 change 一个 worktree，完成后立即清理（见 finishing 步骤）。
- 基线测试不绿不动工：先解决环境/基线问题。

## 收尾

1. 变更合并回 main 后，删除 worktree：
   ```bash
   git worktree remove ../<change-name>
   git branch -d feat/<change-name>
   ```
2. 确认 `git worktree list` 无残留。

## 反模式

- 在多个 worktree 里改同一批文件（冲突自找）。
- worktree 不清理，越攒越多。
- 基线没验绿就开始开发。
