plugins {
//  id 'com.android.application' version '8.7.2' apply false
//  id 'com.android.library' version '8.7.2' apply false
//  id 'org.jetbrains.kotlin.android' version '2.2.0' apply false
//  id 'com.google.dagger.hilt.android' version '2.57.2' apply false
//  id 'com.google.devtools.ksp' version '2.2.0-2.0.2' apply false

	alias(libs.plugins.application) apply false
	alias(libs.plugins.library) apply false
	alias(libs.plugins.kotlin) apply false
	alias(libs.plugins.ksp) apply false
	alias(libs.plugins.hilt) apply false
}
