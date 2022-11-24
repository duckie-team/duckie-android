/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.code.ktlint)
    alias(libs.plugins.code.detekt)
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.local.convention.enum)
    alias(libs.plugins.util.dependency.handler.extensions)
}

koverMerged {
    enable()
    xmlReport {
        reportFile.set(file("$rootDir/report/test-coverage/report.xml"))
    }
    htmlReport {
        reportDir.set(file("$rootDir/report/test-coverage/html"))
    }
}

buildscript {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlin.core)
        classpath(libs.kotlin.dokka.base)
        classpath(libs.build.gradle.agp)
        classpath(libs.build.google.service)
        classpath(libs.build.firebase.crashlytics)
        classpath(libs.build.firebase.performance)
        classpath(libs.build.ui.oss.license)
        classpath(libs.build.di.hilt)
    }
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin.kover.get().pluginId)
        plugin(rootProject.libs.plugins.code.ktlint.get().pluginId)
        plugin(rootProject.libs.plugins.code.detekt.get().pluginId)
        plugin(rootProject.libs.plugins.local.convention.enum.get().pluginId)
        plugin(rootProject.libs.plugins.util.dependency.handler.extensions.get().pluginId)
    }

    repositories {
        google()
        mavenCentral()
        maven(url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
    }

    afterEvaluate {
        detekt {
            parallel = true
            buildUponDefaultConfig = true
            toolVersion = libs.versions.plugin.code.detekt.get()
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11"
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.OptIn",
                    "-opt-in=kotlin.RequiresOptIn",
                )
                // freeCompilerArgs = freeCompilerArgs + listOf(
                //     "-P",
                //     "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$rootDir/report/compose-metrics",
                // )
                // freeCompilerArgs = freeCompilerArgs + listOf(
                //     "-P",
                //     "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$rootDir/report/compose-reports",
                // )
            }
        }
    }

    if (pluginManager.hasPlugin(rootProject.libs.plugins.kotlin.dokka.get().pluginId)) {
        tasks.dokkaHtmlMultiModule.configure {
            moduleName.set("DUCKIE")
            outputDirectory.set(file("$rootDir/documents/dokka"))

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                footerMessage = """
                                |made with <span style="color: #ff8300;">❤</span> by <a href="https://duckie.team/">Duckie Team</a>
                                """.trimMargin()
                customAssets = listOf(file("assets/icon/logo-icon.svg"))
            }
        }
    }
}

subprojects {
    // https://github.com/gradle/gradle/issues/4823#issuecomment-715615422
    @Suppress("UnstableApiUsage")
    if (
        gradle.startParameter.isConfigureOnDemand &&
        buildscript.sourceFile?.extension?.toLowerCase() == "kts" &&
        parent != rootProject
    ) {
        generateSequence(parent) { project ->
            project.parent.takeIf { parent ->
                parent != rootProject
            }
        }.forEach { project ->
            evaluationDependsOn(project.path)
        }
    }

    configure<KtlintExtension> {
        version.set(rootProject.libs.versions.plugin.code.ktlint.source.get())
        android.set(true)
        outputToConsole.set(true)
        additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    }
}

tasks.register(
    name = "cleanAll",
    type = Delete::class,
) {
    allprojects.map(Project::getBuildDir).forEach(::delete)
}
