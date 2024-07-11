dependencies {
    compileOnly(project(":project:module-util"))
    compileOnly(project(":project:module-logger"))
    compileOnly(project(":project:module-common"))
    compileOnly(libs.guava)
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
    // PBKDF2 implementation
    implementation("de.rtner:PBKDF2:1.1.4")
    // BCrypt implementation
    implementation("at.favre.lib:bcrypt:0.10.2")
    // Argon2 implementation
    implementation("de.mkammerer:argon2-jvm-nolibs:2.11")
}

tasks.shadowJar {
    relocate("de.rtner", "${project.group}.libs.de.rtner")
    relocate("at.favre.lib", "${project.group}.libs.at.favre.lib")
    relocate("de.mkammerer", "${project.group}.libs.de.mkammerer")
}