dependencies {
    compileOnly(project(":project:module-logger"))
    compileOnly(libs.guava)
    // String comparison library. Used for dynamic help system.
    implementation("net.ricecode:string-similarity:1.0.0")
}

tasks.shadowJar {
    relocate("net.ricecode.similarity", "${project.group}.libs.net.ricecode.similarity")
}