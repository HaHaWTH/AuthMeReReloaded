dependencies {
    compileOnly(project(":project:module-util"))
    compileOnly(libs.guava)
    // Log4J Logger (required by the console filter)
    compileOnly(libs.apache.logging.log4j.core) // Log4J version bundled in 1.12.2
}