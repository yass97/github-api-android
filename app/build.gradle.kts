import java.util.Properties

plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

apply(from = rootProject.file("gradle/common.gradle"))

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "com.example.githubapi"

    defaultConfig {
        applicationId = "com.example.githubapi"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        buildConfigField("String", "API_BASE_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "GITHUB_API_TOKEN", "\"${properties.getProperty("GITHUB_API_TOKEN")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }

        debug {
            applicationIdSuffix = ".debug"
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data:source"))
    implementation(project(":data:repository"))
    implementation(project(":feature:search"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)

    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.interceptor)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.threetenabp)
}
