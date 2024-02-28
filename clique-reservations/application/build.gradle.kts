import com.gorylenko.GitPropertiesPluginExtension
import org.springframework.boot.gradle.dsl.SpringBootExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.time.OffsetDateTime

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java

    id("org.owasp.dependencycheck")
    id("com.gorylenko.gradle-git-properties")
}

apply(from = "$rootDir/gradle/configJavaModule.gradle.kts")

// https://spring.io/blog/2016/12/16/dependency-management-plugin-1-0-0-rc1
dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${project.properties["springCloudVersion"]}")
    }
}

configurations {
    all {
        exclude(group = "commons-logging", module = "commons-logging")
        exclude(group = "com.zaxxer", module = "HikariCP-java7")
        exclude(group = "junit", module = "junit")
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

dependencies {
    implementation(project(":endpoint"))

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-properties-migrator")

    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-config")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("org.springframework:spring-webmvc")
    implementation("org.postgresql:postgresql")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${project.properties["springdocVersion"]}")

    implementation("org.flywaydb:flyway-core")

    implementation("org.apache.commons:commons-lang3")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit", module = "junit")
    }
}

configure<GitPropertiesPluginExtension> {
    keys = listOf(
            "git.branch", "git.commit.id", "git.commit.time",
            "git.commit.user.name", "git.commit.user.email",
            "git.commit.message.short", "git.commit.message.full"
    )
    dateFormat = "yyyy-MM-dd'T'HH:mmZ"
}

configure<SpringBootExtension> {
    buildInfo {
        properties {
            name.set(rootProject.name)
            artifact.set(rootProject.name)
        }
    }
}

// *********************************************************************************************************************
// TEST

dependencies {
    implementation(project(mapOf("path" to ":service")))
    testImplementation("org.springframework.security:spring-security-test")
}

// *********************************************************************************************************************
// BUILD

tasks.getByName<BootJar>("bootJar") {
    val projectName: String by project
    val archiveExtension: String by archiveExtension
    // archiveFileName.set("${projectName}-${project.version}.${archiveExtension}")
    archiveFileName.set("${projectName}.${archiveExtension}")

    manifest {
        attributes(
                mapOf(
                        "Implementation-Title" to rootProject.name,
                        "Implementation-Vendor" to project.group,
                        "Created-By" to project.group,
                        "Implementation-Date" to OffsetDateTime.now(),
                        "Implementation-Version" to project.version
                )
        )
    }
}
