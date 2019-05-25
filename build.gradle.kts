group = "dev.aafanasev"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.3.21"
    id("org.jetbrains.intellij") version "0.4.8"
}

repositories {
    jcenter()
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.squareup:kotlinpoet:1.1.0")
    compile(files("libs/kakao-2.0.0.jar"))
}

intellij {
    version = "2018.3.4"
    updateSinceUntilBuild = false
    alternativeIdePath = "/Applications/Android Studio.app"
}
