plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp") version Versions.Ksp
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

android {
    namespace = "land.sungbin.androidprojecttemplate.data"
}

dependencies {
    val dependencies = listOf(
        Dependencies.Ksp,
        Dependencies.Jackson,
        Dependencies.Network,
        Dependencies.Jetpack.Room,
        platform(Dependencies.FirebaseBom),
        Dependencies.FirebaseEachKtx.Storage
    ).dependenciesFlatten()
    dependencies.forEach(::implementation)

    val projects = listOf(
        ProjectConstants.Domain,
        ProjectConstants.SharedDomain
    )
    projects.forEach(::projectImplementation)

    val ksps = listOf(
        Dependencies.Compiler.RoomKsp
    )
    ksps.forEach(::ksp)
}
