plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

android {
    namespace = "land.sungbin.androidprojecttemplate.data"
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation(project(":domain"))
}
