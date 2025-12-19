plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // 1. ADD KAPT PLUGIN: Required for Room to generate database code
    kotlin("kapt")
}

android {
    namespace = "com.example.studentnotestracker"
    compileSdk = 34 // Using a common compile SDK version (adjust if necessary)

    defaultConfig {
        applicationId = "com.example.studentnotestracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Standard Compose and Core (Keep your existing libs.xxx aliases)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Define Room version
    val room_version = "2.6.1"

    // --- 2. ADD ROOM DATABASE (C, R, U, D) ---
    // Core Room Runtime
    implementation("androidx.room:room-runtime:$room_version")
    // Compiler (REQUIRED: MUST use 'kapt')
    kapt("androidx.room:room-compiler:$room_version")
    // Kotlin Extensions (for Coroutines/Flow)
    implementation("androidx.room:room-ktx:$room_version")

    // --- 3. ADD NAVIGATION & VIEWMODEL ---
    // Compose Navigation (For moving between screens)
    implementation("androidx.navigation:navigation-compose:2.7.5")
    // Compose ViewModel access (For using viewModel() in Composables)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Test dependencies (Keep your existing aliases)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}