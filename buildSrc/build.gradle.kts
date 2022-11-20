plugins {
    `kotlin-dsl`
}

dependencies {
    // Unresolved reference: implementations
    implementation(libs.build.kotlin)
    implementation(libs.build.gradle.agp)
}
