/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.exception.handling.extension

// FIXME(sungbin): 에러 해결
// context(BaseViewModel<out ExceptionState, out ExceptionSideEffect>)
// suspend fun <T> Result<T>.attachExceptionHandling(
//     additinal: (exception: Throwable) -> Unit = {},
// ): Result<T> {
//     return onFailure { exception ->
//         updateState {
//             ExceptionState.Error(exception)
//         }
//         postSideEffect {
//             ExceptionSideEffect.ReportExceptionToCrashlytics(exception)
//         }
//         additinal(exception)
//     }
// }
