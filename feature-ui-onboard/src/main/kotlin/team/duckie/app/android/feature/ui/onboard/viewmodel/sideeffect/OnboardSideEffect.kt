/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import team.duckie.app.android.domain.user.model.User

internal sealed class OnboardSideEffect {
    class UpdateGalleryImages(val images: List<String>) : OnboardSideEffect()

    class UpdateUser(val user: User) : OnboardSideEffect()
    class UpdateAccessToken(val accessToken: String) : OnboardSideEffect()

    class AttachAccessTokenToHeader(val accessToken: String): OnboardSideEffect()
    class DelegateJoin(val kakaoAccessToken: String): OnboardSideEffect()

    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
