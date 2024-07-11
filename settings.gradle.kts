rootProject.name = "authme"

applyAll("project")
applyAll("plugin")

fun applyAll(name: String) {
    File(rootDir, name).listFiles()?.filter { it.isDirectory }?.forEach {
        include("$name:${it.name}")
    }
}