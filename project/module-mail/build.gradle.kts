dependencies {
    compileOnly(project(":project:module-util"))
    compileOnly(project(":project:module-logger"))
    compileOnly(project(":project:module-common"))
    compileOnly(project(":project:module-security"))
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
    // Java Email Library
    api(libs.apache.commons.email)
}

tasks.shadowJar {
    relocate("org.apache.commons", "${project.group}.libs.org.apache.commons")
}