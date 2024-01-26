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
import org.gradle.kotlin.dsl.support.normaliseLineSeparators

plugins {
  `java-base`
  id("com.diffplug.spotless")
}

val copyright = "Copyright © \$YEAR Caleb Cushing."
val license = """
  /*
  $copyright

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
  """.trimIndent().normaliseLineSeparators()

spotless {
  if (!providers.environmentVariable("CI").isPresent) {
    ratchetFrom("origin/main")
  }
  java {
    licenseHeader(license)
  }


  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**")
    licenseHeader(license, "(import|buildscript|plugins|root)")
    // ktfmt()
  }
}
