plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.avows.technicalassessment"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    val debugName = "[D] ${libs.versions.appName.get()}"
    val releaseName = libs.versions.appName.get()

    defaultConfig {
        applicationId = "com.avows.technicalassessment"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            resValue("string", "app_name", debugName)
            applicationIdSuffix = ".debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            resValue("string", "app_name", releaseName)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    with(libs) {
        implementation(androidx.core.ktx)

        // App Compat + Material
        implementation(bundles.appcompat.material)

        // Module Core
        implementation(projects.core.configs)
        implementation(projects.core.navigation)
        implementation(projects.core.prefDataStore)

        // Module Common
        implementation(projects.common.utility)
        implementation(projects.common.sharedResource)

        // Module Data
        implementation(projects.data.auth)
        implementation(projects.data.db)
        implementation(projects.data.home)

        // Module Domain
        implementation(projects.domain.auth)
        implementation(projects.domain.db)
        implementation(projects.domain.home)

        // Module Auth
        implementation(projects.features.auth)
        implementation(projects.features.home)

        // View Components
        implementation(bundles.view.components)

        // Logging
        implementation(timber)

        // Testing
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)

        // Intuit SDP SSP
        implementation(bundles.intuit)

        // Hilt
        implementation(hilt.android)
        ksp(hilt.android.compiler)

        // Coroutines
        implementation(bundles.coroutine)

        // OkHttp
        implementation(bundles.okhttp)

        // Retrofit
        implementation(bundles.retrofit)

        // Moshi
        implementation(moshi.kotlin)
        ksp(moshi.kotlin.codegen)

        // Chucker
        debugImplementation(chucker)
        releaseImplementation(chucker.no.op)

        // Lottie
        implementation(lottie)

        // Room
        implementation(bundles.room)
        ksp(room.compiler)
    }
}