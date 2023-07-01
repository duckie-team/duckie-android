/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.intent

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import team.duckie.app.android.common.android.constant.getAppPackageName

fun Activity.goToMarket(appPackageName: String = getAppPackageName()) {
    // TODO(riflockle7): 추후 다른 곳에서 가져오도록 개선 필요
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
    } catch (e: ActivityNotFoundException) {
        Log.w("goToMarket", "Play Store not found for package name: $appPackageName", e)
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"),
            ),
        )
    }
}
