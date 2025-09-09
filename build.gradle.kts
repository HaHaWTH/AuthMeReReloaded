plugins {
    java
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.2"
}

description = "Fork of the first authentication plugin for the Bukkit API!"

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repository.apache.org/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://repo.dmulloy2.net/nexus/repository/releases/")
    maven("https://repo.dmulloy2.net/nexus/repository/snapshots/")
    maven("https://repo.onarandombox.com/multiverse-releases")
    maven("https://jitpack.io/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // Paper API, https://papermc.io/
    compileOnly(libs.paper.api)
    // Floodgate
    compileOnly(libs.floodgate)
    // Jalu Injector
    implementation(libs.injector)
    // String comparison library. Used for dynamic help system.
    implementation(libs.string.similarity)
    // MaxMind GEO IP with our modifications to use GSON in replacement of the big Jackson dependency
    // GSON is already included and therefore it reduces the file size in comparison to the original version
    implementation(libs.maxmind.db.gson) {
        exclude("com.google.code.gson", "gson")
    }
    // Library for tar archives
    implementation(libs.javatar)
    // Java Email Library
    implementation(libs.commons.email)
    // Log4J Logger (required by the console filter)
    compileOnly(libs.log4j.core) // Log4J version bundled in 1.12.2
    // Libby
    implementation(libs.libby.bukkit)
    // Database Connection Pool
    implementation(libs.hikaricp)
    // PBKDF2 implementation
    implementation(libs.pbkdf2)
    // MySQL connector, shaded into the legacy jar
    implementation(libs.mysql.connector.j)
    implementation(libs.mariadb.java.client)
    // Argon2 implementation
    implementation(libs.argon2.jvm.nolibs)
    // TOTP client
    implementation(libs.googleauth)
    // ConfigMe
    implementation(libs.configme) {
        exclude("org.yaml", "snakeyaml")
    }
    // bStats metrics
    implementation(libs.bstats)
    // ProtocolLib
    compileOnly(libs.protocollib)
    // LuckPerms plugin
    compileOnly(libs.luckperms)
    // PermissionsEx plugin
    compileOnly(libs.pex)
    // zPermissions plugin
    compileOnly(libs.zpermissions) {
        exclude("org.avaje", "ebean")
    }
    // Vault
    compileOnly(libs.vault)
    // Multi World plugin
    compileOnly(libs.multiverse)
    // EssentialsX plugin
    compileOnly(libs.essentialsx) {
        exclude("io.papermc", "paperlib")
    }
    // BCrypt implementation
    implementation(libs.bcrypt)
    // PlaceholderAPI
    compileOnly(libs.placeholderapi)
    // XAuth plugin
    compileOnly(libs.xauth)
    implementation(libs.datasourcecolumns)
    implementation(libs.postgresql) {
        exclude("org.checkerframework", "checker-qual")
    }
    // Required to mock the LuckPerms API
    testImplementation(libs.checker.qual)
    // Universal Scheduler
    implementation(libs.universalscheduler)
    // JDBC drivers for datasource integration tests
    testImplementation(libs.sqlite.jdbc)
    compileOnly(libs.h2)
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    build { dependsOn(shadowJar) }

    // ShadowJar Config
    shadowJar {
        // Options
        archiveFileName = "AuthMe-${project.version}.${archiveExtension.get()}"
        destinationDirectory.set(file("$rootDir/outs"))
        // Libraries Relocate
        relocate("org.apache.http", "fr.xephi.authme.libs.org.apache.http")
        relocate("org.apache.commons", "fr.xephi.authme.libs.org.apache.commons")
        relocate("waffle", "fr.xephi.authme.libs.waffle")
        relocate("com.github.benmanes.caffeine", "fr.xephi.authme.libs.com.github.benmanes.caffeine")
        relocate("com.google.common", "fr.xephi.authme.libs.com.google.common")
        relocate("com.google.thirdparty", "fr.xephi.authme.libs.com.google.thirdparty")
        relocate("com.google.j2objc", "fr.xephi.authme.libs.com.google.j2objc")
        relocate("com.google.errorprone", "fr.xephi.authme.libs.com.google.errorprone")
        relocate("com.google.gson", "fr.xephi.authme.libs.com.google.gson")
        relocate("org.apache.http", "fr.xephi.authme.libs.org.apache.http")
        relocate("org.apache.commons", "fr.xephi.authme.libs.org.apache.commons")
        relocate("waffle", "fr.xephi.authme.libs.waffle")
        relocate("com.github.benmanes.caffeine", "fr.xephi.authme.libs.com.github.benmanes.caffeine")
        relocate("ch.jalu", "fr.xephi.authme.libs.ch.jalu")
        relocate("com.zaxxer.hikari", "fr.xephi.authme.libs.com.zaxxer.hikari")
        relocate("org.slf4j", "fr.xephi.authme.libs.org.slf4j")
        relocate("com.maxmind.db", "fr.xephi.authme.libs.com.maxmind.db")
        relocate("com.ice.tar", "fr.xephi.authme.libs.com.icetar.tar")
        relocate("net.ricecode.similarity", "fr.xephi.authme.libs.ricecode.net.ricecode.similarity")
        relocate("de.rtner", "fr.xephi.authme.libs.de.rtner")
        relocate("org.picketbox", "fr.xephi.authme.libs.org.picketbox")
        relocate("org.jboss.crypto", "fr.xephi.authme.libs.org.jboss.crypto")
        relocate("org.jboss.security", "fr.xephi.authme.libs.org.jboss.security")
        relocate("de.mkammerer", "fr.xephi.authme.libs.de.mkammerer")
        relocate("com.warrenstrange", "fr.xephi.authme.libs.com.warrenstrange")
        relocate("javax.inject", "fr.xephi.authme.libs.javax.inject")
        relocate("at.favre.lib", "fr.xephi.authme.libs.at.favre.lib")
        relocate("org.postgresql", "fr.xephi.authme.libs.org.postgresql")
        // bStats metrics class
        relocate("org.bstats", "fr.xephi.authme.libs.org.bstats")
        relocate("org.mariadb.jdbc", "fr.xephi.authme.libs.org.mariadb.jdbc")
        relocate("com.github.Anon8281.universalScheduler", "fr.xephi.authme.libs.com.github.Anon8281.universalScheduler")
        relocate("com.mysql", "fr.xephi.authme.libs.com.mysql")
        relocate("com.google.protobuf", "fr.xephi.authme.libs.com.google.protobuf")
        relocate("io.netty", "fr.xephi.authme.libs.io.netty")
        relocate("org.apache.commons.validator", "fr.xephi.authme.libs.org.apache.commons.validator")
        relocate("com.alessiodp.libby", "fr.xephi.authme.libs.com.alessiodp.libby")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
