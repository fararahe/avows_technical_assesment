plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.avows.configs"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        release {
            isMinifyEnabled = true
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
        // Modules
        implementation(projects.core.prefDataStore)
        implementation(projects.common.utility)

        implementation(androidx.core.ktx)

        // App Compat + Material
        implementation(bundles.appcompat.material)

        // Testing
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)

        // Okhttp
        implementation(bundles.okhttp)

        // Retrofit
        implementation(bundles.retrofit)

        // Moshi
        implementation(moshi.kotlin)
        ksp(moshi.kotlin.codegen)

        // Hilt
        implementation(hilt.android)
        ksp(hilt.android.compiler)
    }
}