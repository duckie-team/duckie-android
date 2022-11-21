/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmDokka)
    id(ConventionEnum.JvmJUnit4)
}

android {
    namespace = "team.duckie.app.android.util.viewmodel"
}

dependencies {
    implementations(
        libs.kotlin.coroutines,
        projects.utilKotlin,
    )
    testImplementations(
        libs.test.turbine,
        libs.test.coroutines,
    )
}
