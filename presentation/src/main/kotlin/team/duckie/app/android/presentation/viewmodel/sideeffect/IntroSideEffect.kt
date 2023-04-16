/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel.sideeffect

import team.duckie.app.android.domain.user.model.User

internal sealed class IntroSideEffect {
    class GetUserFinished(val user: User?) : IntroSideEffect()

    class ReportError(val exception: Throwable) : IntroSideEffect()
}
