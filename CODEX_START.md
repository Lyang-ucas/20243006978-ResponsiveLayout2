# 在 Android Studio 中继续 Lab2

## 当前进度

本地原布局基线、land 和 sw600dp 三个开发阶段已完成并分别提交；手机和平板横竖屏实际验收通过。先读 `docs/resume-checkpoint.md` 和 `docs/device-verification.md`，不要重新执行布局开发。
Preview 尚有 Layoutlib 字体加载异常，未记为通过。远程发布与 Canvas 仍待真实学号、平台选择和发布授权。

## 打开工程

Android Studio → Open，选择 `/Users/lucas/Moving computing  lab/Lab2/Lab2`。这一层有 `settings.gradle` 和 `gradlew`；不要选择只有 PDF 的父目录。

等待 Gradle 同步完成，运行模块默认选 `linearapp`。先读 `docs/course-scope-review.md`；已按“基础、符合课件”要求收紧实现范围。
完整复制保留了 `.idea`，如 IDE 运行配置或终端指向 Lab1，请在 Lab2 副本中修正路径。

## 启动指定模型

Android Studio 的 Terminal 中执行：

```sh
cd '/Users/lucas/Moving computing  lab/Lab2/Lab2'
./start-codex.sh
```

脚本会进入自己的项目目录，并明确指定 `gpt-6-sol` 和 `high`。它先查找终端中的 `codex`，找不到时会使用本机已安装桌面应用内的 CLI，因此能处理截图中的 `codex: not found`。

截图表明 Android Studio 终端的 PATH 没有提供 `codex`。本机实际存在且已验证的可执行文件为 `/Applications/ChatGPT.app/Contents/Resources/codex`；无须为这次启动修改全局 shell 配置或重新安装。

如需绕过脚本，在工程目录可直接执行：

```sh
/Applications/ChatGPT.app/Contents/Resources/codex --model gpt-6-sol -c 'model_reasoning_effort="high"'
```

准备阶段已在不含桌面应用路径的 PATH 环境验证 `start-codex.sh --version` 可成功运行。该检查仅验证可执行文件查找；后续本次实施中，Android Studio 当前终端已实际显示 `gpt-6-sol high` 会话。官方安装与诊断说明见 [Codex CLI](https://learn.chatgpt.com/docs/codex/cli) 与 [故障排查](https://learn.chatgpt.com/docs/reference/troubleshooting)。

项目 `.codex/config.toml` 同时保存了 `model = "gpt-6-sol"` 与 `model_reasoning_effort = "high"`。项目配置仅在信任项目后加载；明确启动参数可以避免依赖项目配置的加载状态。不要为此更改全局模型配置。

进入会话后使用 `/status` 核对模型及推理强度；需要时用 `/model` 查看可选模型。若账号或 CLI 不提供 `gpt-6-sol`，先说明具体错误，不自动换模型。本次准备工作没有发起该模型的实际推理请求，因此不等于验证了账号访问权限。

配置和启动参数参考 [OpenAI 官方配置文档](https://learn.chatgpt.com/docs/config-file/config-reference)；模型名称与 high 支持见 [GPT-6 Sol 官方文档](https://developers.openai.com/api/docs/models/gpt-6-sol)。本机 CLI 的帮助已核对支持 `--model` 和 `-c`。

## 可直接粘贴的续接请求

```text
请先读取 AGENTS.md、docs/resume-checkpoint.md 和 docs/device-verification.md，核对当前 Git 状态与历史。本地默认、land 和 sw600dp 布局已完成，四种设备配置已实际验收，不要重复实施、复制工程或凑提交。请根据我接下来指定的事项继续；保持课程基础方法和 Lab1 原布局。Preview 的 assetStream 字体加载异常尚未解决，未执行的可选测试如实保留。没有新的发布指令时不要创建远程、推送或提交 Canvas。模型使用 gpt-6-sol，推理强度 high。
```

后续可指定讲解三个 XML 的方向和权重，或排查本机 Preview 字体异常。T0–T3 与设备验收已有记录，不需要再次启动开发阶段。

发布前仍需你的真实学号和平台选择。老师规定的最终仓库名是 `<学号>-ResponsiveLayout2`，公开可访问；发布需后续明确指令，本地开发已完成。
