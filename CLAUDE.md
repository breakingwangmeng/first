# CLAUDE.md

本文件是 Claude Code 的入口指针。完整说明见 [AGENTS.md](./AGENTS.md)。

核心要求：任何代码改动前必须走 OpenSpec 流程（proposal → design → tasks，人类签字后实现），实现阶段强制 TDD。任务开始前检查 `.qoder/skills/` 下相关技能并遵循。

斜杠命令由 OpenSpec CLI 提供：`/opsx:propose`、`/opsx:apply`、`/opsx:archive`、`/opsx:explore`。
