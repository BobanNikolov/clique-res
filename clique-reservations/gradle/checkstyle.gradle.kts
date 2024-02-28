import org.gradle.api.plugins.quality.CheckstyleExtension

apply(plugin = "checkstyle")

configure<CheckstyleExtension> {
    toolVersion = project.properties["checkstyleVersion"] as String

    val archive = configurations.getByName("checkstyle").filter { it.name.startsWith("checkstyle") }
    config = resources.text.fromArchiveEntry(archive, "google_checks.xml")
    configProperties =
        mapOf(
            "org.checkstyle.google.suppressionfilter.config" to rootProject.file("checkstyle-suppressions.xml").absolutePath,
            "suppressionFile" to rootProject.file("checkstyle-suppressions.xml").absolutePath
        )

    setIgnoreFailures(true)
    maxWarnings = 0
    maxErrors = 0
}

tasks.withType<Checkstyle>().configureEach {
    minHeapSize.set("200m")
    maxHeapSize.set("1g")
    reports {
        xml.required.set(true)
        html.required.set(true)
        sarif.required.set(true)
    }
}
