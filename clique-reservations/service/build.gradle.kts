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
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${project.properties["springCloudVersion"]}")
    }
}

dependencies {
    api(project(":model"))

    implementation("org.slf4j:slf4j-api")

    implementation("org.springframework.boot:spring-boot")

    implementation("com.innoventsolutions.birt-spring-boot:birt-spring-boot-starter:0.0.7")


    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-web")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.springframework:spring-context-support")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.springframework.boot:spring-boot-starter-graphql")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("org.aspectj:aspectjweaver")

    implementation("org.apache.commons:commons-lang3")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")

    implementation("org.mapstruct:mapstruct:${project.properties["mapstructVersion"]}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${project.properties["mapstructVersion"]}")

    implementation("org.springdoc:springdoc-openapi-starter-common:${project.properties["springdocVersion"]}")

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