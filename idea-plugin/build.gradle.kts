group = "dev.aafanasev"
version = "1.0-SNAPSHOT"

plugins {
    `kotlin-dsl`
    id("org.jetbrains.intellij") version "0.4.7"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":common"))
}

intellij {
    version = "2018.2.2"
    alternativeIdePath = "/Applications/Android Studio.app"
    // setPlugins("org.jetbrains.kotlin:1.3.11-release-IJ2018.3-1")
}

