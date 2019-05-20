plugins {
    kotlin("jvm") version "1.3.21"
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.squareup:kotlinpoet:1.1.0")
    compile(files(KAKAO_PATH))
}

repositories {
    jcenter()
    mavenCentral()
    google()
}