# Lab2 任务拆解与实施计划

整理及续接日期：2026-09-26。本计划最初用于准备，之后用户授权完成本地实施。当前已完成 linearapp 横屏与 sw600dp 变体、设备验收和三次阶段提交；README 与续接文档同步整理。Preview 字体加载异常尚未解决，未记为通过；发布与 Canvas 仍待后续。以下分阶段步骤保留作过程依据，最新证据见 `docs/device-verification.md` 和 `docs/resume-checkpoint.md`。

## 1. 本次请求、老师要求与实施建议

用户本次请求：阅读 Lab2、检查 Lab1、复制完整工程到 Lab2，并写好后续 Codex CLI 可执行的详细任务；后续模型使用 `gpt-6-sol`，推理强度 `high`。

老师要求来源：父目录的 `Practical 2 - SubmissionGuidelines.pdf`，共 2 页，已阅读全部内容及页面。
这是一份提交规范，没有完整的 Lab2 布局示意图、具体重排方式、评分细则或截止日期。以下实施方案覆盖已知要求；若课程另有 Practical 2 任务书，应先核对后再最终确定布局。

| PDF 明确要求 | 需要交付的内容 | 当前状态 |
| --- | --- | --- |
| 使用从 Practical 1 选取的原布局 | 所选模块的 `res/layout/activity_main.xml` 和依赖资源 | 已复制，两种版本均保留 |
| 提供横屏 alternative layout | 同名 `res/layout-land/activity_main.xml` | 已完成 linearapp 横屏变体 |
| 提供 smallest width 或 available width alternative layout | `layout-sw<N>dp` 或 `layout-w<N>dp` 内的同名布局 | 已完成 linearapp sw600dp 变体 |
| 包含完整 Android Studio 工程 | 模块、源码、资源、构建配置、Wrapper | 已复制 |
| 在合适虚拟设备上测试 | 手机与适合宽度变体的设备运行验证 | 手机 sw448dp、平板 sw800dp 横竖屏实际运行通过 |
| 新建独立 GitHub/GitLab 仓库，不能使用 Practical 1 仓库 | 新远程仓库 | 本地独立仓库已初始化，远程未创建 |
| 仓库名 `[Student ID]-ResponsiveLayout2`，公开 | 使用真实学号命名并验证 public | 学号未提供 |
| 至少 3 次有信息量的提交，体现开发过程 | 原布局基线、横屏、宽度适配等真实阶段 | 已建立三次验证后阶段提交 |
| 完整工程及最新改动已推送 | 远程内容与本地一致 | 待后续发布 |
| Canvas 提交公开仓库 URL | 仓库链接，通常无需另传工程文件 | 待用户提交 |

PDF 中的提交示例出现 `sw600dp`，并不等于强制使用 600dp。006 课件第 17 页也介绍 sw600dp。选 LinearLayout、具体左右分区、增加组合限定符及截图文档都是本计划的建议。

## 1A. 课程基础边界（用户新增要求）

本节是用户要求的实施约束；优先于此前默认采用 ConstraintLayout 的建议。详细审查见 `docs/course-scope-review.md`。

- 004 第 6、12、17、21-25 页：Java Activity、XML、LinearLayout、TextView 和 Button。
- 005 第 11、13-14 页：0dp + layout_weight、match_parent/wrap_content、dp/sp。
- 006 第 15、17、19、28-30 页：同名 alternative layout、land/sw/w 限定符与横竖方向重排。
- 首选三个 activity_main.xml：原默认布局、land、sw600dp。组合限定符虽在 006 第 31 页出现，也仅在实际需要时增加。
- 原布局照旧保留。横屏用 horizontal 容器把原来的上下区域排成左右区域；平板主要调整方向、空间比例和边距。无需新架构层。
- 学过并不等于必须使用：006 的 Fragments、008 的 MVC/MVVM 对这个静态界面不是必要条件。
- 默认不新增 Kotlin/Compose、ViewModel/LiveData、依赖注入、导航框架、动态宽度判断或复杂测试工具。
- 紧邻课程且有实际用途的小延伸可以使用，但应附一句用途说明；不能因为库常见就成批引入。
- 最初准备阶段仅修订计划与审查记录；后续已获用户授权实施。不重写 Lab1，不删除现有测试。

## 2. Lab1 基线与复制结果

