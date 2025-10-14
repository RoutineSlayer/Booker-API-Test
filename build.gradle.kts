plugins {
    id("java")
}

group = "com.aqa.luna"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6") {
        exclude(group = "commons-codec", module = "commons-codec")
    }

    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")

}

tasks.test {
    useJUnitPlatform()
}