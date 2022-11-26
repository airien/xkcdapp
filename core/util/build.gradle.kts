plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "no.hanne.xkcd.core.util"
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
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn "
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    implementation(projects.core.models)
    implementation(projects.core.text)
    implementation(libs.androidx.core)
    implementation(libs.androidx.coroutines)
    implementation(libs.timber)
}
