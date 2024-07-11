dependencies {
    compileOnly(project(":project:module-util"))
    compileOnly(libs.guava)
    compileOnly(libs.configme)
    compileOnly(libs.jalu.injector)
    // PBKDF2 implementation
    implementation("de.rtner:PBKDF2:1.1.4")
    // BCrypt implementation
    implementation("at.favre.lib:bcrypt:0.10.2")
}

tasks.shadowJar {
    relocate("de.rtner", "${project.group}.libs.de.rtner")
    relocate("at.favre.lib", "${project.group}.libs.at.favre.lib")
}