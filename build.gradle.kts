plugins {
    `kotlin-dsl`
    kotlin("android") version "1.7.20" apply false
    kotlin("plugin.serialization") version "1.5.31" apply false
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("com.diffplug.spotless") version "5.17.1"
    id("com.android.library") version "7.3.1" apply false
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0-alpha04")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}

subprojects {
    plugins.withType<com.android.build.gradle.AppPlugin> {
        configure<com.android.build.gradle.AppExtension> {
            packagingOptions {
                resources.pickFirsts.add("META-INF/gradle/incremental.annotation.processors")
            }
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
    }
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        toolVersion = "1.18.1"
        baseline = file("detekt-baseline.xml")
        config = files("${rootProject.projectDir}/.detekt/rules.yml")
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        ratchetFrom = "origin/main"

        val kotlinUserData = mapOf(
            "end_of_line" to "lf",
            "indent_size" to "4",
            "indent_style" to "space",
            "insert_final_newline" to "true",
            "max_line_length" to "120",
            "ij_kotlin_allow_trailing_comma" to "true",
            "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
        )

        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")

            ktlint("0.41.0").userData(kotlinUserData)
        }
    }
}
