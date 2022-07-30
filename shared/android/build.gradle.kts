plugins {
    id("com.android.library")
    id("kotlin-android")
    id("name.remal.check-dependency-updates") version Versions.BuildUtil.CheckDependencyUpdates
}

android {
    namespace = "land.sungbin.androidprojecttemplate.shared.android"
}

dependencies {
    val apis = listOf(
        Dependencies.SharedKtx,
        Dependencies.EachKtx.Core,
        Dependencies.Jetpack.DataStore,
        project(ProjectConstants.Domain),
        project(ProjectConstants.SharedDomain)
    ).dependenciesFlatten()
    apis.forEach(::api)

    val dependencies = listOf(
        Dependencies.EachKtx.Activity,
        Dependencies.EachKtx.Fragment
    )
    dependencies.forEach(::implementation)
}
