plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "no.hanne.xkcd.core.repository"
    compileSdk = 33

    defaultConfig {
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1,licences/ASM,DEPENDENCIES}")
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    implementation(projects.core.models)
    implementation(projects.core.database)
    implementation(projects.core.exception)
    implementation(projects.core.text)
    implementation(projects.core.network)
    implementation(libs.androidx.coroutines)
    implementation(libs.androidx.datastore)
    implementation(libs.timber)
    implementation(libs.dagger.hilt.core)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt.core)
    implementation(platform(libs.arrow.bom))
    implementation(libs.arrow.core)
    implementation(libs.ktor.core)

    testImplementation(libs.kotlin.stdLib)
    testImplementation(libs.ktor.client.serialization)
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
}

kapt {
    correctErrorTypes = true
}
