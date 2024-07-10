dependencies {
    compileOnly(project(":project:module-util"))
    compileOnly(libs.guava)
    // Log4J Logger (required by the console filter)
    compileOnly("org.apache.logging.log4j:log4j-core:2.20.0") // Log4J version bundled in 1.12.2
}