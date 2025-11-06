
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")


    /*
    alias(libs.plugins.hilt2) version "2.51.1" apply false
    alias(libs.plugins.ksp) version "2.0.21-1.0.27" apply false
*/
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
//    implementation(libs.androidx.lifecycle.runtime.ktx)
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
// Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // For Kotlin Coroutines support
    ksp(libs.androidx.room.compiler) // Use "ksp" for the annotation processor

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Lifecycle ViewModel integration for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)


}
