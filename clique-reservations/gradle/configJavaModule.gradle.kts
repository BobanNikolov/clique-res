import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.net.InetAddress
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

apply(plugin = "java")

tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Title"] = rootProject.name + "-" + project.name
        attributes["Implementation-Version"] = project.version
        attributes["Built-By"] = System.getProperty("user.name")
        attributes["Built-JDK"] = System.getProperty("java.version")
        attributes["Built-Host"] = InetAddress.getLocalHost().getHostName()
        attributes["Build-Time"] = OffsetDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
        attributes["Source-Compatibility"] = project.properties["sourceCompatibility"] as String
        attributes["Target-Compatibility"] = project.properties["targetCompatibility"] as String
        attributes["Git-Branch"] = project.properties["git.ref"]
        attributes["Git-Commit"] = project.properties["git.commit"]
    }
}

//apply(from = "$rootDir/gradle/createBuildInfoFile.gradle")

apply(plugin = "com.adarshr.test-logger")

// Version Overrides
// extra["log4j2.version"] = "2.17.0"


tasks.withType<JavaCompile> {
    // this subproject requires -parameters option
    options.compilerArgs.addAll(listOf("-parameters", "-Xlint:unchecked", "-Xlint:deprecation"))

    //enable compilation in a separate daemon process
    options.setFork(true)
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(project.properties["targetCompatibility"] as String))
//		vendor.set(JvmVendorSpec.ADOPTOPENJDK)
    }
    sourceCompatibility = JavaVersion.toVersion(project.properties["sourceCompatibility"] as String)
    targetCompatibility = JavaVersion.toVersion(project.properties["targetCompatibility"] as String)
}

// *********************************************************************************************************************
// TEST

apply(from = "$rootDir/gradle/jacoco.gradle.kts")
apply(from = "$rootDir/gradle/checkstyle.gradle.kts")

dependencies {
    "compileOnly"("org.projectlombok:lombok")
    "annotationProcessor"("org.projectlombok:lombok")
    "testCompileOnly"("org.projectlombok:lombok")
    "testAnnotationProcessor"("org.projectlombok:lombok")

    "annotationProcessor"("org.springframework.boot:spring-boot-configuration-processor")

    "implementation"("org.slf4j:slf4j-api")

    "testImplementation"("org.junit.jupiter:junit-jupiter-api")
    "testImplementation"("org.junit.jupiter:junit-jupiter-params")
    "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine")

    "testImplementation"("org.mockito:mockito-core")
    "testImplementation"("org.mockito:mockito-junit-jupiter")
    "testImplementation"("org.assertj:assertj-core")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        showExceptions = true
        exceptionFormat = FULL
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }

    // Fail the 'test' task on the first test failure
    failFast = true

    finalizedBy(tasks.named("jacocoTestReport"))
}
