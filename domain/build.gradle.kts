plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "land.sungbin.androidprojecttemplate.domain"
}

dependencies {
    implementation("com.github.toss:tuid:v0.2.1")
    implementation("androidx.annotation:annotation:1.5.0")
    implementation("team.duckie.quack:quack-lint-core:1.0.1")
}
