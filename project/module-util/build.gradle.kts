dependencies {
    compileOnly(libs.guava)
    // String comparison library. Used for dynamic help system.
    api("net.ricecode:string-similarity:1.0.0")
}

tasks.shadowJar {
    relocate("net.ricecode.similarity", "${project.group}.libs.net.ricecode.similarity")
}