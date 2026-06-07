// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.ksp) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.sonarqube)
}

sonar {
    properties {
        property("sonar.projectKey", "myorgtest-validator_weatherapp")
        property("sonar.organization", "myorgtest-validator")
        property("sonar.host.url", "https://sonarcloud.io")

        property("sonar.kotlin.binaries", "**/build/classes/kotlin/main, **/build/tmp/kotlin-classes/debug")
        property("sonar.sources", "src/main/java")
        property("sonar.tests", "src/test/java, src/androidTest/java")
        property("sonar.junit.reportPaths", "**/build/test-results/testDebugUnitTest")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    }
}

