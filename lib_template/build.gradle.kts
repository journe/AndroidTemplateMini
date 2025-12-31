import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.library)
	alias(libs.plugins.kotlin)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
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

	// 方式2：引用bundles（批量引入，简化代码）
	implementation(libs.bundles.navigation)
	implementation(libs.bundles.room)
	ksp(libs.room.compiler)

	implementation(libs.hilt)
	ksp(libs.hilt.compiler)

	implementation(libs.retrofit)
	implementation(libs.retrofit.gson)
	implementation(libs.retrofit.logging)

	implementation(libs.coil)

	implementation("com.github.journe:Android-logger:v2.2.1")

    implementation("com.guolindev.permissionx:permissionx:1.7.1")
	debugImplementation("com.guolindev.glance:glance:1.1.0")

	implementation(libs.xpopup)
	implementation(libs.utilcodex)

	implementation(libs.eventbus)
//    implementation("com.github.neo-turak:LiveEventBus:1.8.1")

	debugImplementation(libs.crashview)

	implementation(libs.shapeview)

	implementation(libs.dslTablayout)
	implementation(libs.dslTablayout.vp1)
	implementation(libs.dslTablayout.vp2)


}