/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("PrivatePropertyName")

package team.duckie.app.android.common.android.exception.handling.reporter

import android.app.Activity
import android.widget.Toast

private val DefaultToastReportMessage = """
    |예상치 못한 문제가 발생했어요.
    |잠시 후 다시 시도해 보세요.
""".trimMargin()

context(Activity) fun Throwable.reportToToast(message: String = DefaultToastReportMessage) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}
