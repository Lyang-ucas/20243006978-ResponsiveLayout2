# Lab1 课程范围审查与 Lab2 基础实现边界

审查日期：2026-09-26。
本文记录最初准备阶段的课程范围审查；后续已按此范围完成本地实现，最新状态见 `resume-checkpoint.md` 和 `device-verification.md`。文中“尚未实施”指审查当时。
用户要求：优先课件讲过的基础方法；允许有实际用途、与已学知识紧密相关且短时间可理解的小延伸。
审查依据是提供的 001-008 课件及当前 Lab1 全部应用源码、布局、资源、构建配置和测试。不推断课堂上是否还讲过额外内容。

## 1. 结论

Lab1 的运行功能很简单：一个 Java Activity 加载一个 XML 界面，没有数据库、网络、业务架构或动态布局引擎。主要实现符合课件的 Java + XML 路线。

- `linearapp` 的主要布局方法在课件中直接讲过，适合作为最基础的 Lab2 起点。
- `constraintapp` 的布局类型和基础约束在课件中讲过，但其加权约束链及 chainStyle 未在提供的课件中直接展示；属于可接受的近邻知识延伸，学习负担高于 LinearLayout 权重。
- 按钮背景、图标和系统栏样式使用了少量额外 XML 资源知识，都是独立、局部的小延伸，不构成复杂架构。
- DOM/XML 解析测试及 ActivityScenario 设备测试比本次课程布局任务需要的手动验证复杂；它们属于开发辅助代码，不参与应用正常运行。保留已有代码即可，Lab2 不需要继续扩展这些机制。

本次审查没有修改 Lab1 或 Lab2 的应用源码、资源、测试及构建配置。已更新 Lab2 计划、代理上下文及 CLI 指南；默认改用 `linearapp`。Lab2 新布局尚未实施。

## 2. 课件与实际知识点对照

页码使用 PDF 实际页码，从 1 开始。

| 来源 | 本次相关内容 | 如何应用到任务 |
| --- | --- | --- |
| 001，第 4、9-10 页 | 假定已有 Java/C# 面向对象基础；课程涵盖 Android、测试与版本控制 | 基础 Java 继承、方法、数组不自动视为超纲；测试主题出现不表示具体测试 API 已教 |
| 002，第 4、19、23-25 页 | Java + XML，SDK/API、Android Studio、Gradle、模拟器和 lint | 延续现有工具链；工具链版本较新不等于实现方法复杂 |
| 003，第 10、15、19-20 页 | Empty Views Activity、Java、模拟器运行、Git Add/Commit/Push | 在 Android Studio 操作并分阶段提交，不增加 CI/CD 或发布自动化 |
| 004，第 6、9、12、16-19、21-25 页 | Java/XML 分工，资源目录，View/ViewGroup，LinearLayout、ConstraintLayout，TextView/Button | Lab1 原 Activity 和控件保留 |
| 005，第 6、10-14、16 页 | 基础约束、LinearLayout weights、0dp、wrap_content/match_parent、dp/sp | 首选方向与权重分配；字号与间距用正确单位 |
| 006，第 13-19、21-24、28-31 页 | 同名布局变体，land、sw600dp、w600dp；横竖布局方向变化；组合限定符 | 新增 land 和一个宽度变体；平板组合变体仅按实际需要添加 |
| 006，第 25-36 页 | Fragments 用于减少布局重复与组合界面 | 本任务只有少量静态布局，默认直接保留三个 XML，无需增加 Fragment 类和生命周期知识 |
| 007，第 4-10、17-19、38 页 | 可读性、需求、代码质量、审查 AI 生成代码 | 代码要能解释；每个新增属性有具体用途 |
| 008，第 10、15、33、39、48-53 页 | 分解与抽象的取舍，MVC/MVVM，按需求选择架构 | 本布局没有业务状态，不为使用模式而增加 Model/Repository/ViewModel |

006 第 28-30 页的演示将 vertical 容器换成 horizontal，并对相应方向使用 0dp + 权重。Lab2 应以这一思路做区域重排，而不是在 Java 中检测屏幕后手动改位置。

## 3. Lab1 按文件审查

路径相对 Lab1 工程根目录；两模块同类资源作用相同。

