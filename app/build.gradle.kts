plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.pokeapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pokeapi"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val retrofit_version = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    val hilt_version = "2.56.1"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    val lifecycle_version = "2.9.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    val material_version = "1.12.0"
    implementation("com.google.android.material:material:$material_version")
    val fragment_version = "1.8.6"
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    val picasso_version = "2.8"
    implementation("com.squareup.picasso:picasso:$picasso_version")
    val room_version = "2.7.1"
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-paging:$room_version")
    val paging_version = "3.3.6"
    implementation("androidx.paging:paging-runtime:$paging_version")
}

kapt {
    correctErrorTypes = true
}