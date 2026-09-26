# Lab1 副本基线验证

日期：2026-09-26。
路径说明：以下是首次复制时的历史记录。目录后来改名，当前入口为 `/Users/lucas/Moving computing  lab/Lab2/Lab2`；原 Lab1 在 `/Users/lucas/Moving computing  lab/Lab1/Lab1`。
验证对象：`/Users/lucas/Moving computing/Lab2/Lab2`，尚未实现 Lab2 变体。

## 复制

- 源项目：`/Users/lucas/Moving computing/Lab1/Lab1`。
- 复制时逐文件核对 1547 个文件的 SHA-256，全部一致。
- 排除的仅是 Lab1 的 `.git` 元数据；复制清单见 `lab1-copy-manifest.json`。
- 副本已初始化全新 Git 仓库 `main`；无提交、无远程。未修改原仓库。
- 后续编译更新的构建产物和缓存不作为源码复制一致性的持续判断依据。

## 工具链与检查结果

- 继承工具链：AGP 9.3.0、Gradle 9.5.0、compileSdk/targetSdk 37、minSdk 23、Java 源码兼容级别 17。
- 使用 JDK：Android Studio 内置 `/Applications/Android Studio.app/Contents/jbr/Contents/Home`。
- Android SDK：`/Users/lucas/Library/Android/sdk`。

实际执行：

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:assembleDebug :linearapp:lintDebug :linearapp:testDebugUnitTest :constraintapp:assembleDebug :constraintapp:lintDebug :constraintapp:testDebugUnitTest --console=plain
```

结果：`BUILD SUCCESSFUL in 12s`，94 个任务执行。

| 检查 | linearapp | constraintapp |
| --- | --- | --- |
| assembleDebug | 通过 | 通过 |
| lintDebug | 通过 | 通过 |
| testDebugUnitTest | 通过，2 个测试方法 | 通过，2 个测试方法 |

lint 详情在各模块 `build/reports/lint-results-debug.html`。成功意味着检查任务通过，不必然意味着没有所有级别的提示；后续以报告为准。构建有 Gradle 弃用提示，当前未阻塞构建；本次不升级依赖或构建工具。

## 未执行项目

- 当时 `adb devices -l` 没有连接设备。
- 已配置 AVD 名称只有 `Pixel_10_Pro_XL`，未启动。
- 未执行 connectedDebugAndroidTest、旋转测试或截图验收。
- 未创建平板 AVD。
- 未创建 Lab2 横屏/宽度变体。
- 未生成开发提交，未创建远程、未推送、未提交 Canvas。
- 未发起 gpt-6-sol 实际请求；只保存了所需配置与明确启动参数。
