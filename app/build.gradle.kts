plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.treehole"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.treehole"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
//    implementation(libs.core)
//    implementation(libs.espresso.intents)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.glide)
    testImplementation("org.robolectric:robolectric:4.10")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:4.2.0")
    testImplementation ("org.mockito:mockito-inline:4.2.0")

}


