pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GithubApi"
include(":app")
include(":data")
include(":data:source")
include(":data:model")
include(":data:repository")
include(":feature")
include(":feature:common")
include(":feature:search")
