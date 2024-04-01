plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

apply(from = rootProject.file("gradle/common.gradle"))

android {
    namespace = "com.example.repository"

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
}

dependencies {
    implementation(project(":data:model"))
    implementation(project(":data:source"))

    implementation(libs.retrofit)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