| 实际使用 | 文件 | 分类与理由 | 后续处理 |
| --- | --- | --- | --- |
| Activity、onCreate、setContentView | 两模块 `src/main/java/.../MainActivity.java` | 基础；004 第 6 页说明 Java 功能与 XML UI 分工，008 第 47 页展示相同加载方式 | 保留，不增加业务方法 |
| 嵌套 LinearLayout、horizontal/vertical | `linearapp/src/main/res/layout/activity_main.xml` | 直接课内；004 第 17、30 页 | Lab2 首选 |
| 0dp + layout_weight | 同上 | 直接课内；005 第 11 页、006 第 28-30 页 | 保留；横向加权置宽为 0dp，纵向加权置高为 0dp |
| weightSum 及小数权重 | 同上 | 紧邻延伸；weightSum 是总权重，小数仍是比例分配，没有新的算法 | 原布局保留；新布局优先简单比例，避免不必要的嵌套权重 |
| ConstraintLayout 基础边缘约束 | `constraintapp/src/main/res/layout/activity_main.xml` | 直接课内；004 第 19 页、005 第 10 页 | 保留参考 |
| 多项互相连接的 chains、chainStyle、Horizontal/Vertical_weight | 同上 | 提供的课件未直接展示。能从“约束连接 + 比例分配”理解，属于近邻延伸，但属性较多 | 默认不扩展；如以后使用，先解释每条链的组成与比例 |
| 字符串、颜色、尺寸资源引用 | 两模块 `res/values/*.xml` 与布局 | 资源目录在004第9页，dp/sp在005第14页；具体 @string/@color/@dimen 写法是基础关联知识 | 保留，不把数值大量写死在 Java 中 |
| start/end、gravity、margin、padding、minHeight | 两模块布局 | 控件摆放和尺寸相关属性，紧邻课程布局知识 | 可使用必要属性；不强制把 start/end 改回 left/right |
| shape/selector、pressed 状态、圆角 | 两模块 `res/drawable/button_background.xml` | 004 第 9 页提到 XML drawable；具体 selector/shape 未直接展开。少量标签即可解释颜色与圆角 | 可保留原样；Lab2 不再扩展复杂动画或样式系统 |
| vector/path 图标 | 两模块 `res/drawable/ic_launcher.xml` | 路径绘制语法未直接教；是启动图标的静态资源，与适配方法无关 | 原资源可保留；不作为 Lab2 新知识目标 |
| Theme.Material.Light.NoActionBar、系统栏颜色 | 两模块 `res/values/styles.xml` | 基础主题配置的近邻延伸；用于与目标界面外观一致 | 保留，避免主题重构 |
| values-v27、windowLightNavigationBar | 两模块 `res/values-v27/styles.xml` | API 版本资源限定符是宽度限定符的近邻；002第19页讲过 API 兼容性 | 允许保留，需要时简短说明“较新版本使用替代样式” |
| fitsSystemWindows | 两模块默认布局 | 与系统栏空间有关的近邻属性，课件未直接展示 | 原样保留；只有实测遮挡时做最小修正，不预先引入 WindowInsets 框架 |
| tools:ignore | 两模块布局 | 编辑器/lint 辅助，不是运行逻辑；应理解具体提示再决定 | 原始保留；新布局不要批量复制忽略规则来掩盖问题 |
| AndroidX ConstraintLayout 依赖 | `constraintapp/build.gradle` | 006 第 28-30 页直接使用 androidx.constraintlayout；旧课件中的 android.support 名称不需逐字照搬 | 保留，不降级到旧 support 包 |
| JUnit、Files、正则、DOM/XML 解析 | 两模块 `src/test/java/.../*LayoutContractTest.java` | 课程提过测试，但没有直接教授该检查方法；与布局本体相比额外知识较多 | 保留辅助验证，默认不新增相似解析代码 |
| ActivityScenario、AndroidJUnit4、lambda、尺寸断言 | 两模块 `src/androidTest/java/.../MainActivityTest.java` | Java lambda 是近邻语言知识；设备测试 API 未在提供课件直接展示 | 不作为完成 Lab2 必须掌握的实现方法；已有测试可运行，主要靠模拟器检查 |
| Gradle Wrapper、namespace/applicationId、minSdk/targetSdk | 根目录与模块构建文件 | Android 工程配置，002/003已介绍相关工具与 API 概念 | 保持可构建配置，不人为升级或降级 |

