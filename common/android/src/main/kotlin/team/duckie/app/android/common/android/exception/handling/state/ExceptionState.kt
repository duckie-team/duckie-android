/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.exception.handling.state

import android.annotation.SuppressLint
import androidx.annotation.Discouraged

@Discouraged(message = "`Result<T>.attachExceptionHandling()` 함수가 완성되기 전엔 사용하면 안 됩니다.")
interface ExceptionState {
    @SuppressLint("DiscouragedApi")
    class Error(val exception: Throwable) : ExceptionState
}
