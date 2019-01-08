group = "com.aafanasev"
version = "0.1"

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

gradlePlugin {
    plugins {
        create("kakao-maker-gradle-plugin") {
            id = "kakao-maker"
            implementationClass = "com.aafanasev.kakaomaker.KakaoMakerPlugin"
        }
    }
}

repositories {
    jcenter()
    mavenCentral()
    google()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.android.tools.build:gradle:3.2.1")
    implementation("com.squareup:kotlinpoet:1.0.1")
    implementation(files("libs/kakao-1.4.0.jar"))
}

