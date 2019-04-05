group = "com.aafanasev"
version = "0.3.1"

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.10.0"
}

gradlePlugin {
    plugins {
        create("kakaoMakerPlugin") {
            id = "com.aafanasev.kakao-maker"
            displayName = "Kakao Maker"
            description = "Generate Kakao screens from Android layouts"
            implementationClass = "com.aafanasev.kakaomaker.KakaoMakerPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/aafanasev/kakao-maker/"
    vcsUrl = "https://github.com/aafanasev/kakao-maker/"
    tags = listOf("kakao", "android", "espresso")
}

repositories {
    jcenter()
    mavenCentral()
    google()
}

val kakaoJar = "libs/kakao-2.0.0.jar"

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.android.tools.build:gradle:3.3.1")
    implementation("com.squareup:kotlinpoet:1.1.0")
    implementation(files(kakaoJar))
}

tasks {
    "jar"(Jar::class) {
        from(zipTree(kakaoJar))
    }
}
