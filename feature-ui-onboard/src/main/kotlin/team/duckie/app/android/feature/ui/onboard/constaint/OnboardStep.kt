/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.constaint

import team.duckie.app.android.util.kotlin.AllowMagicNumber

@AllowMagicNumber
enum class OnboardStep(private val index: Int) {
    Login(0),
    Profile(1),
    Category(2),
    Tag(3);

    operator fun minus(previous: Int): OnboardStep {
        return values()[index - previous]
    }

    operator fun plus(next: Int): OnboardStep {
        return values()[index + next]
    }
}
