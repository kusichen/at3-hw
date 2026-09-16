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
}

/*tasks.test {
    useJUnitPlatform()
}*/

// -- Задача 1: Запуск всех тестов (используется встроенный task 'test')--
// Ничего делать не нужно, так как task test уже есть по дефолту.

tasks.register<Test>("reportTest"){
    group = "myTestInHw2"
    useJUnitPlatform{
        includeTags("N-Test")
    }
    dependsOn("test")
    doLast{
        println("Test run is over")
    }
}