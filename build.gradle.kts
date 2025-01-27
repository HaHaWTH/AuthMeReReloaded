plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

description = "Fork of the first authentication plugin for the Bukkit API!"

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    mavenLocal()
    // PaperMC
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repository.apache.org/content/repositories/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://repo.dmulloy2.net/nexus/repository/releases/")
    maven("https://repo.dmulloy2.net/nexus/repository/snapshots/")
    maven("https://repo.onarandombox.com/multiverse-releases")
    maven("https://jitpack.io/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // Spigot API, https://www.spigotmc.org/
    compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
    // Java Libraries
    compileOnly("org.geysermc.floodgate:api:2.2.2-SNAPSHOT")
    // Jalu Injector
    implementation("ch.jalu:injector:1.0")
    // String comparison library. Used for dynamic help system.
    implementation("net.ricecode:string-similarity:1.0.0")
    // MaxMind GEO IP with our modifications to use GSON in replacement of the big Jackson dependency
    // GSON is already included and therefore it reduces the file size in comparison to the original version
    implementation("com.maxmind.db:maxmind-db-gson:2.0.3") {
        exclude("com.google.code.gson", "gson")
    }
    // Library for tar archives
    implementation("javatar:javatar:2.5")
    // Java Email Library
    implementation("org.apache.commons:commons-email:1.6-SNAPSHOT")
    // Log4J Logger (required by the console filter)
    compileOnly("org.apache.logging.log4j:log4j-core:2.20.0") // Log4J version bundled in 1.12.2
    // Libby
    implementation("com.alessiodp.libby:libby-bukkit:2.0.0-SNAPSHOT")
    // Database Connection Pool
    implementation("com.zaxxer:HikariCP:4.0.3" /* Latest java 8 release */) {
        exclude("org.slf4j", "slf4j-api")
    }
    // HikariCP Logger
    implementation("org.slf4j:slf4j-simple:1.7.36") // We can't update to 2.x as long as we use HikariCP for java 8
    // PBKDF2 implementation
    implementation("de.rtner:PBKDF2:1.1.4")
    // MySQL connector, shaded into the legacy jar
    implementation("com.mysql:mysql-connector-j:8.4.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
    // Argon2 implementation
    implementation("de.mkammerer:argon2-jvm-nolibs:2.11")
    // TOTP client
    implementation("com.warrenstrange:googleauth:1.5.0")
    // Keep in sync with spigot 1.19
    implementation("com.google.guava:guava:33.2.1-jre") {
        exclude("org.checkerframework", "checker-qual")
    }
    implementation("com.google.code.gson:gson:2.10.1")
    // ConfigMe
    implementation("ch.jalu:configme:1.3.1") {
        exclude("org.yaml", "snakeyaml")
    }
    // bStats metrics
    implementation("org.bstats:bstats-bukkit:3.0.2")
    // ProtocolLib
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    // Adventure API
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    implementation("net.kyori:adventure-text-serializer-gson:4.17.0")
    // LuckPerms plugin
    compileOnly("net.luckperms:api:5.4")
    // PermissionsEx plugin
    compileOnly("ru.tehkode:PermissionsEx:1.23.5-SNAPSHOT")
    // zPermissions plugin
    compileOnly("org.tyrannyofheaven.bukkit:zPermissions:1.4.3-SNAPSHOT") {
        exclude("org.avaje", "ebean")
    }
    // Vault, https://dev.bukkit.org/bukkit-plugins/vault/
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    // Multi World plugin, https://www.spigotmc.org/resources/multiverse-core.390/
    compileOnly("com.onarandombox.multiversecore:multiverse-core:4.3.14")
    // EssentialsX plugin
    compileOnly("net.essentialsx:EssentialsX:2.20.1") {
        exclude("io.papermc", "paperlib")
    }
    // BCrypt implementation
    implementation("at.favre.lib:bcrypt:0.10.2")
    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.6")
    // XAuth, another authentication plugin, required by the database converter
    compileOnly("de.luricos.bukkit:xAuth:2.6.1-SNAPSHOT")
    implementation("ch.jalu:datasourcecolumns:0.1.1-SNAPSHOT")
    implementation("org.postgresql:postgresql:42.7.3") {
        exclude("org.checkerframework", "checker-qual")
    }
    // Required to mock the LuckPerms API
    testImplementation("org.checkerframework:checker-qual:3.48.0")
    // Universal Scheduler
    implementation("com.github.Anon8281:UniversalScheduler:0.1.6")
    // JDBC drivers for datasource integration tests
    testImplementation("org.xerial:sqlite-jdbc:3.47.1.0")
    compileOnly("com.h2database:h2:2.2.224")
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
        relocate(
            "com.github.Anon8281.universalScheduler",
            "fr.xephi.authme.libs.com.github.Anon8281.universalScheduler"
        )
        relocate("com.mysql", "fr.xephi.authme.libs.com.mysql")
        relocate("com.google.protobuf", "fr.xephi.authme.libs.com.google.protobuf")
        relocate("io.netty", "fr.xephi.authme.libs.io.netty")
        relocate("org.apache.commons.validator", "fr.xephi.authme.libs.org.apache.commons.validator")
        relocate("com.alessiodp.libby", "fr.xephi.authme.libs.com.alessiodp.libby")
        relocate("net.kyori.adventure", "fr.xephi.authme.libs.net.kyori.adventure")
        relocate("net.kyori.examination", "fr.xephi.authme.libs.net.kyori.examination")
        relocate("net.kyori.option", "fr.xephi.authme.libs.net.kyori.option")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
