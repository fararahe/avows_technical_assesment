plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.avows.data.db"
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
}

dependencies {
    with(libs) {
        implementation(androidx.core.ktx)

        // App Compat + Material
        implementation(bundles.appcompat.material)

        // Module
        implementation(projects.core.configs)
        implementation(projects.core.navigation)
        implementation(projects.core.prefDataStore)
        implementation(projects.core.sharedModel)
        implementation(projects.common.utility)
        implementation(projects.domain.db)

        // Testing
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)

        // Hilt
        implementation(hilt.android)
        ksp(hilt.android.compiler)

        // Room
        implementation(bundles.room)
        ksp(room.compiler)
    }
}