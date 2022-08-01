import io.deepmedia.tools.publisher.common.GithubScm
import io.deepmedia.tools.publisher.common.License
import io.deepmedia.tools.publisher.common.Release

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.dokka")
    id("io.deepmedia.tools.publisher")
    id("jacoco")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 19
        targetSdk = 32
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes["debug"].isTestCoverageEnabled = true
    buildTypes["release"].isMinifyEnabled = false

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true

        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }

        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

jacoco { toolVersion = "0.8.8" }

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("compileDebugSources")

    reports.html.required.set(true)
    reports.xml.required.set(true)

    sourceDirectories.from(android.sourceSets["main"].java.srcDirs)
    additionalSourceDirs.from("$buildDir/generated/source/buildConfig/debug")
    additionalSourceDirs.from("$buildDir/generated/source/r/debug")
    classDirectories.from(fileTree("$buildDir/intermediates/javac/debug") {
        // Not everything here is relevant for CameraView, but let's keep it generic
        exclude(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "android/**",
            "androidx/**",
            "com/google/**",
            "**/*\$ViewInjector*.*"
        )
    })
    executionData.from(fileTree(mapOf("dir" to project.buildDir, "include" to listOf(
            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec", // Unit tests
            "outputs/code_coverage/debugAndroidTest/connected/**/coverage.ec" // Android tests
    ))))
}

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        named("main") {
            noAndroidSdkLink.set(false)
        }
    }
}

dependencies {
    // Androidx
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.multidex:multidex:2.0.1")

    // RendererRecyclerViewAdapter
    api("com.github.vivchar:RendererRecyclerViewAdapter:3.0.1")

    // Token Autocomplete
    api("com.splitwise:tokenautocomplete:3.0.2@aar")

    // ImagePicker
    api("com.github.dhaval2404:imagepicker:2.1")

    implementation("com.github.bumptech.glide:glide:4.13.1")

    // Input mask
    api("com.github.RedMadRobot:input-mask-android:6.1.0")

    // Android SVG
    implementation("com.caverock:androidsvg-aar:1.4")

    // Expandable Layout (To use InlineDatePickerElement)
    implementation("com.github.cachapa:ExpandableLayout:2.9.2")

    // ThreeTen Android Backport (To use InlineDatePickerElement)
    api("com.jakewharton.threetenabp:threetenabp:1.4.0")

    // WheelPicker (To use InlineDatePickerElement)
    implementation("com.github.AigeStudio:WheelPicker:5913fa15fc")

    // Test dependencies
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("io.kotest:kotest-runner-junit5:5.4.1")
    testImplementation("io.kotest:kotest-property:5.2.3")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0")
}

publisher {
    project.description = "Easily build generic forms with minimal effort (A Kotlin port of FormMaster)"
    project.artifact = "k-form-master"
    project.group = "com.github.thejuki"
    project.url = "https://github.com/TheJuki/KFormMaster"
    project.scm = GithubScm("TheJuki", "KFormMaster")
    project.addLicense(License.APACHE_2_0)
    project.addLicense("License", "https://github.com/TheJuki/KFormMaster/blob/master/LICENSE")
    project.addDeveloper("Justin Kirk", "thejuki@hotmail.com", "thejuki")
    release.version = "8.3.0"
    release.tag = "8.3.0"
    release.sources = Release.SOURCES_AUTO
    release.docs = Release.DOCS_AUTO

    directory()

    sonatype {
        auth.user = "SONATYPE_USER"
        auth.password = "SONATYPE_PASSWORD"
        signing.key = "SIGNING_KEY"
        signing.password = "SIGNING_PASSWORD"
    }
}