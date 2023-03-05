/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "PropertyName")

import land.sungbin.dependency.graph.DependencyInfo
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.code.ktlint)
    alias(libs.plugins.code.detekt)
    alias(libs.plugins.kotlin.dokka)
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.local.plugin.enum)
    alias(libs.plugins.local.convention.enum)
    alias(libs.plugins.util.secrets) apply false
    alias(libs.plugins.util.dependency.handler.extensions)
    alias(libs.plugins.util.dependency.graph)
    alias(libs.plugins.kotlin.ksp)
}

val UtilModulePrefix = "util-"
val SharedUiModulePrefix = "shared-ui-"
val FeatureModulePrefix = "feature-"
val UiFeatureModulePrefix = "feature-ui-"
val PluginModulePrefix = "plugin-"

dependencyGraphConfigs {
    dotFilePath = "assets/dependency-graph/project.dot"

    dependencyBuilder { project ->
        with(project) {
            when {
                // colorset: https://coolors.co/palette/b4bd9b-bc455a-fdba77-f6cf98-81bdc3-fdf8ec-f9d6d3-ccd5c3
                plugins.hasPlugin(PluginEnum.AndroidApplication) -> DependencyInfo(
                    color = "#B4BD9B",
                    isBoxShape = true,
                )
                plugins.hasPlugin(PluginEnum.AndroidDfm) -> DependencyInfo("#BC455A")
                name.startsWith(UtilModulePrefix) -> DependencyInfo("#FDBA77")
                name.startsWith(SharedUiModulePrefix) -> DependencyInfo("#F6CF98")
                name.startsWith(UiFeatureModulePrefix) -> DependencyInfo("#81BDC3")
                name.startsWith(FeatureModulePrefix) -> DependencyInfo("#FDF8EC")
                name.startsWith(PluginModulePrefix) -> DependencyInfo("#F9D6D3")
                plugins.hasPlugin(PluginEnum.AndroidLibrary) -> DependencyInfo("#CCD5C3")
                else -> null
            }
        }
    }
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
        maven(url = uri("https://devrepo.kakao.com/nexus/content/groups/public/"))
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
                jvmTarget = "17"
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.OptIn",
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xcontext-receivers",
                )
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
