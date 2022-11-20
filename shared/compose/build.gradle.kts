plugins {
    id("com.android.library")
    id("kotlin-android")
    id("name.remal.check-dependency-updates") version Versions.BuildUtil.CheckDependencyUpdates
}

android {
    namespace = "land.sungbin.androidprojecttemplate.shared.compose"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Main
    }
}

dependencies {
    val projects = listOf(
        ProjectConstants.Domain,
        ProjectConstants.SharedDomain
    )
    projects.forEach(::projectImplementation)

    val dependencies = listOf(
        Dependencies.Compose,
        Dependencies.EachKtx.Core,
        Dependencies.EachKtx.Lifecycle,
    ).dependenciesFlatten()
    dependencies.forEach(::implementation)
}
