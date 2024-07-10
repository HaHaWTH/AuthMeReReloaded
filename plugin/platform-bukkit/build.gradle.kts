description = "Fork of the first authentication plugin for the Bukkit API!"

dependencies {

  // Adventure Bukkit
  implementation("net.kyori:adventure-platform-bukkit:4.3.2")

  // Hooks - Start
  // bStats metrics
  implementation("org.bstats:bstats-bukkit:3.0.2")
  // ProtocolLib
  compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
  // LuckPerms plugin
  compileOnly("net.luckperms:api:5.4")
  // PermissionsEx plugin
  compileOnly("ru.tehkode:PermissionsEx:1.23.5-SNAPSHOT")
  // Hooks - End

}

tasks.shadowJar {
    archiveBaseName.set("AuthMe-Bukkit")
}