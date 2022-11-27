/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "team.duckie.app.android.local"
version = "master"

gradlePlugin {
    plugins {
        create("conventionEnumPlugin") {
            id = "team.duckie.app.android.local.convention.enum"
            implementationClass = "ConventionEnum"
        }
        create("pluginEnumPlugin") {
            id = "team.duckie.app.android.local.plugin.enum"
            implementationClass = "PluginEnum"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}
