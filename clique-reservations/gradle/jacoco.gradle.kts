apply(plugin = "jacoco")

configure<JacocoPluginExtension> {
	toolVersion = project.properties["jacocoToolVersion"] as String
}

tasks.withType<JacocoReport> {
	reports {
		xml.required.set(true)
		csv.required.set(true)
		html.required.set(true)
		// html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

tasks.named("jacocoTestReport") {
	dependsOn("test")

	description = "Creates Jacoco report for unit tests."
	group = "reporting"
}