## 4. 两点容易误判的地方

**使用权重的小数不是“高级算法”。** 原布局有 1.0、2.0、2.6 等值，只是表达区域的相对比例。weightSum 等于总权重时通常不是必须，但已有原布局无需为追求基础而全部删除。

**课件出现术语不等于必须在作业里实现。** 002 的真实应用示例提到 Compose/Room/Flow，008 有 MVC/MVVM；本任务要展示基础响应式布局，不需要这些架构或库。006 虽讲了 Fragments，少量重复 XML 对当前学习阶段可接受。

003 第 10 页示例最低 API 是 Android 7.0（24），原工程为 23；这是配置差异，不是超纲功能。若详细任务书强制最低 API 再调整，否则本次不改变可运行基线。

## 5. Lab2 最基础实施方案

1. 原 `linearapp/src/main/res/layout/activity_main.xml` 保留不动。
2. 复制为 `layout-land/activity_main.xml`，主要调整容器方向，将原先上下区域改为左右区域。保留 TextView/Button、四个色块阅读顺序和已有资源。
3. 再复制为 `layout-sw600dp/activity_main.xml`，用同样的方向、权重与简单边距调整适配平板。它应有合理的布局区别，而不是只新增目录。
4. 不修改 Activity 的布局加载代码。Android 按资源目录自动选用同名 activity_main.xml。
5. Preview 选手机/平板和横竖屏，再实际运行模拟器、旋转、检查文字和按钮；保存几张能说明适配结果的截图。
6. 基线、横屏、宽度适配分别形成真实提交；README 简要说明三个资源目录的用途。

需要第四个 `layout-sw600dp-land` 时可以增加，因为006第31页直接展示过；应先验证前三个是否已经足够。

### 小延伸的接受标准

新增方法应能用两三句话讲清：解决什么具体问题、与已学知识如何相连、修改了哪几个属性或少量代码。
单独调整资源、按钮外观、系统栏兼容通常符合这一标准；为了三个静态布局引入多层架构或测试框架不符合当前任务需要。

## 6. 本次更新与验证

- 已将上述范围写入项目 `AGENTS.md`，使后续 CLI 会话能读取。
- 已修订 `LAB2_TASK_PLAN.md` 与 `CODEX_START.md`，默认模块改为 linearapp，并改用当前目录 `Moving computing  lab`。
- 已修复启动脚本：PATH 无 codex 时，使用本机已安装应用内的 CLI。模型仍明确为 gpt-6-sol + high。
- 脚本检查只运行版本/帮助，不发起模型请求。
- 此次不重跑 Android 构建：应用源码、资源、测试和构建配置均未更改；之前的基线检查仍有效，目录迁移后首次在 Android Studio 使用时需重新同步。
- `docs/lab1-copy-manifest.json` 与基线记录是首次复制时的历史证据，旧路径反映当时的位置，不要把它当作当前入口。

## 7. 参考课件

以下文件全部只读，本次未修改或重新导出 PDF。

- [001 Course Introduction](</Users/lucas/mobile computing and software structure/001_Course Introduction.pdf>)
- [002 Introduction to Mobile Computing and Android](</Users/lucas/mobile computing and software structure/002_Introduction to Mobile Computing and Android.pdf>)
- [003 Android Studio and Git](</Users/lucas/mobile computing and software structure/003_Android Studio and Git.pdf>)
- [004 Android User Interface](</Users/lucas/mobile computing and software structure/004_Android User Interface.pdf>)
- [005 Android Responsive Layout 1](</Users/lucas/mobile computing and software structure/005_Android Responsive Layout 1.pdf>)
- [006 Android Responsive Layout 2](</Users/lucas/mobile computing and software structure/006_Android Responsive Layout 2.pdf>)
- [007 Introduction to Software Architecture 1](</Users/lucas/mobile computing and software structure/007_Introduction to Software Architecture 1.pdf>)
- [008 Introduction to Software Architecture 2](</Users/lucas/mobile computing and software structure/008_Introduction to Software Architecture 2.pdf>)
