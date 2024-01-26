// SPDX-License-Identifier: MIT
// Copyright © 2023-2024 Caleb Cushing.
buildscript { dependencyLocking.lockAllConfigurations() }

plugins { our.javalibrary }

dependencies {
  runtimeOnly(platform(libs.spring.platform))
  runtimeOnly(libs.starter.core)
  runtimeOnly(libs.starter.log4j2)
  runtimeOnly(libs.starter.webflux)
  runtimeOnly(libs.starter.actuator)

  api(platform(libs.spring.platform))
  api(libs.spring.boot.autoconfigure)

  implementation(platform(libs.spring.platform))
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot.core)
  implementation(libs.spring.context)

  testImplementation(platform(libs.spring.platform))
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.junit.api)
  testImplementation(libs.spring.beans)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}

tasks.withType<JacocoCoverageVerification>().configureEach {
  dependsOn(project.tasks.withType<JacocoReport>())
  violationRules { rule { limit { minimum = 0.3.toBigDecimal() } } }
}
