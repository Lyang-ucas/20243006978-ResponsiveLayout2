# Lab2 设备验证记录

日期：2026-09-26。默认实施模块：`linearapp`。本文件随真实开发阶段更新，不使用复制来的 Lab1 报告作为验收证据。

## T0 / T1：原布局基线

- 用户已授权从中断点继续完成本地实施及分阶段提交。
- 已重新核对教师提交规范两页；Lab2 父目录中仅发现提交规范，没有额外界面任务书。重排按 `LAB2_TASK_PLAN.md` 的建议进行。
- Android Studio 窗口路径为 `~/Moving computing  lab/Lab2/Lab2`，运行模块为 `linearapp`。已触发 Gradle 同步；项目配置使用 `$PROJECT_DIR$`，未发现需修复的旧目录。
- 当前目录执行 `:linearapp:assembleDebug --console=plain` 成功，使用 Android Studio 内置 JDK。首次沙箱执行因用户 Gradle 缓存锁访问受限失败；获权限后相同命令成功，无构建配置修改。
- 手机 AVD：`Pixel_10_Pro_XL`；Android 17 / API 37；1344 × 2992 px，480 dpi；运行时 `sw448dp w448dp h997dp port`。
- 已安装并实际启动原布局，检查标题、四个按顺序排列的等宽色块、应用文字和两个按钮均完整显示；系统手势区域未遮挡按钮。
- 原始截图：[phone-portrait-baseline.png](screenshots/phone-portrait-baseline.png)。保留原 Lab1 文案与默认布局。
- 本次 Android Studio 终端中已观察到当前会话为 `gpt-6-sol high`；不再将模型访问描述为已验证之前的脚本检查结果。

## T2：手机横屏

- 新增 `linearapp/src/main/res/layout-land/activity_main.xml`，从默认布局复用全部关键 ID、字符串、颜色及按钮背景。默认布局未修改。
- 顶部标题保持内容高度，下方 horizontal 容器按 2:1 分配两区宽度；左侧标题和四个等宽色块，右侧应用文字与按钮。
- 构建、lint、已有 2 个单元测试任务通过；单元测试为原布局契约，本次显示 UP-TO-DATE，不声称它们覆盖新变体。
- 手机运行配置：`sw448dp w997dp h448dp land`。读取实际 Activity 视图层级并观察截图，确认已使用左右分区；四个色块各 460px 宽。
- 已运行 `:linearapp:connectedDebugAndroidTest`，1 个已有测试通过。Gradle 设备测试结束后会卸载被测应用，后续截图前已重新安装。
- 已执行应用运行中的竖屏→横屏→竖屏，回到原布局；[返回竖屏截图](screenshots/phone-return-portrait.png)与基线 PNG 字节一致。
- [横屏截图](screenshots/phone-landscape.png)：全部文字、色块和按钮完整，按钮未被手势区域遮挡。
- lint 没有错误。存在 NestedWeights、DisableBaselineAlignment、ButtonOrder、ButtonStyle 及依赖更新建议；保留课程权重方法、原控件顺序和背景，不添加忽略规则或升级依赖。
- 初始基线提交：`d12ddab`。初始导入的 `git diff --check` 曾报告继承文件末尾空行；为保留 Lab1 文件原样未进行格式重写。

## T3：平板宽度适配

- 新增 `linearapp/src/main/res/layout-sw600dp/activity_main.xml`，从原布局复制后调整比例：根容器四个区域为 1:3:1:1，内容区内标题/色块为 1:2；内容区和按钮区两侧为 32dp。四个色块保持等宽与原阅读顺序。
- 独立临时 AVD：`Lab2_Tablet_API37`，位于 `/private/tmp/lab2-avds`；复用已安装的 Android 17 / API 37 ARM64 镜像，1200 × 1920px、240dpi，实际 `sw800dp`。没有修改已有手机 AVD 或下载镜像。
- 竖屏实际 Activity 配置为 `sw800dp w800dp h1280dp port`；内容外边距 48px = 32dp，色块各 276px 宽。截图：[tablet-portrait.png](screenshots/tablet-portrait.png)。
- 横屏实际 Activity 配置为 `sw800dp w1280dp h800dp land`；内容外边距仍为 48px，色块各 456px 宽。截图：[tablet-landscape.png](screenshots/tablet-landscape.png)。
- 两方向均实际加载平板上下布局，没有回退到手机横屏左右布局；文字、色块与按钮完整，手势区域未遮挡按钮。无需增加 `layout-sw600dp-land`。
- 平板竖屏和横屏分别运行已有设备测试，各 1 个测试通过；另实际旋转回竖屏并检查显示恢复。

## T4：最终检查与局限

| 配置 | 实际选用资源 | 实际运行和截图 | 已有设备测试 |
| --- | --- | --- | --- |
| 手机竖屏，sw448dp | `layout` | 通过；最终 APK 截图与基线字节一致 | 1 个通过 |
| 手机横屏，sw448dp | `layout-land` | 通过；T2 已验证，最终阶段未修改此 XML | 1 个通过（T2） |
| 平板竖屏，sw800dp | `layout-sw600dp` | 通过 | 1 个通过 |
| 平板横屏，sw800dp | `layout-sw600dp` | 通过 | 1 个通过 |
| 手机竖→横→竖、平板横→竖 | 系统自动选择资源 | 通过，未添加方向锁定或手动判断 | 以实际运行检查 |

最终执行命令：

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:assembleDebug :linearapp:lintDebug :linearapp:testDebugUnitTest :constraintapp:assembleDebug :constraintapp:lintDebug :constraintapp:testDebugUnitTest --console=plain
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:connectedDebugAndroidTest --console=plain
```

- 两模块构建与 lint 任务通过；两模块各 2 个已有单元测试为缓存通过（UP-TO-DATE），源码未修改；本次没有新增测试代码。
- 设备测试分别在以上四种配置实际执行；后一次 Gradle 运行会覆盖报告，应以本记录、截图和各阶段提交辨别执行范围。
- Preview 已尝试：原布局在 Android Studio 中显示两项 `Cannot invoke "java.io.InputStream.available()" because "assetStream" is null`。日志堆栈位于 Layoutlib 的 `Font_Builder_Delegate.createBuffer` / `ResourceHelper.getFont`。这是字体加载时的 IDE 渲染异常，Preview 验收未通过；设备四配置视觉验收已完成。没有据此修改原布局、升级/降级构建工具或全局 SDK。
- 未执行可选的 599/600dp 阈值、窗口缩放、放大字体及其他导航模式测试；未在本次运行保留模块的设备测试。
- 本机仅安装 API 37 平台，本次不声称覆盖所有 minSdk 23–37 设备。

## T5：最终文件核对

- 再次只读比较两模块原源码、资源、测试、构建配置和 Wrapper 共 33 个文件，与 Lab1 字节一致；本次应用变更仅为两个新增布局 XML。
- 最终 lint 报告：linearapp 0 错误 / 16 警告，constraintapp 0 错误 / 4 警告。
- `git diff --check` 对本次新增与文档修改通过；`git ls-files -ci --exclude-standard` 无输出，缓存、构建产物、本机配置和签名文件未被跟踪。
- 三个真实开发提交为 `d12ddab`（基线）、`93015ba`（横屏）、`081ebc8`（平板）；随后提交 README、当前状态和续接说明。
- `git remote -v` 无输出；未发布、推送或操作 Canvas。
