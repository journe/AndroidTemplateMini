import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.application)
	alias(libs.plugins.kotlin)
	alias(libs.plugins.ksp)
	alias(libs.plugins.hilt)
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
			storeFile = file("keystore.jks")
			storePassword = "asdasd"
			keyAlias = "key"
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

	kotlin {
		compilerOptions {
			// 替代jvmTarget = "17"
			jvmTarget.set(JvmTarget.JVM_17)
			// 替代freeCompilerArgs += "-Xenable-preview"（添加编译参数）
//			freeCompilerArgs.add("-Xenable-preview")
			// 可选：其他编译器参数（如启用所有警告）
//			allWarningsAsErrors.set(true)
		}
	}
	buildFeatures {
		viewBinding = true
		buildConfig = true
	}

}

dependencies {
	implementation(project(":lib_template"))

	implementation(libs.material)

	implementation(libs.hilt)
	ksp(libs.hilt.compiler)
}