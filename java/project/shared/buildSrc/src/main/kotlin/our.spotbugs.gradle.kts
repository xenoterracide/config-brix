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
import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsTask
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-base`
  id("com.github.spotbugs")
}

spotbugs {
  excludeFilter.set(rootProject.file("spotbugs/exclude.xml"))
  effort.set(Effort.MAX)
  reportLevel.set(Confidence.LOW)
}

tasks.withType<SpotBugsTask>().configureEach {
  reports.register("html") {
    enabled = true
  }
}

val libs = the<LibrariesForLibs>()

dependencies {
  spotbugs(libs.spotbugs)
}
