plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.template"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "VERSION_TYPE", "\"VERSION_STATUS_ALPHA\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "VERSION_TYPE", "\"VERSION_STATUS_RELEASE\"")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation("androidx.fragment:fragment-ktx:1.8.6")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("com.google.code.gson:gson:2.10")
    implementation("androidx.collection:collection-ktx:1.4.0")

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)


    implementation("com.google.auto.service:auto-service:1.0")
    kapt("com.google.auto.service:auto-service-annotations:1.0")

    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("io.coil-kt:coil:2.7.0")

    implementation("com.github.journe:Android-logger:v2.2.1")

//    implementation("com.guolindev.permissionx:permissionx:1.7.1")
//	debugImplementation("com.guolindev.glance:glance:1.1.0")

    implementation("com.github.li-xiaojun:XPopup:2.10.0")
    implementation("com.blankj:utilcodex:1.31.1")

    implementation("io.github.jeremyliao:live-event-bus-x:1.8.0")
//    implementation("com.github.neo-turak:LiveEventBus:1.8.1")

    debugImplementation("cat.ereza:customactivityoncrash:2.4.0")
    implementation("com.github.getActivity:ShapeView:9.2")

    implementation("com.github.angcyo.DslTablayout:TabLayout:3.7.2")
    implementation("com.github.angcyo.DslTablayout:ViewPager1Delegate:3.7.2")
    implementation("com.github.angcyo.DslTablayout:ViewPager2Delegate:3.7.2")

}