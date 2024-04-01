plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

apply(from = rootProject.file("gradle/common.gradle"))

android {
    namespace = "com.example.source"

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

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.threetenabp)
}
