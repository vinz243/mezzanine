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
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.exposed:exposed:0.14.2")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")

    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.3.21")
    testImplementation("org.amshove.kluent:kluent:1.49")
    testCompile(group = "org.slf4j", name = "slf4j-simple", version = "1.8.0-beta4")

    // testCompile(project(":catalog-themoviedb"))
}


tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}