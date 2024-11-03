pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TechnicalAssessment"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":common:utility")
include(":core:configs")
include(":core:navigation")
include(":features:auth")
include(":features:home")
include(":core:pref-data-store")
include(":data:auth")
include(":data:home")
include(":domain:home")
include(":domain:auth")
include(":core:shared-model")
include(":common:shared-resource")
include(":data:db")
include(":domain:db")
