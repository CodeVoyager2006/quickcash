plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.development_01"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.development_01"
        minSdk = 30
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")

    // Core Android (XML-based)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.core)
    implementation(libs.androidx.uiautomator)

    // Local unit testing (JUnit + Robolectric)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    // Instrumented Android tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.androidx.runner)
}