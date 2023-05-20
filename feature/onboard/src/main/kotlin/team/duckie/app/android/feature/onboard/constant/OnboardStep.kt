/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.onboard.constant

import team.duckie.app.android.common.kotlin.AllowMagicNumber

@AllowMagicNumber
internal enum class OnboardStep(private val index: Int) {
    Activity(-1),
    Login(0),
    Profile(1),
    Category(2),
    Tag(3),
    ;

    operator fun minus(previous: Int): OnboardStep {
        return OnboardStep.values().first { it.index == index - previous }
    }

    operator fun plus(next: Int): OnboardStep {
        return OnboardStep.values().first { it.index == index + next }
    }
}

internal annotation class CollectInStep {
    companion object
}

internal annotation class RequiredStep(@Suppress("unused") vararg val step: OnboardStep)
