#!/bin/sh
# Start a local interactive session with the user's requested model and effort.
set -eu
lab2_project_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
cd "$lab2_project_dir"

# Android Studio terminals may not inherit the desktop app's PATH.
if command -v codex >/dev/null 2>&1; then
    lab2_codex_bin=codex
elif [ -x '/Applications/ChatGPT.app/Contents/Resources/codex' ]; then
    lab2_codex_bin='/Applications/ChatGPT.app/Contents/Resources/codex'
elif [ -x '/Applications/Codex.app/Contents/Resources/codex' ]; then
    lab2_codex_bin='/Applications/Codex.app/Contents/Resources/codex'
else
    printf '%s\n' '未找到 Codex CLI。请安装 Codex CLI 或确认 ChatGPT/Codex 桌面应用已安装。' >&2
    exit 127
fi

exec "$lab2_codex_bin" --model gpt-6-sol -c 'model_reasoning_effort="high"' "$@"
