rootProject.name = "clique-reservations"
pluginManagement {
    val artifactDownloadUrl: String by settings
    // val artifactUsername: String by settings
    // val artifactPassword: String by settings

    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings

    val gradleQoomonGitVersioning: String by settings
    val gradleOwaspDependencyCheck: String by settings
    val gradleGitPropertiesGenerator: String by settings
    val gradleSonarQube: String by settings
    val gradleBenManesVersion: String by settings
    val gradleBuildTimeTracker: String by settings
    val gradleDependencyLicenseReport: String by settings
    val gradleTestLogger: String by settings
    val gradleOsackyDoctor: String by settings

    repositories {
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven {
            url = uri(artifactDownloadUrl)
            // credentials {
            //	username = artifactUsername
            //	password = artifactPassword
            // }
        }
    }
    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion

        id("me.qoomon.git-versioning") version gradleQoomonGitVersioning
        id("org.owasp.dependencycheck") version gradleOwaspDependencyCheck
        id("com.gorylenko.gradle-git-properties") version gradleGitPropertiesGenerator
        id("com.github.ben-manes.versions") version gradleBenManesVersion
        id("net.rdrei.android.buildtimetracker") version gradleBuildTimeTracker
        id("com.github.jk1.dependency-license-report") version gradleDependencyLicenseReport
        id("com.adarshr.test-logger") version gradleTestLogger
        id("com.osacky.doctor") version gradleOsackyDoctor
    }
}

val projectName: String by settings
rootProject.name = projectName

include(":model")
include(":service")
include(":endpoint")
include(":application")
