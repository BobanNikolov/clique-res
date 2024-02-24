plugins {
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
    project(":service")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
