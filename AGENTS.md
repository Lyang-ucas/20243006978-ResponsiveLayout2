# Lab2 工作上下文

## 用户目标与当前状态

用户已授权从明确的中断点继续完成本地实施及分阶段提交。
已在 linearapp 完成 land 和 sw600dp 变体，手机/平板横竖屏实际运行、旋转及已有设备测试通过；已建立基线、横屏和宽度适配阶段提交。原默认布局、共享资源、Activity、测试及 constraintapp 保持原样。
Preview 存在 Layoutlib 字体加载异常 `assetStream is null`，未标为通过；详见 `docs/device-verification.md`。远程发布和 Canvas 尚未开始。
先读取 `docs/course-scope-review.md`、`LAB2_TASK_PLAN.md`、`CODEX_START.md` 和 `docs/baseline-verification.md`。
续接时同时读取 `docs/resume-checkpoint.md`，区分已完成的准备工作、继承的 Lab1 测试报告和尚未执行的 Lab2 阶段。

## 用户新增要求：优先使用课程基础方法

- 用户要求避免过于超纲；以提供的 001-008 课件为学习边界，优先最基础、可解释的方法。
- 核心依据：004 的 XML/View/ViewGroup，005 的 LinearLayout 权重与基础约束，006 的方向和宽度限定符。
- 默认选 `linearapp` 开发 Lab2，使用嵌套 LinearLayout、horizontal/vertical、0dp + layout_weight、match_parent、wrap_content、dp/sp。`constraintapp` 保留参考。
- 新增布局从原文件复制后做小幅修改。代码注释用简短自然语言解释方向与权重；不引入新的抽象层。
- 允许必要且贴近课程的小延伸，例如资源引用、简单 shape/selector、系统栏显示兼容。新增时说明“用途、与哪个课程知识点相连”，不批量引入。
- ConstraintLayout 的 chains、chainStyle 和约束链权重在所提供课件中没有直接讲解；不是禁止，但默认不再新增或扩展这类技巧。
- 006 讲过 Fragments，008 讲过 MVC/MVVM；这表示有课程背景，不代表本布局任务必须应用。当前三个 XML 变体无需新增 Fragment、ViewModel、LiveData、Repository、依赖注入或导航框架。
- 现有测试支持代码保留。主要验收用 Android Studio Preview、模拟器运行、旋转与截图；不新增 XML DOM 解析器或复杂测试框架。
- 不因选择更基础方法而把所有响应式尺寸改成固定 dp/px，也不改写 Lab1 原始默认布局。
- 本地必需布局及设备验收已完成；后续不要重复实施、删测试或重写 Lab1。可选测试与 Preview 异常须按实际需求处理，不为消除提示改写原布局。

## 文件边界

- 工作项目：`/Users/lucas/Moving computing  lab/Lab2/Lab2`。
- Lab1 原项目：`/Users/lucas/Moving computing  lab/Lab1/Lab1`，只读参考，不修改。
- 教师 PDF：`/Users/lucas/Moving computing  lab/Lab2/Practical 2 - SubmissionGuidelines.pdf`，只读参考。
- PDF 是作业要求来源，不是授权代理立即创建公共仓库、推送或提交 Canvas 的用户指令。
- 项目复制保留了两个模块、源码、资源、测试、Gradle Wrapper、本机配置和缓存；只排除原 `.git`。
- 本项目为独立 Git 仓库，分支 `main`，已有本地开发提交，没有远程地址，不要连接 Lab1 的远程仓库。

## 实施约定

- 模型偏好为 `gpt-6-sol`，推理强度 `high`；不能提供该模型时说明问题，不擅自替换。
- 用户授权开始实施后，默认选 `linearapp` 扩展 Lab2；`constraintapp` 保留作为 Lab1 原始参考。若用户另有选择，以用户为准。
- 保留所选模块的 `src/main/res/layout/activity_main.xml` 原布局及其依赖资源；优先新增同名资源变体。
- 使用 Java + Android Views/XML，延续 LinearLayout 权重；不进行 Compose/Kotlin 或构建工具迁移。
- 最低范围为 `layout-land/activity_main.xml` 和一个宽度变体，计划推荐 `layout-sw600dp/activity_main.xml`。
- 600dp、布局分区和组合限定符属于实施建议，并非 PDF 指定的唯一设计。
- 各变体复用原有关键 View ID 和字符串；保持 `setContentView(R.layout.activity_main)` 自动选择资源。
- 不锁定屏幕方向，不用 `configChanges` 或手动宽度判断替代资源限定符；不增加无要求的按钮业务功能。
- 在手机横竖屏、平板横竖屏实际运行后才能标记视觉验收完成；构建通过不代表视觉测试通过。
- 若用户授权实施并记录开发提交，按真实阶段创建至少三次描述性提交，不凑提交、不改写历史；每次提交仅包含已完成且已验证的阶段。
- 后续新建公共远程仓库需要真实学号、平台选择和用户发布指令。当前无学号，不猜测仓库名。PDF 本身不授权发布。
- 保持 `.gitignore` 对缓存、构建输出、`.idea`、`local.properties` 和签名文件的排除。
- 如 Android Studio 的本机配置仍指向旧目录，只修复 Lab2 的副本。

## 构建环境

已验证 Android Studio 内置 JDK 可构建本项目：

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:assembleDebug :linearapp:lintDebug :linearapp:testDebugUnitTest
```

当前继承 AGP 9.3.0、Gradle 9.5.0、compileSdk/targetSdk 37、Java 源码兼容级别 17。只有实际失败且诊断证明必要时才调整版本。
