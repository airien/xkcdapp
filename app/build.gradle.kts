plugins {
    id("com.android.application")
    kotlin("plugin.serialization")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val composeVersion = "1.4.0-alpha01"
val kotlinVersion = "1.7.20"
val ktorVersion = "2.0.2"
val roomVersion = "2.4.2"

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "no.hanne.xkcd"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
        }
    }

    val boolean = "boolean"
    val trueValue = "true"
    val falseValue = "false"
    val endpoint = "endpoint"
    val devServerFlavor = "devServer"
    val prodServerFlavor = "prodServer"

    flavorDimensions.add(endpoint)
    productFlavors {
        create(devServerFlavor) {
            manifestPlaceholders["baseUrl"] = ""
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
            buildConfigField(boolean, "ENABLE_LOGGING", trueValue)
            buildConfigField("String", "XKCD_JSON_URL", "\"https://xkcd.com/\"")
            buildConfigField("String", "XKCD_EXPLAIN_URL", "\"https://www.explainxkcd.com/wiki/index.php/\"")
            buildConfigField("String", "TYPESENSE_URL", "\"https://qtg5aekc2iosjh93p.a1.typesense.net/multi_search?use_cache=true\"")
            buildConfigField("String", "TYPESENSE_API_KEY", "\"8hLCPSQTYcBuK29zY5q6Xhin7ONxHy99\"")
        }

        create(prodServerFlavor) {
            manifestPlaceholders["baseUrl"] = ""
            buildConfigField(boolean, "ENABLE_LOGGING", trueValue)
            buildConfigField("String", "XKCD_JSON_URL", "\"https://xkcd.com/\"")
            buildConfigField("String", "XKCD_EXPLAIN_URL", "\"https://www.explainxkcd.com/wiki/index.php/\"")
            buildConfigField("String", "TYPESENSE_URL", "\"https://qtg5aekc2iosjh93p.a1.typesense.net/multi_search?use_cache=true\"")
            buildConfigField("String", "TYPESENSE_API_KEY", "\"8hLCPSQTYcBuK29zY5q6Xhin7ONxHy99\"")
        }
    }

    testOptions.unitTests.isIncludeAndroidResources = true
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1,licences/ASM,DEPENDENCIES}")
    }
}

dependencies {
    testImplementation("junit:junit:4.12")
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    implementation(projects.core.models)
    implementation(projects.core.network)
    implementation(projects.core.repository)
    implementation(projects.core.database)
    implementation(projects.core.exception)
    implementation(projects.core.ui)
    implementation(projects.core.util)
    implementation(libs.ktor.core)
    implementation(libs.ktor.client.serialization)
    implementation(platform(libs.arrow.bom))
    implementation(libs.arrow.core)
    implementation(libs.androidx.core)
    implementation(libs.jetpack.hilt.navigation.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.toolingpreview)
    implementation(libs.androidx.activity)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.coil.landscapist)
    implementation(libs.dagger.hilt.core)
    implementation(libs.timber)
    implementation(libs.jetpack.room.ktx)
    implementation(libs.androidx.coroutines)
    kapt(libs.dagger.hilt.compiler)
}
