plugins {
    id("java")
}

group = "at3.hw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.rest-assured:rest-assured:5.5.6")
    implementation("com.fasterxml.jackson.core:jackson-core:2.22.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.22.3")
    testImplementation("org.assertj:assertj-core:3.27.7")
    implementation("com.codeborne:selenide:7.18.2")

    implementation("org.seleniumhq.selenium:selenium-java:4.49.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.51.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("Api-Tests") {
    group = "verification"

    useJUnitPlatform() {
        includeTags("api-test")
    }
}

// -- Задача 1: Запуск всех тестов (используется встроенный task 'test')--
// Ничего делать не нужно, так как task test уже есть по дефолту.

/*
tasks.register<Test>("reportTest"){
    group = "myTestInHw2"
    dependsOn("test")
    doLast{
        println("Test run is over")
    }
}*/
