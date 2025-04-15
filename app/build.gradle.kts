plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.vuapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vuapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    testImplementation("io.mockk:mockk:1.13.7")


    implementation("com.google.android.material:material:1.11.0")



    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    //
    implementation("androidx.activity:activity-ktx:1.8.2")

    // Coroutine (Optional)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")



        //  Unit Testing Libraries
        testImplementation("junit:junit:4.13.2")
        testImplementation("org.mockito:mockito-core:4.8.0")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
        testImplementation("androidx.arch.core:core-testing:2.2.0")
    }

