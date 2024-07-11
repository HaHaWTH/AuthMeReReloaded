dependencies {
    compileOnly(project(":project:module-common"))
    compileOnly(project(":project:module-util"))
    compileOnly(project(":project:module-logger"))
    compileOnly(project(":project:module-configuration"))
    compileOnly(project(":project:module-message"))
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
    // Java Email Library
    implementation("org.apache.commons:commons-email:1.6.0")
}

tasks.shadowJar {
    relocate("org.apache.commons", "${project.group}.libs.org.apache.commons")
}