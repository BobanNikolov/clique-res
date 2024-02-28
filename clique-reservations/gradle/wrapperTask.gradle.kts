tasks.withType<Wrapper> {
	gradleVersion = properties["gradleVersion"].toString()
	distributionUrl = "https://repo1.maven.org/maven2/"
	distributionType = Wrapper.DistributionType.BIN
}
