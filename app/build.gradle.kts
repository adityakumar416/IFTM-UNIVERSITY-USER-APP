plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.iftm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.iftm"
        minSdk = 21
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx:20.3.5")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.test.espresso:espresso-idling-resource:3.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.webkit:webkit:1.7.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.rubensousa:decorator:2.1.0")


    //noinspection BomWithoutPlatform
    implementation("com.google.firebase:firebase-bom:32.3.1")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    implementation ("com.facebook.shimmer:shimmer:0.1.0@aar")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    androidTestImplementation ("com.android.support.test.espresso:espresso-core:3.0.2")

   // implementation("com.github.Drjacky:ImagePicker:1.8.4")





    implementation ("com.google.code.gson:gson:2.10")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    
    
}