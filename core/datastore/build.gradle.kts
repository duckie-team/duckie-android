/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    alias(libs.plugins.duckie.android.library)
}

android {
    namespace = "team.duckie.app.android.feature.datastore"
}

dependencies {
    api(libs.androidx.datastore)
}
