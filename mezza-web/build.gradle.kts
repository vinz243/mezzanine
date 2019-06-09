val spek_version = "2.0.1"

plugins {
    kotlin("jvm")
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    implementation(project(":mezza-core"))
    implementation(project(":mezza-core"))
    implementation(kotlin("reflect"))

    api("io.ktor:ktor-server-core:1.2.1")
    api("io.ktor:ktor-server-core:1.2.1")
    api("io.ktor:ktor-gson:1.2.1")
    testCompile("io.ktor:ktor-server-netty:1.2.1")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")

    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.3.21")
    testImplementation("org.hamcrest:hamcrest:2.1")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("io.rest-assured:rest-assured:4.0.0")
    testCompile(group = "org.slf4j", name = "slf4j-simple", version = "1.8.0-beta4")

    // testCompile(project(":catalog-themoviedb"))
}


tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}