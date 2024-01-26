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
  `java-test-fixtures`
  checkstyle
}

val libs = the<LibrariesForLibs>()

tasks.withType<Checkstyle>().configureEach {
  isShowViolations = true
  reports {
    html.required.set(false)
    xml.required.set(false)
  }
}

fun checkstyleConfig(filename: String): File {
  val path = "checkstyle/${filename}"
  val f = file(path)
  return if (f.exists()) f else rootProject.file(path)
}

tasks.named<Checkstyle>("checkstyleMain") {
  configFile = checkstyleConfig("main.xml")
}

tasks.named<Checkstyle>("checkstyleTestFixtures") {
  configFile = checkstyleConfig("test.xml")
}

tasks.named<Checkstyle>("checkstyleTest") {
  configFile = checkstyleConfig("test.xml")
}
