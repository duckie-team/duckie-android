/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import team.duckie.app.android.common.kotlin.seconds
import team.duckie.quackquack.ui.animation.QuackAnimationMillis
import team.duckie.quackquack.ui.modifier.QuackAlwaysShowRipple
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    init {
        // FIXME(sungbin): 인앱에서 250 ms 는 너무 길게 느껴짐
        QuackAnimationMillis = 0.15.seconds.toInt()
        QuackAlwaysShowRipple = BuildConfig.ALWAYS_RIPPLE
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}
