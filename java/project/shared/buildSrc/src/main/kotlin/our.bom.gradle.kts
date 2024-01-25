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

plugins {
  `java-library`
  `java-test-fixtures`
  id("com.github.ben-manes.versions")
}

dependencyLocking {
  lockAllConfigurations()
}

repositories {
  mavenCentral()
}
val libs = the<LibrariesForLibs>()

configurations.configureEach {
  exclude(group = "org.slf4j", module = "slf4j-nop")
  exclude(group = "junit", module = "junit")

  resolutionStrategy {
    componentSelection {
      all {
        val spotbugs = Regex("^spotbugs.*")
        if (!name.matches(spotbugs) && !candidate.module.matches(spotbugs) ) {
          val nonRelease = Regex("^[\\d.]+-(M|ea|beta).*$")
          if (candidate.version.matches(nonRelease)) reject("no pre-release")
        }

        if (candidate.module == "nullaway") {
          val reason = "crash https://github.com/uber/NullAway/issues/533"
          if (candidate.version.matches(Regex("^0\\.9\\.[34]$"))) reject(reason)
        }
      }
    }
  }
}
