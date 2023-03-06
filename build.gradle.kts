plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "dev.dasischbims"
version = "1.0-SNAPSHOT"
description = "Noita save utility for uploading save files to Google Drive"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("com.diogonunes:JColor:5.5.1")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.zeroturnaround:zt-zip:1.15")
    implementation("com.google.api-client:google-api-client:2.2.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.3.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "dev.dasischbims.nsu.Main"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "19"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    doLast {
        file("./build/libs/${project.name}-${project.version}-all.jar").copyTo(file("./out/nsu.jar"), overwrite = true)
    }
}