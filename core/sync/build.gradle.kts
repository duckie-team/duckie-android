import DependencyHandler.Extensions.implementations

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidHilt)
}

android {
    namespace = "team.duckie.app.android.core.sync"
}

dependencies {
    implementations(
        projects.domain,
        projects.presentation,
        platform(libs.firebase.bom),
        libs.firebase.messaging,
    )
}
