---
name: requesting-code-review
description: 对照计划审查已完成的工作，按严重级别输出问题，critical 阻塞
when-to-use: 完成一个任务 / 完成整个 change / 准备合并分支时
---

# requesting-code-review

审查的对象是"与计划的符合度 + 代码质量"，不是找茬。

## 审查维度

1. **Spec 符合度**：实现是否覆盖 proposal / design / tasks 的每一条要求？
2. **范围控制**：是否混入了计划外的改动？
3. **测试质量**：测试是否真的验证了行为？有无漏测边界？
4. **代码质量**：命名、职责划分、错误处理（对照 `.qoder/rules/coding-conventions.md`）。

## 问题分级

| 级别 | 定义 | 处理 |
|---|---|---|
| critical | 与 spec 不符、测试缺失或造假、明显 bug、范围蔓延 | 必须修复才能继续 |
| warning | 可接受的偏差、测试覆盖不足、潜在性能问题 | 需说明理由或补测试 |
| info | 风格、命名建议 | 可选 |

## 流程

1. 收集：读取 change 的 spec 三件套 + Git diff + 测试结果。
2. 逐条对账，输出问题清单（每条标注级别、位置、依据）。
3. critical 未清零 → 修复后重新审查，禁止带病推进。
4. 报告交给用户确认后再进入下一阶段（或归档）。

## 反模式

- 只审代码不审 spec。
- 把 warning 当 critical 阻塞流程（或反之）。
- 审查意见模糊："这里写得不好"（必须指出具体问题与改法）。
