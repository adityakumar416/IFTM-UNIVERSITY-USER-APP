pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter() //add jcenter
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() //add jcenter
        maven { url = uri("https://www.jitpack.io" )}
            maven { url = uri("https://maven.google.com") }

    }
}

rootProject.name = "IFTM"
include(":app")
 