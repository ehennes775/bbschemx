plugins {
    kotlin("multiplatform") version "1.4.32"
}

group = "com.github.ehennes775"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    
    sourceSets {

        jvm()

        val commonMain by getting {
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.freeplane.bulenkov:darcula:2018.2")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.32")
                implementation("org.xerial:sqlite-jdbc:3.34.0")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
            }
            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}


