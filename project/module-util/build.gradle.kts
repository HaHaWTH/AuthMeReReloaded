dependencies {
    compileOnly(libs.guava)
    // String comparison library. Used for dynamic help system.
    implementation("net.ricecode:string-similarity:1.0.0")
}

tasks {
    shadowJar {
        relocate("net.ricecode.similarity", "fr.xephi.authme.libs.ricecode.net.ricecode.similarity")
    }
}