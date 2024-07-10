subprojects {

    tasks {
        build { dependsOn(shadowJar) }
    }

}