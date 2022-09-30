plugins {
    id("com.android.library")
    id("kotlin-android")
    id("name.remal.check-dependency-updates") version 1.5.0
}

android {
    namespace = "land.sungbin.androidprojecttemplate.shared.domain"
}

dependencies {
    val apis = listOf(
        Dependencies.Coroutine,
        Dependencies.Util.Logeukes
    )
    apis.forEach(::api)
}
