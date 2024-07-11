dependencies {
    compileOnly(project(":project:module-common"))
    compileOnly(project(":project:module-util"))
    compileOnly(project(":project:module-logger"))
    compileOnly(project(":project:module-configuration"))
    compileOnly(project(":project:module-message"))
    compileOnly(libs.guava)
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
}