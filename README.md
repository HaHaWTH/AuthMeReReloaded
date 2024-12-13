# AuthMeReReloaded
**"A fork of the best authentication plugin for the Bukkit modding API!⭐"**

[English](https://github.com/HaHaWTH/AuthMeReReloaded) | [简体中文](https://github.com/HaHaWTH/AuthMeReReloaded/blob/master/README-zh.md)

![Graph](https://bstats.org/signatures/bukkit/AuthMeReloaded-Fork.svg)
<p align="center">
  <img src="https://img.shields.io/github/languages/code-size/HaHaWTH/AuthMeReReloaded.svg" alt="Code size"/>
  <img src="https://img.shields.io/github/repo-size/HaHaWTH/AuthMeReReloaded.svg" alt="GitHub repo size"/>
  <img src="https://www.codefactor.io/repository/github/hahawth/authmerereloaded/badge" alt="CodeFactor" />
  <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/HaHaWTH/AuthMeReReloaded/total?logo=github&label=GitHub%20Downloads&color=black">
  <img alt="Spiget Downloads" src="https://img.shields.io/spiget/downloads/114010?logo=spigotmc&label=SpigotMC%20Downloads&color=orange">
  <img alt="Modrinth Downloads" src="https://img.shields.io/modrinth/dt/3IEZ9vol?logo=modrinth&label=Modrinth%20Downloads&color=light-green">
  <img alt="Hangar Downloads" src="https://img.shields.io/hangar/dt/AuthMeReReloaded?logo=hangar&label=Hangar%20Downloads&color=white">
</p>

**Detailed Changes:**
 1. Improved mail sending logic & support more emails
 2. Shutdown mail sending(When server is closed, email you)
 3. Legacy bug fixes
 4. Anti Ghost Player(Doubled login bug)
 5. Use the best performance method by server brand
 6. Bedrock Compatibility(Floodgate needed)(based on UUID)
 7. Update checker
 8. Integrated GUI Captcha feature(Bedrock compatibility & ProtocolLib needed)(70% Asynchronous)
 9. Improved listeners
 10. Player login logic improvement to reduce lag
 11. Automatically purge bot data
 12. **Folia support (in active testing)**
 13. **Velocity support (See [Velocity Support](./vc-support.md))**
 14. Support Virtual Threads caching
 15. Automatically fix portal stuck issue
 16. Automatically login for Bedrock players(configurable)
 17. Fix shulker box crash bug on legacy versions(MC 1.13-)
 18. **H2 database support**
 19. **100% compatibility with original authme and extensions**
 20. More......

**Download links:**
[Releases](https://github.com/HaHaWTH/AuthMeReReloaded/releases/latest)
[Actions(Dev builds, use at your own risk!)](https://github.com/HaHaWTH/AuthMeReReloaded/actions/workflows/maven.yml)

**Pull Requests and suggestions are welcome!**

## Building

* [Gradle](https://gradle.org/) - Dependency Management

The GradleWrapper in included in this project.

**Windows:**

```
gradlew.bat clean build
```

**macOS/Linux:**

```
./gradlew clean build
```

Build artifacts should be found in `./outs` folder.

## License

Only modifications to AuthMeReloaded is under AGPL-3.0 license, AuthMeReloaded is licensed under GPL-3.0.

<picture>
  <source
    media="(prefers-color-scheme: dark)"
    srcset="
      https://api.star-history.com/svg?repos=HaHaWTH/AuthMeReReloaded&type=Date&theme=dark
    "
  />
  <img alt="Star History Chart" src="https://api.star-history.com/svg?repos=HaHaWTH/AuthMeReReloaded&type=Date" />
</picture>
