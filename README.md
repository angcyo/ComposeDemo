# 2025-03-31

[Jetpack Compose 使用入门](https://developer.android.google.cn/develop/ui/compose/documentation?hl=zh-cn)
[Kotlin Multiplatform](https://www.jetbrains.com/zh-cn/kotlin-multiplatform/)
[Compose Multiplatform](https://www.jetbrains.com/zh-cn/compose-multiplatform/)
[Kotlin Multiplatform Wizard](https://kmp.jetbrains.com/)

## project

### settings.gradle

```groovy
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeDemo"
include ':app'
```

### build.gradle

```groovy
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```

or

```groovy
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //id "com.android.application" version "8.9.1" apply false
    id "com.android.application" version libs.versions.agp apply false
    id "org.jetbrains.kotlin.android" version libs.versions.kotlin apply false
    id "org.jetbrains.kotlin.plugin.compose" version libs.versions.kotlin apply false
}
```

## app

### build.gradle

```groovy
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}
```

or

```groovy
plugins {
    id "com.android.application"
    id "org.jetbrains.kotlin.android"
    id "org.jetbrains.kotlin.plugin.compose"
}
```

`dependencies`

```groovy
dependencies {

    implementation libs.androidx.core.ktx
    implementation libs.androidx.lifecycle.runtime.ktx
    implementation libs.androidx.activity.compose         //implementation "androidx.activity:activity-compose:1.10.1"
    implementation platform(libs.androidx.compose.bom)    //implementation platform("androidx.compose:compose-bom:2024.09.00")
    implementation libs.androidx.ui                       //implementation "androidx.compose.ui:ui"
    implementation libs.androidx.ui.graphics              //implementation "androidx.compose.ui:ui-graphics"
    implementation libs.androidx.ui.tooling.preview       //implementation "androidx.compose.ui:ui-tooling-preview"
    implementation libs.androidx.material3                //implementation "androidx.compose.material3:material3"
    testImplementation libs.junit
    androidTestImplementation libs.androidx.junit
    androidTestImplementation libs.androidx.espresso.core
    androidTestImplementation platform(libs.androidx.compose.bom)
    androidTestImplementation libs.androidx.ui.test.junit4
    debugImplementation libs.androidx.ui.tooling
    debugImplementation libs.androidx.ui.test.manifest
}
```