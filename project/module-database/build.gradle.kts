dependencies {
    // TODO 纯S这Authme原来的数据库
    compileOnly(project(":project:module-util"))
    compileOnly(project(":project:module-common"))
    compileOnly(project(":project:module-logger"))
    compileOnly(libs.guava)
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
    // Database Connection Pool
    implementation("com.zaxxer:HikariCP:4.0.3" /* Latest java 8 release */) {
        exclude("org.slf4j", "slf4j-api")
    }
    implementation("ch.jalu:datasourcecolumns:0.1.2")
}

tasks.shadowJar {
    relocate("com.zaxxer.hikari", "${project.group}.libs.com.zaxxer.hikari")
    relocate("ch.jalu", "${project.group}.libs.ch.jalu")
}