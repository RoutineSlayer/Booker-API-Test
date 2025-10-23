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
val dotenvVersion = "5.2.2"

val aspectJVersion = "1.9.21"
val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion") {
        exclude(group = "commons-codec", module = "commons-codec")
    }
    testImplementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")

    testImplementation("io.github.cdimascio:java-dotenv:$dotenvVersion")

    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")


    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
}

tasks.test {
    useJUnitPlatform()
    ignoreFailures = true
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}