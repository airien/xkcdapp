plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "no.hanne.xkcd.core.database"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.kotlin.stdLib)
    implementation(projects.core.models)
    implementation(libs.jetpack.room.ktx)
    implementation(libs.kotlinx.serialization.json)
    kapt(libs.jetpack.room.compiler)
}
