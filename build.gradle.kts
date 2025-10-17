plugins {
    id("java")
}

group = "com.aqa.luna"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.0"
val restAssuredVersion = "5.5.6"
val allureVersion = "2.25.0"
val jacksonVersion = "2.20.0"
val lombokVersion = "1.18.42"

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion") {
        exclude(group = "commons-codec", module = "commons-codec")
    }

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-rest-assured")
}

tasks.test {
    useJUnitPlatform()
}