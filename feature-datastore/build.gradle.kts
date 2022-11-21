/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
}

android {
    namespace = "team.duckie.app.android.feature.datastore"
}

dependencies {
    implementation(libs.androidx.datastore)
}
