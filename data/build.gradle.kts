plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "land.sungbin.androidprojecttemplate.data"

    lint {
        disable.add("ListNaming")
    }
}

dependencies {

//    implementation("team.duckie.quack:quack-lint-core:1.0.1")

    val dependencies = listOf(
        Dependencies.FirebaseEachKtx.DataBase,
        Dependencies.FirebaseEachKtx.FireStore,
        Dependencies.Jetpack.Hilt,
        Dependencies.Jetpack.Room,
        platform(Dependencies.FirebaseBom),
    )
    dependencies.forEach(::implementation)

    Dependencies.Network.forEach(::implementation)
    Dependencies.Login.forEach(::implementation)

    kapt(Dependencies.Compiler.Hilt)
    kapt(Dependencies.Compiler.RoomKsp)

    implementation(project(":domain"))
}