- 原目录：`/Users/lucas/Moving computing  lab/Lab1/Lab1`。
- 新目录：`/Users/lucas/Moving computing  lab/Lab2/Lab2`，Android Studio 应打开这一层。
- 原工程名：`ResponsiveLayout1`，含 `linearapp` 和 `constraintapp` 两个独立应用模块。
- 两个模块都使用 Java Activity + XML Views；Activity 仅加载 `R.layout.activity_main`，没有按钮业务逻辑。
- 默认布局包含 Lab 1 标题、Responsive Layout 1 标题、This/is/my/first 四个色块、Android Application 文字、Change/Cancel 按钮。
- Linear 版通过嵌套容器和权重适配；Constraint 版通过约束链及权重适配。
- 每个模块已包含 2 个本地布局契约测试及 1 个设备测试方法；原测试只针对原默认布局，不能证明 Lab2 变体正确。
- 所有项目文件均已复制，含隐藏文件、本机配置与缓存；仅原 `.git` 未复制，避免继承 Lab1 的远程配置和提交历史。
- 复制时核对 1547 个文件的 SHA-256；记录见 `docs/lab1-copy-manifest.json`。后续构建会更新副本缓存，清单反映复制时的状态。
- Lab1 工作区原本干净，复制后不修改原目录。
- 副本的两个模块已完成 Debug 编译、lint、原有单元测试，结果见 `docs/baseline-verification.md`。

## 3. 建议选用的模块与目标文件

根据用户新增的“最基础、按课件思路”要求，默认选 `linearapp` 作为 Lab2 实施对象，保留 `constraintapp`。PDF 使用“selected”布局，未要求两种实现都扩展；保留两个模块方便对照，也满足完整项目复制的请求。

```text
linearapp/src/main/res/
  layout/activity_main.xml                 已有：保留 Lab1 默认布局
  layout-land/activity_main.xml            必做：新增手机横屏布局
  layout-sw600dp/activity_main.xml         必做：推荐的宽度适配选项
  layout-sw600dp-land/activity_main.xml    可选：仅平板横屏需要不同重排时新增
  values/dimens.xml                        已有：共享尺寸，优先不改变默认值
  values-sw600dp/dimens.xml                可选：平板尺寸覆盖
```

辅助文件：更新 `README.md`，新增 `docs/device-verification.md` 和按需保存 `docs/screenshots/`。本计划、CLI 指南、复制核对清单已在项目内。

