plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

apply(from = rootProject.file("gradle/common.gradle"))

android {
    namespace = "com.example.search"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlin.compiler.get()
    }
}

dependencies {
    implementation(project(":data:repository"))
    implementation(project(":data:model"))
    implementation(project(":feature:common"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.graphics)
    implementation(libs.compose.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.coil.compose)

    implementation(libs.threetenabp)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
}
