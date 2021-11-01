plugins {
    id("com.android.application")
    kotlin("android")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sharedTodo"))
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    
    // Access composable APIs built on top of Activity
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")

    // Compose
    implementation("androidx.compose.runtime:runtime:1.0.4")
    implementation("androidx.compose.ui:ui:1.0.4")
    implementation("androidx.compose.foundation:foundation:1.0.4")
    implementation("androidx.compose.foundation:foundation-layout:1.0.4")
    implementation("androidx.compose.material:material:1.0.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.4")
    implementation("androidx.compose.ui:ui-tooling:1.0.4")
    implementation("com.google.android.material:compose-theme-adapter:1.0.4")
    
    // Koin
    implementation("io.insert-koin:koin-android:3.1.3")
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "com.example.kmmtodoapp.android"
        minSdkVersion(23)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures { 
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-beta01"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}