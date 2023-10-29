/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android

import android.app.Application
import androidx.datastore.preferences.core.edit
import com.gu.toolargetool.TooLargeTool
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import team.duckie.app.android.common.kotlin.seconds
import team.duckie.app.android.core.datastore.PreferenceKey
import team.duckie.app.android.core.datastore.dataStore
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
        TooLargeTool.startLogging(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // DataStore 에서 가져와 stage 여부를 확인하는 로직
        // 값이 없을 경우, BuildFlavor 내 기본 값으로 설정한다.
        // TODO(riflockle7): 동기적으로 처리할 수 있는 방법이 있을까?
        runBlocking {
            applicationContext.dataStore.edit { preference ->
                if (preference[PreferenceKey.DevMode.IsStage] == null) {
                    preference[PreferenceKey.DevMode.IsStage] = BuildConfig.IS_STAGE
                }
            }
        }
    }
}