宽度限定符解释：`sw600dp` 按应用可用区域的最小尺寸匹配；`w600dp` 按当前可用宽度匹配，因此手机旋转也可能触发 `w600dp`。窗口缩放可能改变匹配。限定符规则及顺序以 [Android Views 资源文档](https://developer.android.com/topic/architecture/views/resources/providing-resources-views) 为准。

本计划推荐 `sw600dp`，便于独立演示手机横屏与平板宽度适配。若课程任务书要求 available width，则切换为 `w<N>dp` 并同步修改测试矩阵。

## 4. 分阶段任务

### T0：核对输入与开发范围

1. 先读 `docs/course-scope-review.md`、本计划、项目 `AGENTS.md`、教师 PDF，以及 004/005/006 课件；确认是否还有另一份详细任务书。
2. 如果没有额外任务书，按已知要求推进，记录布局设计采用本计划建议，不声称满足尚未提供的示意图。
3. 默认选择 `linearapp`，使用课程直接讲过的方向和权重；如用户另选 Constraint 版，优先基础约束，必要延伸应解释，不直接扩大实现范围。
4. 学号与 GitHub/GitLab 选择只影响发布阶段，不阻塞本地布局开发。远程仓库名称不得使用虚构学号。

验收：明确所选模块、原布局保留方式、宽度限定符选择，以及未提供的信息。

### T1：建立可运行的初始阶段

1. Android Studio 打开 Lab2 工程并同步 Gradle；核对 Project 与终端当前路径确实为新目录。
2. 默认保持原 `layout/activity_main.xml`、字符串、颜色、按钮样式及 View ID。
3. 运行所选模块，保存原布局基线截图。当前应用 ID 与 Lab1 相同，安装可能覆盖设备上同 ID 的旧应用；若需同时安装，再有意识地改 Lab2 的 applicationId。
4. 工程显示名可改为 `ResponsiveLayout2`，这是整理建议，不是 PDF 强制项；避免无理由改 namespace 和包目录。
5. 确认 `.gitignore` 有效，只提交工程文件与有用文档，不提交本机配置、缓存和 APK。
6. 用户开始实施并授权记录开发阶段后，建立第一条真实提交：`Import Practical 1 layouts as baseline for Practical 2`。

验收：原布局可运行；源码仍与 Lab1 对应；独立本地 Git 无 Lab1 远程地址。

### T2：实现横屏布局

1. 新建 `linearapp/src/main/res/layout-land/activity_main.xml`，文件名保持一致。
2. 从原布局复用控件、资源及 ID，调整 LinearLayout 的嵌套、方向和权重，使较短的竖向空间能容纳全部内容。
3. 设计建议：保留顶部标题，下方左右分区；左侧放布局标题与四个色块，右侧放应用文字及按钮。四个色块仍保持 This/is/my/first 顺序。
4. 对照 005 第 11 页和 006 第 28-30 页：horizontal 容器内的加权子项用 width=0dp；vertical 容器内用 height=0dp；剩余维度采用 match_parent/wrap_content。主要使用简单的 1:1 或 1:2 比例，只在保留原视觉比例时沿用原值。文字用 sp，间距用 dp。
5. 不改变默认布局，不通过锁屏方向或 Activity 代码切换布局。
6. 在手机模拟器横屏实际运行，并旋转回竖屏；检查系统栏、文字、四个色块和按钮均可见，没有重叠或裁切。
7. 每次横屏阶段验证完成后形成独立提交：`Add landscape layout for wider screens`。

验收：有真实重排，手机横屏自动加载变体，竖屏仍加载原布局。

### T3：实现宽度适配布局

1. 新建 `linearapp/src/main/res/layout-sw600dp/activity_main.xml`；若选 available width，改用已确认的 `layout-w<N>dp`。
2. 建议平板采用更宽的内容区域、增加合理外边距，并让文字区与按钮区的空间比例区别于原布局；保留四个等宽色块的阅读顺序。
3. 使用相同的 LinearLayout 方向与权重分配空间，优先只新增布局 XML。仅确有需要时用 values-sw600dp/dimens.xml 调整大屏间距与字号，并用“同名资源在大屏被替换”解释这一小延伸。
4. 创建或使用实际满足 `smallestScreenWidthDp >= 600` 的平板 AVD；不能仅凭像素分辨率判断。
5. 验证平板横竖屏；只有宽度与方向同时匹配时，`sw600dp` 的优先级高于单独 `land`，可能两种方向都用宽度变体。
6. 若同一大屏布局在横竖屏都合适，保留三种布局即可；如横屏需要不同重排，再加 `layout-sw600dp-land`，顺序不可写成 `layout-land-sw600dp`。
7. 宽度阶段完成并验证后独立提交：`Add sw600dp alternative layout for tablets`，如选择 w 限定符则相应修改提交说明。

验收：达到阈值的可用区域加载大屏布局；手机仍正确使用默认或横屏布局；平板两方向无裁切。

### T4：验证代码、资源选择与实际显示

1. 先运行 linearapp 的 Debug 编译与 lint，再运行已有单元测试。已有测试仅是辅助，不要求为布局练习新增测试代码。保留的 constraintapp 最终也应仍能构建。
2. 以 Android Studio Preview、模拟器运行、旋转和截图为主要验收方法；检查 ID/文字缺失、重叠及裁切。不新增 DOM 解析器或复杂测试框架。已有测试保留并按需运行。
3. 原来的“所有色块等宽”设备测试针对原布局；变体若改变排列方式，应按具体布局验证，不能机械套用原位置断言。
4. 执行下文设备矩阵，记录 AVD 名称、API、方向、选中的布局与截图；通过 Preview 的配置选择或模拟器显示设置确认适配区域。只有匹配异常时再检查实际 dp 配置，不为记录 dp 新增 Java 逻辑。
5. 在 Preview 分别选择默认、land 和 sw600dp，并实际运行对照；若匹配结果仍不清楚，使用布局检查工具。无需给根 View 增加调试标记或改变文案。
6. 在 `docs/device-verification.md` 记录实际执行结果；失败项目修复后复测，不将未执行项目标为通过。

验收：构建与现有测试通过，所有必测配置有真实运行证据。

### T5：整理说明与开发提交

1. 更新 README 为 Lab2，说明保留的 Lab1 布局、所选模块、变体路径、限定符设计与运行方式。
2. 记录测试设备和截图；区分 PDF 必须项与额外验证建议。
3. 检查至少三次真实开发提交，消息描述具体变化；不要仅拆分同一份完成品或用空提交凑数量。
4. 必要的最终修复和说明可形成第 4 次提交，如 `Document device verification and layout selection`。
5. 使用 `git status`、`git log --oneline`、`git remote -v` 确认工作区、提交记录和仓库独立性。

验收：可交接的完整工程，至少 3 个有意义的已完成阶段，最新修改已提交。

### T6：后续发布与 Canvas 提交

这是后续用户发布请求的范围；本次仅准备计划，不创建公共远程、不推送、不操作 Canvas。

1. 取得真实 Student ID 和 GitHub/GitLab 选择。
2. 创建全新的公开仓库，命名为 `<真实学号>-ResponsiveLayout2`。
3. 核对公开仓库包含的文件，确认本机配置、缓存、签名和凭据未被跟踪。
4. 连接新远程并推送所有阶段；不得复用 Practical 1 的仓库。
5. 在未登录视图确认公开可访问，核对布局文件、工程文件和至少 3 个提交可见。
6. 将实际仓库 URL 提交到 Canvas；只有看到提交成功状态后，才能记为已提交。

验收：正确命名的公开独立仓库与最新工程可访问，Canvas 接收正确链接。

## 5. 设备验收矩阵

以下目标按推荐的 `sw600dp` 方案描述；实际运行时记录系统报告的 dp 值。

| 配置 | 预期布局 | 核心验收 | 状态 |
| --- | --- | --- | --- |
| 手机竖屏，sw < 600 | `layout` | 与选中的 Lab1 原布局一致 | sw448dp 实际通过 |
| 手机横屏，sw < 600 | `layout-land` | 左右分区正确，所有元素可见 | sw448dp 实际通过 |
| 平板竖屏，sw >= 600 | `layout-sw600dp` | 大屏布局被加载，空间分配合理 | sw800dp 实际通过 |
| 平板横屏，sw >= 600 | `layout-sw600dp` 或组合变体 | 正确选择资源，按钮未裁切 | sw800dp 实际通过，仅需 sw600dp |
| 手机竖屏→横屏→竖屏 | 自动切换默认/land | 无崩溃、控件不缺失 | 实际通过 |
| 建议：阈值附近 599/600dp、窗口缩放 | 与当前配置匹配 | 无资源空缺，回退正确 | 可选 |
| 建议：较大字体与系统导航方式 | 与当前配置匹配 | 文字和按钮仍可读可用 | 可选 |

上次基线检查时，本机只有名为 `Pixel_10_Pro_XL` 的 AVD，尚未运行，没有连接设备；宽度适配需要另准备合适的平板 AVD。

## 6. 验证命令

在 Android Studio 终端中，确认当前目录是工程根目录；若终端 Java 环境未配置，使用下列单次 JAVA_HOME 设置。

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :constraintapp:assembleDebug :constraintapp:lintDebug :constraintapp:testDebugUnitTest
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:assembleDebug :linearapp:lintDebug :linearapp:testDebugUnitTest
```

模拟器启动并完全进入系统后，执行设备测试：

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:connectedDebugAndroidTest
```

设备测试需覆盖相应配置，且仍应检查截图。不要用构建成功代替设备验收。

## 7. 当前交接清单

- [x] 阅读教师 PDF 两页。
- [x] 分析两种 Lab1 实现与测试。
- [x] 完整复制项目文件并核对内容。
- [x] 初始化独立本地 Git 仓库，不继承旧远程。
- [x] 两个模块编译、lint、4 个原有单元测试通过。
- [x] 写好本计划、代理上下文和 CLI 使用指南。
- [x] 项目模型配置为 `gpt-6-sol` + `high`。
- [x] 实现横屏布局。
- [x] 实现宽度适配布局。
- [x] 模拟器视觉验收与设备测试（四配置实际通过）。
- [x] 至少三次真实开发提交。
- [ ] Android Studio Preview 恢复渲染：当前字体加载异常，不影响已通过的设备验收。
- [ ] 取得学号并新建公开远程仓库、推送。
- [ ] Canvas 提交链接。
