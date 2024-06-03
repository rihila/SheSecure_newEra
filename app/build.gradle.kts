import com.android.build.gradle.internal.cxx.caching.decodeObjectFileCacheEvent
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { input ->
        localProperties.load(input)
    }
}

val apiKey: String = localProperties.getProperty("API_KEY") ?: ""


plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}



android {
    namespace = "com.example.shesecure10"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shesecure10"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        android.buildFeatures.buildConfig=true
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

        





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
    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "34.0.0"
}


dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")


    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    dependencies {
        // ... other androidx dependencies

        // add the dependency for the Google AI client SDK for Android
        implementation("com.google.ai.client.generativeai:generativeai:0.2.0")

        // Required for one-shot operations (to use `ListenableFuture` from Guava Android)
        implementation("com.google.guava:guava:31.0.1-android")

        // Required for streaming operations (to use `Publisher` from Reactive Streams)
        implementation("org.reactivestreams:reactive-streams:1.0.4")
    }
}
apply(plugin = "com.google.gms.google-services")