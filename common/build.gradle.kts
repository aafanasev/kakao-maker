plugins {
    kotlin("jvm") version "1.3.21"
}

val kakaoJar = "libs/kakao-2.0.0.jar"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.squareup:kotlinpoet:1.1.0")
    implementation(files(kakaoJar))
}

repositories {
    jcenter()
    mavenCentral()
    google()
}