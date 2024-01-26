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
plugins {
  jacoco
  `java-base`
}

val coverageMinimum: Property<Double> = project.objects.property<Double>()


tasks.withType<JacocoReport> {
  dependsOn(project.tasks.withType<Test>())
}

project.tasks.check.configure {
  dependsOn(tasks.withType<JacocoCoverageVerification>())
}

tasks.withType<JacocoCoverageVerification>().configureEach {
  dependsOn(project.tasks.withType<JacocoReport>())
  violationRules {
    rule {
      limit {
        coverageMinimum.convention(0.9).let { minimum = it.get().toBigDecimal() }
      }
    }
  }
}
