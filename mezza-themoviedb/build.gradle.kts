
val spek_version = "2.0.1"

plugins {
    kotlin("jvm")
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile(project(":mezza-core"))
    implementation(kotlin("reflect"))
    
    implementation(group = "info.movito", name = "themoviedbapi", version = "1.9")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")

    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.3.21")
    testImplementation("org.amshove.kluent:kluent:1.49")
    testImplementation("io.mockk:mockk:1.9.3")
   // testCompile(project(":catalog-themoviedb"))
}


tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}