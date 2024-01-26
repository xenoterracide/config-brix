/*
Copyright © 2023 Caleb Cushing.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OFS ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  `java-library`
  `java-test-fixtures`
}

repositories {
  mavenCentral()
}
val libs = the<LibrariesForLibs>()

dependencies {
  implementation(libs.log4j.api)
  testImplementation(platform(libs.junit.bom))
  testImplementation(libs.bundles.test)
  testRuntimeOnly(libs.bundles.junit.platform)
}

val available = tasks.register("tests available") {
  val java: Provider<FileCollection> = sourceSets.test.map { it.java }
  doLast {
    if (java.get().isEmpty) throw RuntimeException("no tests found")
  }
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()

  testLogging {
    lifecycle {
      showStandardStreams = true
      displayGranularity = 2
      exceptionFormat = TestExceptionFormat.FULL
      events.addAll(listOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED))
    }
  }
  reports {
    html.required.set(false)
    junitXml.required.set(false)
  }
  inputs.dir(rootProject.file("buildSrc/src/main"))
  finalizedBy(available)

  afterSuite(
    KotlinClosure2<TestDescriptor, TestResult, Unit>(
      { descriptor, result ->
        if (descriptor.parent == null) {
          logger.lifecycle("Tests run: ${result.testCount}, Failures: ${result.failedTestCount}, Skipped: ${result.skippedTestCount}")
          if (result.testCount == 0L) throw IllegalStateException("You cannot have 0 tests");
        }
        Unit
      })
  )
}
