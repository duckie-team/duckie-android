/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import team.duckie.quackquack.ui.modifier.QuackAlwaysShowRipple

@HiltAndroidApp
class App : Application() {
    init {
        QuackAlwaysShowRipple = BuildConfig.ALWAYS_RIPPLE
    }
}
