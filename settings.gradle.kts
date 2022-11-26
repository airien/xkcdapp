pluginManagement {
    repositories {
        gradlePluginPortal() // <- this
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "xkcd"

include(":app")
include(":core:database")
include(":core:models")
include(":core:network")
include(":core:repository")
include(":core:ui")
include(":core:util")
include(":core:text")
include(":core:exception")

buildCache {
    local {
        isEnabled = true
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}