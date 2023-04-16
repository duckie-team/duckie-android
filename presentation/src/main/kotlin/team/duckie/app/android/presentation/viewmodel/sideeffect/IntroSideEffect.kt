/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel.sideeffect

import team.duckie.app.android.domain.user.model.User

internal sealed class IntroSideEffect {
    /** 유저 설정이 안되어 있을 시 방출하는 SideEffect (앱 설치 후 첫 진입, 로그아웃 등) */
    object UserNotInitialized : IntroSideEffect()

    /** 유저 정보를 성공적으로 가져온 경우 방출하는 SideEffect */
    class GetUserFinished(val user: User) : IntroSideEffect()

    /** 유저 정보를 가져오는 도중 에러 발생 시 방출하는 SideEffect */
    class GetMeError(val exception: Throwable) : IntroSideEffect()

    /** 앱 업데이트가 필요할 시 방출하는 SideEffect */
    class UpdateRequireError(val exception: Throwable) : IntroSideEffect()

    /** 에러 Report 하는 SideEffect */
    class ReportError(val exception: Throwable) : IntroSideEffect()
}
