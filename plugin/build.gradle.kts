subprojects {

    repositories {
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
        maven("https://repo.onarandombox.com/content/repositories/multiverse/")
        maven("https://repo.onarandombox.com/content/repositories/multiverse-snapshots/")
        maven("https://nexuslite.gcnt.net/repos/other/")
        maven("https://jitpack.io/")
        maven("https://repo.maven.apache.org/maven2/")
    }

    dependencies {
        // Modules
        implementation(project(":project:module-configuration", "shadow"))
        implementation(project(":project:module-logger", "shadow"))
        implementation(project(":project:module-util", "shadow"))
        implementation(project(":project:module-security", "shadow"))
        implementation(project(":project:module-common", "shadow"))
        implementation(project(":project:module-message", "shadow"))
        implementation(project(":project:module-database", "shadow"))
        implementation(project(":project:module-mail", "shadow"))
        // Adventure API
        implementation(rootProject.libs.adventure.text.minimessage)
        implementation(rootProject.libs.adventure.text.serializer.gson)
        // Spigot API, https://www.spigotmc.org/
        compileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
        // Java Libraries
        compileOnly("org.geysermc.floodgate:api:2.2.2-SNAPSHOT")
        // Jalu Injector
        implementation(rootProject.libs.jalu.injector)
        // MaxMind GEO IP with our modifications to use GSON in replacement of the big Jackson dependency
        // GSON is already included and therefore it reduces the file size in comparison to the original version
        implementation("com.maxmind.db:maxmind-db-gson:2.0.3") {
            exclude("com.google.code.gson", "gson")
        }
        // Library for tar archives
        implementation("javatar:javatar:2.5")
        // Log4J Logger (required by the console filter) TODO Remove
        compileOnly("org.apache.logging.log4j:log4j-core:2.20.0") // Log4J version bundled in 1.12.2
        // Libby
        implementation("com.alessiodp.libby:libby-bukkit:2.0.0-SNAPSHOT")
        // Database Connection Pool TODO  Remove
        implementation("com.zaxxer:HikariCP:4.0.3" /* Latest java 8 release */) {
            exclude("org.slf4j", "slf4j-api")
        }
        // HikariCP Logger
        implementation("org.slf4j:slf4j-simple:1.7.36") // We can't update to 2.x as long as we use HikariCP for java 8
        // MySQL connector, shaded into the legacy jar
        implementation("com.mysql:mysql-connector-j:8.4.0")
        implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
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
        // Dependencies used by HAProxy feature
//    implementation("io.netty:netty-codec-haproxy:4.1.104.Final")
//    compileOnly("commons-validator:commons-validator:1.8.0")
        // zPermissions plugin
        compileOnly("org.tyrannyofheaven.bukkit:zPermissions:1.4.3-SNAPSHOT") {
            exclude("org.avaje", "ebean")
        }
        // Vault, https://dev.bukkit.org/bukkit-plugins/vault/
        compileOnly("net.milkbowl.vault:VaultAPI:1.7")
        // Multi World plugin, https://www.spigotmc.org/resources/multiverse-core.390/
        compileOnly("com.onarandombox.multiversecore:Multiverse-Core:4.3.1")
        // EssentialsX plugin
        compileOnly("net.essentialsx:EssentialsX:2.20.1")
        // XAuth, another authentication plugin, required by the database converter
        compileOnly("de.luricos.bukkit:xAuth:2.6.1-SNAPSHOT")
        implementation("ch.jalu:datasourcecolumns:0.1.1-SNAPSHOT") // TODO REMOVE
        implementation("org.postgresql:postgresql:42.7.3") {
            exclude("org.checkerframework", "checker-qual")
        }
        // Required to mock the LuckPerms API
        testImplementation("org.checkerframework:checker-qual:3.40.0")
        // Universal Scheduler
        implementation("com.github.Anon8281:UniversalScheduler:0.1.6")
        // JDBC drivers for datasource integration tests
        testImplementation("org.xerial:sqlite-jdbc:3.46.0.0")
        compileOnly("com.h2database:h2:2.2.224")
    }

    tasks {
        if (!project.name.startsWith("platform")) return@tasks
        build { dependsOn(shadowJar) }
        // ShadowJar Config
        shadowJar {
            // Options
            archiveAppendix.set("")
            archiveClassifier.set("")
            destinationDirectory.set(file("$rootDir/outs"))
            // Kotlin
            relocate("kotlin.", "kolin200.")
            // Adventure
            relocate("net.kyori.adventure", "${project.group}.libs.net.kyori.adventure")
            relocate("net.kyori.examination", "${project.group}.libs.net.kyori.examination")
            relocate("net.kyori.option", "${project.group}.libs.net.kyori.option")
            // Others
            relocate("org.apache.http", "${project.group}.libs.org.apache.http")
            relocate("org.apache.commons", "${project.group}.libs.org.apache.commons")
            relocate("waffle", "${project.group}.libs.waffle")
            relocate("com.github.benmanes.caffeine", "${project.group}.libs.com.github.benmanes.caffeine")
            relocate("com.google.common", "${project.group}.libs.com.google.common")
            relocate("com.google.thirdparty", "${project.group}.libs.com.google.thirdparty")
            relocate("com.google.j2objc", "${project.group}.libs.com.google.j2objc")
            relocate("com.google.errorprone", "${project.group}.libs.com.google.errorprone")
            relocate("com.google.gson", "${project.group}.libs.com.google.gson")
            relocate("org.apache.http", "${project.group}.libs.org.apache.http")
            relocate("org.apache.commons", "${project.group}.libs.org.apache.commons")
            relocate("com.github.benmanes.caffeine", "${project.group}.libs.com.github.benmanes.caffeine")
            relocate("ch.jalu", "${project.group}.libs.ch.jalu")
            relocate("org.slf4j", "${project.group}.libs.org.slf4j")
            relocate("com.maxmind.db", "${project.group}.libs.com.maxmind.db")
            relocate("com.ice.tar", "${project.group}.libs.com.icetar.tar")
            relocate("org.picketbox", "${project.group}.libs.org.picketbox")
            relocate("org.jboss.crypto", "${project.group}.libs.org.jboss.crypto")
            relocate("org.jboss.security", "${project.group}.libs.org.jboss.security")
            relocate("com.warrenstrange", "${project.group}.libs.com.warrenstrange")
            relocate("javax.inject", "${project.group}.libs.javax.inject")
            relocate("at.favre.lib", "${project.group}.libs.at.favre.lib")
            relocate("org.postgresql", "${project.group}.libs.org.postgresql")
            // bStats metrics class
            relocate("org.bstats", "${project.group}.libs.org.bstats")
            relocate("org.mariadb.jdbc", "${project.group}.libs.org.mariadb.jdbc")
            relocate(
                "com.github.Anon8281.universalScheduler",
                "${project.group}.libs.com.github.Anon8281.universalScheduler"
            )
            relocate("com.mysql", "${project.group}.libs.com.mysql")
            relocate("com.google.protobuf", "${project.group}.libs.com.google.protobuf")
            relocate("io.netty", "${project.group}.libs.io.netty")
            relocate("org.apache.commons.validator", "${project.group}.libs.org.apache.commons.validator")
            relocate("com.alessiodp.libby", "${project.group}.libs.com.alessiodp.libby")
        }
    }

}
