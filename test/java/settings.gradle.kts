rootProject.name = "ai-wh40k"

rootDir.resolve("module").listFiles()?.forEach { file ->
  if (file.isDirectory && file?.list { _, name -> name == "build.gradle.kts" }?.isNotEmpty() == true) {
    val name = file.name;
    include(":$name")
    project(":$name").projectDir = file("module/$name")
  } else {
    throw Exception("Invalid module directory: $file")
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
