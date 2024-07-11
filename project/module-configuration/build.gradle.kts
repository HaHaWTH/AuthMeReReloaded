/*
  代码来自 Taboolib:module-configuration
  链接 https://github.com/TabooLib/taboolib/blob/master/module/module-configuration
 */

dependencies {
    compileOnly(project(":project:module-util"))
    api("org.yaml:snakeyaml:2.2")
    api("com.electronwill.night-config:core:3.6.7")
    api("com.electronwill.night-config:yaml:3.6.7") {
        exclude("org.yaml", "snakeyaml")
    }
}

tasks.shadowJar {
    // NightConfig
    relocate("com.electronwill.nightconfig", "com.electronwill.nightconfig_3_6_7")
    // Snakeyaml
    relocate("org.yaml.snakeyaml", "org.yaml.snakeyaml_2_2")
}