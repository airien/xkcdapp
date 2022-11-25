plugins {
    id("com.palantir.git-version") version "0.11.0"
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "no.hanne.xkcd.core.network"
    compileSdk = 33

    defaultConfig {
        namespace = "no.hanne.xkcd.core.network"
        minSdk = 26
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {}
        release {
            isMinifyEnabled = false
        }
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
    implementation(projects.core.util)
    implementation(projects.core.models)
    implementation(projects.core.exception)
    implementation(libs.ktor.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.serialization)
    implementation(libs.kotlin.stdLib)
    implementation(libs.timber)
    implementation(libs.androidx.core)
    implementation(platform(libs.arrow.bom))
    implementation(libs.arrow.core)
    implementation(libs.dagger.hilt.core)
    implementation(libs.androidx.coroutinesPlayServices)

    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.testing.mockk)
    testImplementation(libs.testing.junit4)
    testImplementation(libs.testing.compose.ui.test)
    testImplementation(libs.testing.compose.ui.manifest)
    testImplementation(libs.testing.robolectric)
    testImplementation(libs.bundles.androidx.test)

    testImplementation(libs.testing.hilt)
    testImplementation(libs.testing.ktor)
    kaptTest(libs.dagger.hilt.compiler)
    testAnnotationProcessor(libs.dagger.hilt.compiler)

    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.androidx.espresso.core)
}