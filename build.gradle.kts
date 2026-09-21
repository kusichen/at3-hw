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
    testImplementation("org.assertj:assertj-core:4.0.0-M1")
}

/*tasks.test {
    useJUnitPlatform()
}*/

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