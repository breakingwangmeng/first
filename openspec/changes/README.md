# Changes（变更提案）

本目录存放**进行中的变更提案**。任何代码改动前，必须先在此产出提案三件套并经人类签字。

## 结构

```
openspec/changes/
├── <change-name>/          # 进行中的变更（kebab-case）
│   ├── proposal.md         # 为什么做、改什么（what & why）
│   ├── design.md           # 怎么做（技术方案）
│   ├── tasks.md            # 实施清单（可勾选，每项可验证）
│   └── specs/              # 规格增量（ADDED/MODIFIED/REMOVED Requirements）
│       └── <capability>/
│           └── spec.md
└── archive/                # 已完成并归档的变更（<date>-<name>/）
```

## 工作流

```
/opsx:explore   →   /opsx:propose <idea>   →   /opsx:apply   →   /opsx:archive
```

1. **explore**：需求模糊时，先探索现状、对比方案、理清边界。
2. **propose**：生成 change 目录与三件套，人类签字后才进入实现。
3. **apply**：按 tasks.md 逐项实现，走 TDD（RED→GREEN→REFACTOR）。
4. **archive**：变更完成后归档到 `archive/<date>-<name>/`，spec 增量合入 `specs/`。

## 人类把关点

- **proposal.md 签字**：确认"做的是对的事"（范围、非目标、验收标准）。
- **design.md 签字**：确认"用对的方式做"（技术方案、被否决的备选）。
- **review 报告**：critical 问题清零才能继续（见 `.qoder/skills/requesting-code-review/`）。

## 约定

- 一个 change 只做一件事，命名 kebab-case。
- 无签字不写代码。跳过流程的代码改动会被回退。
