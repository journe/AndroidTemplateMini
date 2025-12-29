plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.template.mini"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }


    signingConfigs {
        create("release") {
            // 密钥库文件路径
            storeFile = file("keystore.jks")
            // 密钥库密码
            storePassword = "asdasd"
            // 密钥别名
            keyAlias = "key"
            // 密钥密码
            keyPassword = "asdasd"

        }

    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "VERSION_TYPE", "\"VERSION_STATUS_ALPHA\"")
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isMinifyEnabled = true
            buildConfigField("String", "VERSION_TYPE", "\"VERSION_STATUS_RELEASE\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    implementation(project(":lib_template"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.fragment:fragment-ktx:1.8.6")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("com.google.code.gson:gson:2.10")
    implementation("androidx.collection:collection-ktx:1.4.0")

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.activity)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.constraintlayout)

    implementation("com.google.auto.service:auto-service:1.0")
    kapt("com.google.auto.service:auto-service-annotations:1.0")

}
kapt {
    correctErrorTypes = true
}