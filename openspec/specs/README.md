# Specs（活文档）

本目录存放**已归档 capability 的活规格**（living specifications），是系统当前真实行为的唯一权威描述。

## 结构

```
openspec/specs/
└── <capability>/
    └── spec.md        # 该 capability 的需求与场景（SHALL / Scenario 格式）
```

## spec.md 格式

纯 Markdown，无特殊语法。需求用 SHALL 声明，每个需求配 1+ 个场景：

```markdown
## Requirement: 主题选择
系统 SHALL 允许用户在亮色与暗色主题间切换，默认跟随系统偏好。

#### Scenario: 用户切换暗色模式
- **WHEN** 用户点击主题切换按钮
- **THEN** 应用切换到暗色模式并持久化该选择
```

## 生命周期

1. **来源**：`openspec/changes/<name>/specs/` 中的增量变更（ADDED / MODIFIED / REMOVED Requirements）。
2. **合入**：`/opsx:archive` 时，change 内的 spec 增量被合并进本目录对应 capability 的 spec.md。
3. **维护**：capability 的行为变更必须先走 propose 流程，禁止直接手改本目录。

## 约定

- 每个 change 对应一个独立 capability（kebab-case 命名），避免 archive 冲突。
- 本目录只进不出：被 archive 的需求是历史事实，删除需求用 REMOVED Requirements 走新 change。
- 配套工程 brief 放 `openspec/notes/`，不放在本目录。
