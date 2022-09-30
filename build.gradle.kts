import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt") version 1.21.0
    id("org.jlleitschuh.gradle.ktlint") version Versions.BuildUtil.KtlintPlugin
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${Versions.BuildUtil.Dokka}")
        classpath("com.android.tools.build:gradle:7.4.0-alpha10")
        // classpath("com.google.gms:google-services:${Versions.Essential.GoogleService}")
        // classpath("com.spotify.ruler:ruler-gradle-plugin:${Versions.BuildUtil.Ruler}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Jetpack.Hilt}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}")
        // classpath("com.google.firebase:perf-plugin:${Versions.Analytics.FirebasePerformance}")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:${Versions.Test.JUnitGradle}")
        classpath("com.google.android.gms:oss-licenses-plugin:${Versions.OssLicense.Classpath}")
        // classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.Analytics.FirebaseCrashlytics}")
        // classpath("com.vanniktech:gradle-dependency-graph-generator-plugin:${Versions.BuildUtil.DependencyGraphGenerator}")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.Util.SecretsGradlePlugin}")
    }
}

allprojects {
    val detektExcludePath = "**/xml/**"

    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }

    afterEvaluate {
        project.apply("$rootDir/gradle/common.gradle")

        detekt {
            buildUponDefaultConfig = true
            toolVersion = Versions.BuildUtil.Detekt
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        tasks.withType<Detekt>().configureEach {
            jvmTarget = ApplicationConstants.jvmTarget
            exclude(detektExcludePath)
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.OptIn",
                    "-Xopt-in=kotlin.RequiresOptIn"
                )
            }
        }
    }

    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    /*configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.github.kittinunf.result" && requested.name == "result" && requested.version == "3.0.0") {
                useVersion("3.0.1")
                because("Transitive dependency of Scabbard, currently not available on mavenCentral()")
            }
        }
    }*/
}

subprojects {
    // https://github.com/gradle/gradle/issues/4823#issuecomment-715615422
    @Suppress("UnstableApiUsage")
    if (gradle.startParameter.isConfigureOnDemand &&
        buildscript.sourceFile?.extension?.toLowerCase() == "kts" &&
        parent != rootProject
    ) {
        generateSequence(parent) { project -> project.parent.takeIf { it != rootProject } }
            .forEach { evaluationDependsOn(it.path) }
    }
}

tasks.register("clean", Delete::class) {
    allprojects.map { it.buildDir }.forEach(::delete)
}

apply {
    // plugin("com.vanniktech.dependency.graph.generator")
    from("gradle/projectDependencyGraph.gradle")
}
