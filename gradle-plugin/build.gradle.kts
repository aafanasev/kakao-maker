import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

group = "com.aafanasev"
version = "0.3.1"

plugins {
    `maven-publish`
    `java-gradle-plugin`
    kotlin("jvm") version "1.3.21"
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])



            pom {

                configurations.implementation.dependencies
//                        .filter { it is DefaultExternalModuleDependency }
                        .forEach {
                           // println("asd $it")
                        }

//                withXml {
//                    (asNode()["dependencies"] as List<Node>).forEach {
//                        println("asd $it")
//                    }
//                }
            }
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
    implementation(project(":common"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.android.tools.build:gradle:3.3.1")
}

tasks {
    "jar"(Jar::class) {
        from(project(":common").sourceSets["main"].output.classesDirs)
        from(zipTree(project(":common").file("libs/kakao-2.0.0.jar")))
    }

    register("deps") {
        println("asd deps")

       project(":common").configurations.implementation.dependencies
                .forEach {
                    println("asd $it")
                }
    }
}