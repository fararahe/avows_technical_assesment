plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.avows.home"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    val debugName = "[D] ${libs.versions.appName.get()}"
    val releaseName = libs.versions.appName.get()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            resValue("string", "app_name", debugName)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        release {
            isMinifyEnabled = true
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    with(libs) {
        implementation(androidx.core.ktx)

        // Modules
        implementation(projects.core.configs)
        implementation(projects.core.navigation)
        implementation(projects.core.sharedModel)
        implementation(projects.common.utility)
        implementation(projects.common.sharedResource)
        implementation(projects.domain.auth)
        implementation(projects.domain.db)
        implementation(projects.domain.home)

        // App Compat + Material
        implementation(bundles.appcompat.material)

        // Testing
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)

        // View Components
        implementation(bundles.view.components)

        // Testing
        testImplementation(junit)
        androidTestImplementation(androidx.junit)
        androidTestImplementation(androidx.espresso.core)

        // Intuit
        implementation(bundles.intuit)

        // Hilt
        implementation(hilt.android)
        ksp(hilt.android.compiler)

        // Chucker
        debugImplementation(chucker)
        releaseImplementation(chucker.no.op)

        // Paging
        implementation(paging.ktx)

        // Room
        implementation(bundles.room)
        ksp(room.compiler)
    }
}