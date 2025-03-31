# 2025-03-31

[Jetpack Compose 使用入门](https://developer.android.google.cn/develop/ui/compose/documentation?hl=zh-cn)

[Kotlin Multiplatform](https://www.jetbrains.com/zh-cn/kotlin-multiplatform/)

[Compose Multiplatform](https://www.jetbrains.com/zh-cn/compose-multiplatform/)

[Kotlin Multiplatform Wizard](https://kmp.jetbrains.com/)

[KMPDemo](https://github.com/angcyo/KMPDemo)

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

//--

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //id "com.android.application" version "8.9.1" apply false
    id "com.android.application" version libs.versions.agp apply false
    //id "org.jetbrains.kotlin.android" version "2.0.21" apply false
    id "org.jetbrains.kotlin.android" version libs.versions.kotlin apply false
    //id "org.jetbrains.kotlin.plugin.compose" version "2.0.21" apply false
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

//--

plugins {
    id "com.android.application"
    id "org.jetbrains.kotlin.android"
    id "org.jetbrains.kotlin.plugin.compose"
}
```

```groovy
android {
    ...
    buildFeatures {
        compose true
    }
    ...
}
```

`dependencies`

```groovy
dependencies {
    //https://mvnrepository.com/artifact/androidx.core/core-ktx
    implementation libs.androidx.core.ktx                 //implementation "androidx.core:core-ktx:1.15.0"
    //https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-runtime-ktx
    implementation libs.androidx.lifecycle.runtime.ktx    //implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.8.7"
    //https://mvnrepository.com/artifact/androidx.activity/activity-compose
    implementation libs.androidx.activity.compose         //implementation "androidx.activity:activity-compose:1.10.1"
    //https://mvnrepository.com/artifact/androidx.compose/compose-bom
    implementation platform(libs.androidx.compose.bom)    //implementation platform("androidx.compose:compose-bom:2024.09.00")
    //https://mvnrepository.com/artifact/androidx.compose.ui/ui 1.7.8
    implementation libs.androidx.ui                       //implementation "androidx.compose.ui:ui"
    //https://mvnrepository.com/artifact/androidx.compose.ui/ui-graphics 1.7.8
    implementation libs.androidx.ui.graphics              //implementation "androidx.compose.ui:ui-graphics"
    //https://mvnrepository.com/artifact/androidx.compose.ui/ui-tooling-preview 1.7.8
    implementation libs.androidx.ui.tooling.preview       //implementation "androidx.compose.ui:ui-tooling-preview"
    //https://mvnrepository.com/artifact/androidx.compose.material/material 1.7.8
    //implementation libs.androidx.material               //implementation "androidx.compose.material:material"
    //https://mvnrepository.com/artifact/androidx.compose.material3/material3 1.3.1
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