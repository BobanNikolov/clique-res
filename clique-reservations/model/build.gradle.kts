plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    `java-library`
}

apply(from = "$rootDir/gradle/configJavaModule.gradle.kts")

// https://spring.io/blog/2016/12/16/dependency-management-plugin-1-0-0-rc1
dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    api("org.springframework.data:spring-data-jpa")
    api("jakarta.persistence:jakarta.persistence-api")

    implementation("org.hibernate.orm:hibernate-core")
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen")


    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.apache.commons:commons-lang3")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit", module = "junit")
    }
}

// *********************************************************************************************************************
// BUILD

tasks.getByName<Jar>("jar") {
    val projectName: String by project
    val name: String by project
    val archiveExtension: String by archiveExtension
    archiveFileName.set("${projectName}-${name}.${archiveExtension}")
}

tasks.withType<JavaCompile> {
    options.annotationProcessorGeneratedSourcesDirectory = file("build/generated")
}

sourceSets {
    create("analytics") {
        java.srcDir("src/main/java")
        java.srcDir("build/generated")
        resources.srcDir("src/main/resources")
    }
}