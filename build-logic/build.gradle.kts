@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.dokka)
}

group = "team.duckie.app.android.convention"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Unresolved reference: implementations
    implementation(libs.kotlin.core)
    implementation(libs.kotlin.dokka.base)
    implementation(libs.kotlin.dokka.plugin)
    implementation(libs.build.gradle.agp)
}

gradlePlugin {
    val prefix = "duckie"
    plugins {
        register("androidApplicationPlugin") {
            id = "$prefix.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidApplicationComposePlugin") {
            id = "$prefix.android.application.compose"
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("androidLibraryPlugin") {
            id = "$prefix.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidLibraryComposePlugin") {
            id = "$prefix.android.library.compose"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("androidLibraryComposeUiTestPlugin") {
            id = "$prefix.android.library.compose.uitest"
            implementationClass = "AndroidLibraryComposeUiTestPlugin"
        }
        register("jvmJunit4Plugin") {
            id = "$prefix.jvm.junit4"
            implementationClass = "JvmJUnit4Plugin"
        }
        register("jvmDokkaPlugin") {
            id = "$prefix.jvm.dokka"
            implementationClass = "JvmDokkaPlugin"
        }
        register("dependencyGraphPlugin") {
            id = "$prefix.jvm.dependency.graph"
            implementationClass = "DependencyGraphPlugin"
        }
    }
}
