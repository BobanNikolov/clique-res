plugins {
    id("org.springframework.boot") version "3.2.2" // Use the appropriate Spring Boot version
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    project(":endpoint")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

springBoot {
    buildInfo()
}
