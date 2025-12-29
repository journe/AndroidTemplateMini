import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("com.google.dagger.hilt.android")
	id("com.google.devtools.ksp")
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

	kotlin {
		compilerOptions {
			jvmTarget.set(JvmTarget.JVM_17)
		}
	}

	buildFeatures {
		viewBinding = true
		buildConfig = true
	}
}

dependencies {

	implementation(libs.material)
	implementation(libs.androidx.activity)


	implementation(libs.androidx.navigation.fragment.ktx)
	implementation(libs.androidx.navigation.ui.ktx)
	implementation(libs.androidx.lifecycle.livedata.ktx)
	implementation(libs.androidx.lifecycle.viewmodel.ktx)


	implementation(libs.hilt)
	ksp(libs.hilt.compiler)


	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
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