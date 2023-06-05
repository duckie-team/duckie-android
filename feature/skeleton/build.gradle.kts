import DependencyHandler.Extensions.implementations

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidHilt)
}

android {
    /**
     * STEP
     * 1. settings.gradle.kts 에 해당 모듈 추가하기
     * 2. namespace 및 activity 명 모듈 명에 맞게 수정하기
     * 3. 패키지 구조 모듈 명에 맞게 변경하기 (ex. feature/skeleton -> feature/exam/list)
     * 4. implementations 모듈 정리
     * 5. navigator 모듈에 navigator 인터페이스 추가 (자세한 내용은 SkeletonNavigator.kt 참고)
     * 6. gradle sync
     */
    namespace = "team.duckie.app.android.feature.skeleton"
}

dependencies {
    implementations(
        projects.di,
        projects.domain,
        projects.navigator,
        projects.common.android,
        projects.common.kotlin,
        projects.common.compose,
        libs.orbit.viewmodel,
        libs.orbit.compose,
        libs.ktx.lifecycle.runtime,
        libs.compose.lifecycle.runtime,
        libs.quack.v2.ui,
        libs.firebase.crashlytics,
    )
}
