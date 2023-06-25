/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.common.android.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

/**
 * 기존 액티비티를 fade out 과 함께 닫고, 새로운 액티비티를 fade in 과 함께 시작합니다.
 *
 * @param intentBuilder 새로 시작할 액티비티의 [Intent]
 */
inline fun <reified T : Activity> Activity.changeActivityWithAnimation(
    intentBuilder: Intent.() -> Intent = { this },
) {
    startActivityWithAnimation<T>(
        intentBuilder = intentBuilder,
        withFinish = true,
    )
}

/**
 * Fade in 과 함께 새로운 액티비티를 시작합니다.
 *
 * @param intentBuilder 새로 시작할 액티비티의 [Intent]
 * @param withFinish 현재 액티비티를 종료할지 여부
 */
inline fun <reified T : Activity> Activity.startActivityWithAnimation(
    intentBuilder: Intent.() -> Intent = { this },
    withFinish: Boolean = false,
) {
    startActivity(intentBuilder(Intent(this, T::class.java)))
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (withFinish) finish()
}

/**
 * Fade in 과 함께 새로운 Launcher 를 시작합니다.
 *
 *  * @param intentBuilder 새로 시작할 액티비티의 [Intent]
 *  * @param withFinish 현재 액티비티를 종료할지 여부
 */
inline fun <reified T : Activity> Activity.startLauncherWithAnimation(
    launcher: ActivityResultLauncher<Intent>,
    intentBuilder: Intent.() -> Intent = { this },
    withFinish: Boolean = false,
) {
    launcher.launch(
        intentBuilder(Intent(this, T::class.java)),
    )
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (withFinish) finish()
}

/**
 * Fade out 애니메이션과 함께 현재 액티비티를 닫습니다.
 */
fun Activity.finishWithAnimation() {
    finish()
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}
