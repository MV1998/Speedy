plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(ksp-android)
    alias(libs.plugins.kspandroid)
}

android {
    namespace = "com.example.speedy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.speedy"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // viewmodel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    // viewmodel utilities for compose
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    //livedata
    implementation (libs.androidx.lifecycle.livedata.ktx)
    //lifecycles only (without ViewModel or LiveData)
    implementation (libs.androidx.lifecycle.runtime.ktx.v283)
    //Lifecycle utilities for compose
    implementation (libs.androidx.lifecycle.runtime.compose)
    //Saved state for module for viewmodel
    implementation (libs.androidx.lifecycle.lifecycle.viewmodel.savedstate)
    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.androidx.animation)
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.7.2")


    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
   // implementation("com.squareup.okhttp3:okhttp:4.10.0")
//
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

}