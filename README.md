# Responsive Layout 2

Practical 2 extends the selected Practical 1 `linearapp` using Java and Android Views/XML.
The original default layout, Java Activity, shared resources and existing tests remain unchanged.
`constraintapp` remains the complete Practical 1 reference application.

## Layouts

| Resource directory in `linearapp/src/main/res` | When used | Layout |
| --- | --- | --- |
| `layout` | Phone portrait | Original Practical 1 layout |
| `layout-land` | Phone landscape | Header above a horizontal 2:1 split; content on the left, message and buttons on the right |
| `layout-sw600dp` | Smallest available width at least 600dp, in either orientation | Vertical regions with height weights 1:3:1:1 and 32dp side margins |

All three files are named `activity_main.xml`, share the original key View IDs and strings,
and are loaded by the existing `setContentView(R.layout.activity_main)`.
The tablet variant fits both orientations; no fourth combined variant is needed for the tested tablet.
The original “Lab 1” and “Responsive Layout 1” text is intentionally retained.

Android selects matching resources automatically; `sw600dp` takes precedence over `land` on a
matching tablet. See [Android alternative resource documentation](https://developer.android.com/topic/architecture/views/resources/providing-resources-views).

## Course methods used

| Course topic | Use in this project |
| --- | --- |
| 004: XML, View/ViewGroup, LinearLayout, TextView/Button | Same controls and nested LinearLayout containers |
| 005: weights and `0dp` | Horizontal weighted children have width `0dp`; vertical weighted children have height `0dp` |
| 005: `match_parent`, `wrap_content`, dp/sp | Fill available regions; keep landscape header/buttons at content height; use dp for margins and sp for text |
| 006: `land`, `sw600dp`, same resource names | Reorder phone landscape regions and automatically choose the tablet layout |

The landscape header/title and button row use `wrap_content` so short screens retain space for
the main content. Tablet height weights allocate half the available height to the content panel;
inside it, title and color row use a 1:2 ratio. Each color block keeps weight 1 and width `0dp`.
Existing color/string/dimension references and the simple button background are reused.

## Open and run

Open this directory in Android Studio (the directory containing `settings.gradle` and `gradlew`),
sync Gradle, select `linearapp`, and run on a phone or tablet. The inherited Gradle project name
and application IDs are retained: `edu.practical1.linearapp` / `edu.practical1.constraintapp`.
Installing these apps can replace the same IDs already present on an emulator.

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:assembleDebug :linearapp:lintDebug :linearapp:testDebugUnitTest :constraintapp:assembleDebug :constraintapp:lintDebug :constraintapp:testDebugUnitTest --console=plain
```

With the chosen emulator running in the desired orientation:

```sh
JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew :linearapp:connectedDebugAndroidTest --console=plain
```

The device test runner removes the app afterwards; run it again from Android Studio before
inspecting the screen. No new test framework or application architecture was added.

## Verification

Phone (`sw448dp`) and tablet (`sw800dp`) portrait/landscape were actually run and inspected.
All four configurations passed the existing device test; rotations and five screenshots are
recorded in [device verification](docs/device-verification.md). Both modules build and pass lint;
the four existing unit tests remain cached passes from the unchanged baseline.

Android Studio Preview currently fails while Layoutlib loads a font (`assetStream is null`).
Preview is not marked as passed. The required device visual checks have completed.
Lint has non-blocking nested-weight, button-style/order and dependency-update warnings.
Optional boundary-width, large-font and alternate-navigation checks were not run.

## Local development history and submission

The baseline, phone landscape and tablet width adaptation were developed and verified in three
separate commits. Documentation is recorded in a further commit; inspect `git log --oneline`.

The teacher requires a **new public** GitHub/GitLab repository named
`<Student ID>-ResponsiveLayout2`, the complete project, at least three descriptive commits,
and submission of its URL through Canvas. These publishing steps remain pending: the real
student ID, chosen platform and publishing instruction have not been provided.

For the latest handoff state, read [resume checkpoint](docs/resume-checkpoint.md).
