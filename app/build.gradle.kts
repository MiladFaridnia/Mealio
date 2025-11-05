
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)

    // This plugin is now included in libs.plugins.kotlin.android,
    // but leaving it for now won't cause harm.
    // alias(libs.plugins.kotlin.compose) // This might be redundant depending on your AGP version
}

android {
    namespace = "com.faridnia.mealio"
    compileSdk = 36
    // Changed to a stable version, 36 is not a final release.

    defaultConfig {
        applicationId = "com.faridnia.mealio"
        minSdk = 24
        targetSdk = 34 // Match compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    // composeOptions might be needed depending on your setup
    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.5" // latest stable Compose compiler (Nov 2025)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


  /*  // 🏠 ROOM (Database)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler) // برای annotation processing

// 🧠 LIFECYCLE
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v286)

// ⚙️ COROUTINES
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

// 🎨 MATERIAL
    implementation(libs.material)

// 📜 RECYCLERVIEW
    implementation("androidx.recyclerview:recyclerview:1.4.0")

// 📐 CONSTRAINTLAYOUT
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

// 🔧 (اختیاری اما مفید برای ViewModel و SavedState)
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.9.4")*/

}
