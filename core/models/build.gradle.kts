plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
    kotlin("android")
}

android {
    namespace = "no.hanne.xkcd.core.models"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    implementation(libs.kotlin.stdLib)
    implementation(libs.kotlin.parcelize)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.jetpack.room.ktx)
}