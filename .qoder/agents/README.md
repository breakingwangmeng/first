# 子代理索引

本目录定义治理流程中可派发的子代理（Subagent）。各子代理只做一件事，输入输出明确。

| 子代理 | 职责 | 触发时机 |
|---|---|---|
| [product-manager](product-manager.md) | 需求澄清与 propose 起草 | brainstorming 之后、propose 之前 |
| [spec-reviewer](spec-reviewer.md) | Spec 合规审查，输出对账报告 | 实现完成、提交 review 时 |
| [test-agent](test-agent.md) | TDD 测试编写（RED 阶段） | 进入实现、写失败测试时 |

## 使用约定

- 子代理只读输入、产出报告或文件，不直接推进流程。
- 子代理的输出必须回到主对话，由人类确认后再进入下一阶段。
