buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("io.deepmedia.tools:publisher:0.7.0")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
