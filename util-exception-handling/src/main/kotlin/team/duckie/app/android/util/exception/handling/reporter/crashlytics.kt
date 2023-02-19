/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.exception.handling.reporter

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import team.duckie.app.android.util.exception.handling.BuildConfig

fun Throwable.reportToCrashlyticsIfNeeded() {
    if (!BuildConfig.DEBUG) {
        Firebase.crashlytics.recordException(this)
    }
}
