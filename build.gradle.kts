import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    // https://mvnrepository.com/artifact/org.whispersystems/curve25519-android
    implementation("cafe.cryptography:curve25519-elisabeth:0.1.0")

    implementation("com.github.ipfs:java-ipfs-api:1.3.3")


    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.mockito:mockito-inline:3.11.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}