import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.osacky.doctor.DoctorExtension
import net.rdrei.android.buildtimetracker.BuildTimeTrackerExtension
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
    id("com.github.ben-manes.versions")
    id("net.rdrei.android.buildtimetracker")
//    id("org.sonarqube")
    java

    id("com.github.jk1.dependency-license-report")
    id("me.qoomon.git-versioning")
    id("com.adarshr.test-logger")

    id("jacoco-report-aggregation")
    id("test-report-aggregation")
    id("com.osacky.doctor")
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
    maven {
        url = uri(project.properties["artifactDownloadUrl"] as String)
        // credentials {
        //	username = project.properties["artifactUsername"] as String
        //	password = project.properties["artifactPassword"] as String
        // }
    }
}

apply(plugin = "base")
apply(from = "$rootDir/gradle/wrapperTask.gradle.kts")

gitVersioning.apply {
    describeTagPattern = "v.+"
    refs {
        considerTagsOnBranches = true
        tag("v(?<version>.*)") {
            version = "\${ref.version}"
        }
        branch("(main|master|release)") {
            describeTagPattern = "v.+"
            version = "\${describe.tag.version.core}-\${describe.distance}-\${commit.short}"
        }
        branch("develop") {
            describeTagPattern = "v.+"
            version = "\${describe.tag.version.core}-\${describe.distance}-\${commit.short}-DEV"
        }
        branch("feature/(?<feature>.+)") {
            version = "0.0.0-\${commit.short}-\${ref.feature}"
        }
        branch(".+") {
            version = "0.0.0-\${commit.short}-\${ref}-SNAPSHOT"
        }
    }

    // optional fallback configuration in case of no matching ref configuration
    rev {
        version = "\${commit}"
    }
}

configure<DoctorExtension> {
    javaHome {
        ensureJavaHomeMatches.set(false)
        ensureJavaHomeIsSet.set(false)
    }
}

tasks.getByName("clean").doLast {
    delete("build")
}

defaultTasks("clean", "test", "build")

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

allprojects {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven {
            url = uri(project.properties["artifactDownloadUrl"] as String)
            // credentials {
            // 	username = project.properties["artifactUsername"] as String
            // 	password = project.properties["artifactPassword"] as String
            // }
        }
    }
    apply(plugin = "build-dashboard")
    apply(plugin = "idea")
    apply(plugin = "project-report")

    apply(plugin = "com.github.ben-manes.versions")

    group = project.properties["projectGroup"] as String

    tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {

        // optional parameters
        checkForGradleUpdate = true
        revision = "release"
    }

    tasks.withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }
}

configure<IdeaModel> {
    module.jdkName = "Java JDK ${properties["sourceCompatibility"]}"
    module.languageLevel = IdeaLanguageLevel(properties["targetCompatibility"] as String)
    project.vcs = "Git"
}

subprojects {
    apply(plugin = "idea")
    configure<IdeaModel> {
        module {
            isDownloadSources = true
            isDownloadJavadoc = true
        }
    }
}


configure<BuildTimeTrackerExtension> {
    reporters.register("summary") {
        options.map {
            "ordered" to false
            "threshold" to 50
            "barstyle" to "unicode"
        }
    }
}

tasks.register("printVersion") {
    group = "CI/CD tasks"
    description = "generate the project version from git repository using rules, tags and history"
    doLast {
        println("VERSION:${project.properties["version"]}")
    }
}

tasks.getByName("version") {
    group = "CI/CD tasks"
    description = "generate the project version from git repository using rules, tags and history"
}