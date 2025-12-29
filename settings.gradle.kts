pluginManagement {
	repositories {
		// 优先使用国内镜像
		maven { url = uri("https://maven.aliyun.com/repository/public/") }
		maven { url = uri("https://maven.aliyun.com/repository/google/") }
		maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin/") }

		mavenCentral()
		gradlePluginPortal()
		google {
			content {
				includeGroupByRegex("com\\.android.*")
				includeGroupByRegex("com\\.google.*")
				includeGroupByRegex("androidx.*")
			}
		}
	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		maven { url = uri("https://maven.aliyun.com/repository/public/") }
		maven { url = uri("https://maven.aliyun.com/repository/google/") }
		maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin/") }
		google()
		mavenCentral()
		maven("https://www.jitpack.io")
	}
}

rootProject.name = "template-mini"
include(":app")
include(":lib_template")
