plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

android {
    namespace = "land.sungbin.androidprojecttemplate.data"

    lint {
        disable.add("ListNaming")
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("team.duckie.quack:quack-lint-core:1.0.1")
    implementation(project(":domain"))
}
